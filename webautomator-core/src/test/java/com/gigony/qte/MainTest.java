/**
 * Copyright (C) 2014 Gigon Bae.
 */
package com.gigony.qte;

import com.gigony.qte.core.model.*;
import com.gigony.qte.core.platform.WebBrowserType;
import com.gigony.qte.core.QTE;
import com.gigony.qte.core.WebAutomator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class unit tests.
 *
 * @version 0.1
 */
public class MainTest {
  private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);


  public final void scenario1() throws Exception {
    WebAutomator automator = QTE.webAutomator(WebBrowserType.FIREFOX);
    WebTestCase testCase = automator.getTestCaseConverter().loadTestCase("/Users/gigony/Development/Repository/github/webautomator/webautomator-core/src/test/resources/fixture/testcases/sampleTestCase4.html", "html");
    TestCaseExecutionResult<WebState, WebEventElement> result = automator.execute(testCase);

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

    automator.quit();
  }
}
