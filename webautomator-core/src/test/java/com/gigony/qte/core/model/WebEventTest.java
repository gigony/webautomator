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

package com.gigony.qte.core.model;

import com.gigony.qte.core.QTE;
import com.gigony.qte.core.StaticWebServer;
import com.gigony.qte.core.WebAutomator;
import com.gigony.qte.core.platform.WebBrowserType;
import com.gigony.qte.core.util.JacksonHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class WebEventTest {

  @Test
  public final void testWebEventSerialization() {
    StaticWebServer.start("/fixture/components/");

    WebAutomator automator = QTE.webAutomator(WebBrowserType.PHANTOMJS);

    WebState webState = automator.getStateExtractor().extractState("http://localhost:8080/button.html");
    System.out.println(webState.getWebDoc().getDocument().toString());

    List<WebEvent> availableEvents = automator.getEventExtractor().getAvailableEvents(webState.getWebDoc());
    for (WebEvent webEvent : availableEvents) {
      System.out.println(webEvent.toString());
    }
    automator.execute(availableEvents.get(0));

    JacksonHelper.printObjectToJson(availableEvents.get(0));
    String jsonStr = JacksonHelper.saveObjectToJsonString(availableEvents.get(0));

    System.out.println("-----");

    WebEvent clone = JacksonHelper.loadObjectFromJsonString(jsonStr, WebEvent.class);
    JacksonHelper.printObjectToJson(clone);
    String clonedJsonStr = JacksonHelper.saveObjectToJsonString(clone);
    Assert.assertEquals(jsonStr, clonedJsonStr);

    automator.quit();
  }

}
