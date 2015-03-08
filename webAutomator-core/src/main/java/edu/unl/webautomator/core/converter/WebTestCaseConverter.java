/*
 * Copyright 2015 Gigon Bae
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package edu.unl.webautomator.core.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.exception.UnsupportedTestCaseException;
import edu.unl.webautomator.core.model.*;
import edu.unl.webautomator.core.platform.browser.BasicWebBrowser;
import edu.unl.webautomator.core.util.IOHelper;
import edu.unl.webautomator.core.util.JSoupHelper;
import edu.unl.webautomator.core.util.JacksonHelper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by gigony on 12/6/14.
 */
public class WebTestCaseConverter implements TestCaseConverter<WebEvent> {
  private WebAutomator webAutomator;
  private static Set<String> supportedFormats = Sets.newHashSet("json", "html");

  @Inject
  public WebTestCaseConverter(@Assisted final WebAutomator automator) {
    this.webAutomator = automator;
  }

  @Override
  public final WebTestCase loadTestCase(final String fileName, final String fileType) throws FileNotFoundException {
    WebTestCase result = null;
    File file = new File(fileName);
    String format = fileType.toLowerCase();

    if (!file.exists()) {
      throw new FileNotFoundException(String.format("'%s' doesn't exist!"));
    }

    if ("json".equals(format)) {
      // create temporary object mapper due to abstract type 'Event' in the test case
      ObjectMapper mapper = new ObjectMapper(JacksonHelper.getObjectMapper().getFactory());
      SimpleModule module = new SimpleModule();
      module.addAbstractTypeMapping(Event.class, WebEvent.class);
      mapper.registerModule(module);

//      try {
      //result = JacksonHelper.loadObjectFromJsonFile(file, WebTestCase.class);
      result = JacksonHelper.loadObjectFromJsonString(IOHelper.getFileContentAsString(file), WebTestCase.class);
      //mapper.readValue(file, WebTestCase.class);
//      } catch (IOException e) {
//        e.printStackTrace();
//        throw new UnsupportedTestCaseException(String.format("Cannot read test case object from %s", fileName));
//      }
    } else if ("html".equals(format)) {
      try {
        result = this.getHtmlTestCase(file);
      } catch (Exception e) {
        throw new UnsupportedTestCaseException(String.format("%s has unsupported file format. (%s)", fileName, e.getMessage()));
      }
    }

    return result;
  }

  private WebTestCase getHtmlTestCase(final File file) throws Exception {
    Document doc = JSoupHelper.parse(file);


    Element baseUrlElem = doc.select("link").get(0);
    if (!baseUrlElem.attr("rel").equals("selenium.base")) {
      throw new RuntimeException("does not have 'selenium.base' attribute");
    }

    List<WebEventElement> webEventElements = new ArrayList<WebEventElement>();

    // get raw information
    String baseUrl = baseUrlElem.attr("href");
    String title = doc.select("title").get(0).text();
    Elements eventItems = doc.select("body>table>tbody>tr");
    for (Element eventItem : eventItems) {
      Elements eventElems = eventItem.select("td");
      if (eventElems.size() != 3) {
        throw new RuntimeException("does not have triple");
      }

      String command = eventElems.get(0).text();
      String target = eventElems.get(1).text();
      String input = eventElems.get(2).text();
      webEventElements.add(new WebEventElement(command, null, target, input));
    }


    // refactoring actions
    // frame reference: com.thoughtworks.selenium.webdriven.Windows

    WebTestCase result = new WebTestCase(title, baseUrl);
    int elemSize = webEventElements.size();
    WebEvent event = new WebEvent();
    LinkedList<String> frameStack = new LinkedList<String>();


    boolean isPrefixCmd = true;
    for (int i = 0; i < elemSize; i++) {
      WebEventElement elem = webEventElements.get(i);
      String command = elem.getEventType();
      String currFrameId = BasicWebBrowser.getFrameId(frameStack);

//    uncomment next line if you want to set frameId for each event
//      elem.setFrameId(currFrameId);

      elem.setFrameId(null);

      if ("open".equals(command)) {
        frameStack.clear();
      }

      if (isPrefixCmd) {
        if ("open".equals(command)) {
          event.addAction(elem);
          isPrefixCmd = false;
          // regard prefix commands until 'open' command as a prefix event of a test case
          result.setPrefixEvent(event);
        } else {
          event.addAction(elem); // will be added as a prefix event of a test case
        }
        continue;
      }

      if (command.startsWith("assert") || command.startsWith("verify")) {
        event.addPostCondition(elem);
      } else {

        if (event.getActionSize() > 0) {
          event = new WebEvent();
          result.add(event);
        }


        if (command.startsWith("waitFor") || command.equals("selectFrame")) {
          // set as a precondition
          if (command.equals("selectFrame")) {
            this.changeFrame(frameStack, elem.getArgs().get(0));
            elem.setFrameId(null);
          }
          if (event.getActionSize() == 0 && event.getPostConditionSize() == 0) {
            event.addPreCondition(elem);
          } else {
            event = new WebEvent();
            result.add(event);
            event.addPreCondition(elem);
          }
        } else {
          event.addAction(elem);
        }
      }
    }

    return result;

  }

  private void changeFrame(final LinkedList<String> frameStack, final String target) {
    String locator = target;
    if (target == null || target.equals("") || target.equals("null")) {
      frameStack.clear();
    }

    if ("relative=top".equals(locator)) {
      frameStack.clear();
      return;
    }

    if ("relative=up".equals(locator)) {
      if (!frameStack.isEmpty()) {
        frameStack.pop();
      }
      return;
    }

    if (locator.startsWith("index=")) {
      try {
        locator = locator.substring("index=".length());
        frameStack.push(locator);
        return;
      } catch (NumberFormatException e) {
        throw new RuntimeException(String.format("locator(locator) is incorrect in 'selectFrame' command", locator));
      }
    }

    if (locator.startsWith("id=")) {
      locator = locator.substring("id=".length());
    } else if (locator.startsWith("name=")) {
      locator = locator.substring("name=".length());
    }

    frameStack.push(locator);
  }

  @Override
  public final void saveTestCase(final String fileName, final String fileType, final TestCase<WebEvent> testCase) {
    File file = new File(fileName);
    String format = fileType.toLowerCase();
    if ("json".equals(fileType)) {
      JacksonHelper.saveObjectToJsonFile(file, testCase);
    } else if ("html".equals(fileType)) {
      this.writeHtml(file, (WebTestCase) testCase);
    }
  }

  private void writeHtml(final File file, final WebTestCase testCase) {
//    StringEscapeUtils.escapeHtml4(
    String title = StringEscapeUtils.escapeHtml4(testCase.getTitle());
    String baseUrl = StringEscapeUtils.escapeHtml4(testCase.getBaseUrl());


    StringBuffer contentBuf = new StringBuffer();

    for (WebEvent event : testCase) {
      for (WebEventElement eventElem : event) {
        int elemSize = eventElem.getArgSize();
        String command = eventElem.getEventType();
        String target = elemSize >= 1 ? StringEscapeUtils.escapeHtml4(eventElem.getArgs().get(0)) : "";
        String input = elemSize >= 2 ? StringEscapeUtils.escapeHtml4(eventElem.getArgs().get(1)) : "";
        String body = String.format("<tr>\n"
          + "\t<td>%s</td>\n"
          + "\t<td>%s</td>\n"
          + "\t<td>%s</td>\n"
          + "</tr>\n", command, target, input);
        contentBuf.append(body);
      }
    }

    StringBuffer buf = new StringBuffer();
    buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
      + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
      + "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n"
      + "<head profile=\"http://selenium-ide.openqa.org/profiles/test-case\">\n"
      + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
      + "<link rel=\"selenium.base\" href=\"");
    buf.append(baseUrl);
    buf.append("\" />\n"
      + "<title>");
    buf.append(title);
    buf.append("</title>\n"
      + "</head>\n"
      + "<body>\n"
      + "<table cellpadding=\"1\" cellspacing=\"1\" border=\"1\">\n"
      + "<thead>\n"
      + "<tr><td rowspan=\"1\" colspan=\"3\">");
    buf.append(title);
    buf.append("</td></tr>\n"
      + "</thead><tbody>\n");
    buf.append(contentBuf.toString());
    buf.append("</tbody></table>\n"
      + "</body>\n"
      + "</html>\n");

    PrintWriter pw = null;
    try {
      pw = new PrintWriter(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
    pw.print(buf.toString());
    pw.close();
  }

  @Override
  public final boolean isSupported(final String fileType) {
    return supportedFormats.contains(fileType.toLowerCase());
  }
}
