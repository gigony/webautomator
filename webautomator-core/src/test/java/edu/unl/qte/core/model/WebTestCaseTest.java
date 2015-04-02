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

package edu.unl.qte.core.model;

import edu.unl.qte.core.util.JacksonHelper;
import org.junit.Test;

public class WebTestCaseTest {


  @Test
  public final void testJsonExport() {
    WebTestCase testcase = new WebTestCase("http://localhost:8080");

    testcase.add(new WebEvent(new WebEventElement("open", null, "/page1.html", null)));
    testcase.add(new WebEvent(new WebEventElement("type", null, "css=#name", null)));
    testcase.add(new WebEvent(new WebEventElement("click", null, "css=#yes_drives", null)));
    testcase.add(new WebEvent(new WebEventElement("click", null, "css=#any_time", null)));
    testcase.add(new WebEvent(new WebEventElement("type", null, "css=#mobile_number", null)));
    testcase.add(new WebEvent(new WebEventElement("click", null, "css=#submit", null)));
    JacksonHelper.printObjectToJson(testcase);


  }

}
