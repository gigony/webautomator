package edu.unl.webautomator.core.converter;
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


import com.fasterxml.jackson.core.type.TypeReference;
import edu.unl.webautomator.core.QTE;
import edu.unl.webautomator.core.StaticWebServer;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.model.TestCase;
import edu.unl.webautomator.core.model.WebEvent;
import edu.unl.webautomator.core.model.WebEventElement;
import edu.unl.webautomator.core.model.WebTestCase;
import edu.unl.webautomator.core.platform.WebBrowserType;
import edu.unl.webautomator.core.util.JacksonHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class WebTestCaseConverterTest {


  @Test
  public final void testSaveTestCase() throws Exception {

    WebTestCase testcase = new WebTestCase("http://localhost:8080");

    testcase.add(new WebEvent(new WebEventElement("open", "", "/page1.html", null)));
    testcase.add(new WebEvent(new WebEventElement("type", "", "css=#name", "name")));
    testcase.add(new WebEvent(new WebEventElement("click", "", "css=#yes_drives", null)));
    testcase.add(new WebEvent(new WebEventElement("click", "", "css=#any_time", null)));
    testcase.add(new WebEvent(new WebEventElement("type", "", "css=#mobile_number", "012-345-6780")));
    testcase.add(new WebEvent(new WebEventElement("click", "", "css=#submit", null)));

    new WebTestCaseConverter(null).saveTestCase("test.json", "json", testcase);
    JacksonHelper.printObjectToJson(testcase);

  }

  @Test
  public final void testLoadTestCase() throws Exception {
    StaticWebServer.start("/fixture/homepage/");
    WebAutomator automator = QTE.webAutomator(WebBrowserType.FIREFOX);
    WebTestCase testCase = new WebTestCaseConverter(null).loadTestCase("test.json", "json");
    automator.execute(testCase);
    automator.quit();
  }

  @Test
  public final void testLoadHtmlTestCase() throws Exception {
    String fileName = ClassLoader.getSystemResource("fixture/testcases/sampleTestCase.html").toURI().getPath();
    WebTestCase testCase = new WebTestCaseConverter(null).loadTestCase(fileName, "html");
    JacksonHelper.printObjectToJson(testCase);
  }

  @Test
  public final void testSaveHtmlTestCase() throws Exception {
    String fileName = ClassLoader.getSystemResource("fixture/testcases/sampleTestCase3.html").toURI().getPath();
    WebTestCase testCase = new WebTestCaseConverter(null).loadTestCase(fileName, "html");
    JacksonHelper.printObjectToJson(testCase);
    new WebTestCaseConverter(null).saveTestCase("test.html", "html", testCase);
  }

  @Test
  public final void testIsSupported() throws Exception {
    Assert.assertEquals(new WebTestCaseConverter(null).isSupported("json"), true);
    Assert.assertEquals(new WebTestCaseConverter(null).isSupported("html"), true);
  }

  @Test
  public final void testSeleniumAPI() throws IOException {
    String[] test = new String[3];
    test[0] = "a";
    test[1] = "b";
    test[2] = "c";
    String[] test2 = JacksonHelper.loadObjectFromJsonString("[ \"a\", \"b\", \"c\" ]", String[].class);
    Map<String, String[]> test3 = JacksonHelper.getObjectMapper().readValue("{\"a\":[\"a\",\"a2\",\"ac\"], \"b\": [], \"c\":[\"def\"]}", new TypeReference<Map<String, String[]>>() {
    });
    JacksonHelper.printObjectToJson(test2);
    System.out.println(test.getClass().getTypeName());
    System.out.println(test3.get("a")[0] + " " + test3.get("c")[0]);

    ArrayList<String> test4 = new ArrayList<String>();
    test4.add("c");
    test4.add("d");
    JacksonHelper.printObjectToJson(test4);
    test4 = JacksonHelper.loadObjectFromJsonString("[ \"a\", \"b\", \"c\" ]", new TypeReference<ArrayList<String>>() {
    });
    JacksonHelper.printObjectToJson(test4);
  }
}
