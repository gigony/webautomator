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
import edu.unl.webautomator.core.util.JSoupHelper;
import edu.unl.webautomator.core.util.JacksonHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by gigony on 12/6/14.
 */
public class WebTestCaseConverter implements TestCaseConverter<WebEventElement> {
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

      try {
        result = mapper.readValue(file, WebTestCase.class);
      } catch (IOException e) {
        e.printStackTrace();
        throw new UnsupportedTestCaseException(String.format("Cannot read test case object from %s", fileName));
      }
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
    Elements eventItems = doc.select("body>table>tbody>tr");
    for (Element eventItem: eventItems) {
      Elements eventElems = eventItem.select("td");
      if (eventElems.size() != 3) {
        throw new RuntimeException("does not have triple");
      }

      String command = eventElems.get(0).text();
      String target = eventElems.get(1).text();
      String input = eventElems.get(2).text();

      webEventElements.add(new WebEventElement(command, "", target, input));
    }

    WebTestCase result = new WebTestCase(baseUrl);
    int elemSize = webEventElements.size();

    WebEvent event = new WebEvent();

    boolean isFirstOpenCmd = true;
    for (int i = 0; i < elemSize; i++) {
      WebEventElement elem = webEventElements.get(i);

      if ("open".equals(elem.getEventType())) {
        event.addAction(elem);
        isFirstOpenCmd = false;
        result.setPrefix(event);
        event = new WebEvent();
        continue;
      }

      if (isFirstOpenCmd) {
        event.addAction(elem);
      } else {





      }

    }



    return result;

  }

  @Override
  public final void saveTestCase(final String fileName, final String fileType, final TestCase<WebEventElement> testCase) {
    File file = new File(fileName);
    String format = fileType.toLowerCase();
    if ("json".equals(fileType)) {
      JacksonHelper.saveObjectToJsonFile(file, testCase);
    }
  }

  @Override
  public final boolean isSupported(final String fileType) {
    return supportedFormats.contains(fileType.toLowerCase());
  }
}
