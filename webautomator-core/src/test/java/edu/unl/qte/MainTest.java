/**
 * Copyright (C) 2014 Gigon Bae.
 */
package edu.unl.qte;

import edu.unl.qte.core.QTE;
import edu.unl.qte.core.WebAutomator;
import edu.unl.qte.core.model.WebTestCase;
import edu.unl.qte.core.platform.WebBrowserType;
import org.junit.Test;

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
