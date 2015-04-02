/**
 * Copyright (C) 2014 Gigon Bae.
 */
package com.gigony.qte;

import com.gigony.qte.core.platform.WebBrowserType;
import com.gigony.qte.core.QTE;
import com.gigony.qte.core.WebAutomator;
import com.gigony.qte.core.model.WebTestCase;

/**
 * Main class unit tests.
 *
 * @version 0.1
 */
public class MainTest {


  public final void scenario1() throws Exception {
    WebAutomator automator = QTE.webAutomator(WebBrowserType.FIREFOX);
    WebTestCase testCase = automator.getTestCaseConverter().loadTestCase("/Users/gigony/Development/Repository/github/webautomator/webautomator-core/src/test/resources/fixture/testcases/sampleTestCase4.html", "html");
    automator.execute(testCase);
    automator.quit();
  }
}
