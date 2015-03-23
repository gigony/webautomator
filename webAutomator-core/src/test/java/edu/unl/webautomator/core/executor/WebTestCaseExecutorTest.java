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

package edu.unl.webautomator.core.executor;

import edu.unl.webautomator.core.QTE;
import edu.unl.webautomator.core.StaticWebServer;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.configuration.WebEventTypes;
import edu.unl.webautomator.core.model.*;
import edu.unl.webautomator.core.platform.WebBrowserType;
import edu.unl.webautomator.core.provider.WebEventInputHandler;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebTestCaseExecutorTest {
  private static final Logger LOG = LoggerFactory.getLogger(WebTestCaseExecutorTest.class);

  @Test
  public final void testExecute() throws Exception {
    StaticWebServer.start("/fixture/homepage/");

    WebAutomatorConfiguration config = WebAutomatorConfiguration.builder()
      .setWebBrowserConfiguration(WebBrowserConfiguration.builder(WebBrowserType.PHANTOMJS)
//        .setWebDriverBinaryPath("/Users/gigony/Development/Repository/github/webautomator/webautomator-core/build/webdrivers/chromedriver")
        .build())
      .setWebEventTypes(WebEventTypes.builder()
        .setDefaultEventTypes()
        .build())
      .setIgnoringFrameIds("")
      .build();


    WebAutomator automator = QTE.webAutomator(config);

    WebTestCase testcase = new WebTestCase("http://localhost:8080");

    testcase.add(new WebEvent(new WebEventElement("open", "", "/page1.html", null)));
    testcase.add(new WebEvent(new WebEventElement("type", "", "css=#name", null)));
    testcase.add(new WebEvent(new WebEventElement("click", "", "css=#yes_drives", null)));
    testcase.add(new WebEvent(new WebEventElement("click", "", "css=#any_time", null)));
    testcase.add(new WebEvent(new WebEventElement("type", "", "css=#mobile_number", null)));
    testcase.add(new WebEvent(new WebEventElement("click", "", "css=#submit", null)));


    automator.getEventInputProvider().setDefaultEventInputHandler("type", new WebEventInputHandler() {
      @Override
      public String getInput(final WebEventElement event, final WebElement element) {
        return "test input";
      }
    });

    automator.getEventInputProvider().addCustomEventInputHandler(new WebEventType("type", "css=#name"), new WebEventInputHandler() {
      @Override
      public String getInput(final WebEventElement event, final WebElement element) {
        return "test name";
      }
    });

    TestCaseExecutionResult<WebState, WebEventElement> result = automator.execute(testcase);

    int size = result.size();

    LOG.debug("State before execution");
    LOG.debug("\t:{}", result.getState(0));
    for (int i = 0; i < size; i++) {
      LOG.debug("Execute event {} : {}", i + 1, result.getTestCase().get(i));
      State nextState = i + 1 < result.getStateSize() ? result.getState(i + 1) : null;
      if (!result.isPassed()) {
        EventExecutionResult<WebEventElement> failureEventInfo = result.getFailureInducingEventInfo();
        if (failureEventInfo.getFailedEvent().equals(result.getTestCase().get(i))) {
          LOG.debug("Failed at this event (cause: {})", failureEventInfo.getCauseMessage());
          break;
        }
      }
      if (nextState != null) {
        LOG.debug("State after executing event {}", i + 1);
        LOG.debug("\t:{}", result.getState(i + 1));
      }
    }
    LOG.debug("ExecutionResult: {}", result);


//    WebState webState = automator.getStateExtractor().extractState("http://localhost:8080/page1.html");
//    System.out.println(webState.getWebDoc().getDocument().toString());
//
//    List<WebEvent> availableEvents = automator.getEventExtractor().getAvailableEvents(webState.getWebDoc());
//    for (WebEvent webEvent : availableEvents) {
//      System.out.println(webEvent.toString());
//    }
//    automator.execute(availableEvents.get(0));
//
//    webState = automator.getStateExtractor().extractState();
//    System.out.println(webState.getWebDoc().getDocument().toString());

    automator.quit();

  }
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#submit, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(2)>td:nth-child(2)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(2)>td:nth-child(3)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(2)>td:nth-child(4)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(2)>td:nth-child(5)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(2)>td:nth-child(6)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(2)>td:nth-child(7)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(2)>td:nth-child(8)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(3)>td:nth-child(2)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(3)>td:nth-child(3)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(3)>td:nth-child(4)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(3)>td:nth-child(5)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(3)>td:nth-child(6)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(3)>td:nth-child(7)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(3)>td:nth-child(8)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(4)>td:nth-child(2)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(4)>td:nth-child(3)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(4)>td:nth-child(4)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(4)>td:nth-child(5)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(4)>td:nth-child(6)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(4)>td:nth-child(7)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#contact_form>fieldset:nth-child(5)>table>tbody>tr:nth-child(4)>td:nth-child(8)>input[type="checkbox"], input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#yes_drives, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#no_drives, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#18to21, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#22to34, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#35to49, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#50to64, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#65, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#any_time, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#evenings, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=click, frameId=, id=#weekends, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=type, frameId=, id=#name, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=type, frameId=, id=#email, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=type, frameId=, id=#phone_number, input=null}], postConditions=[]}
//  WebEvent{preConditions=[], actions=[WebEventElement{eventType=type, frameId=, id=#mobile_number, input=null}], postConditions=[]}


}
