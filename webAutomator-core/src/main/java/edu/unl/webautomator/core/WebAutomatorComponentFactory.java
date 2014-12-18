package edu.unl.webautomator.core;

import edu.unl.webautomator.core.converter.TestCaseConverter;
import edu.unl.webautomator.core.executor.EventExecutor;
import edu.unl.webautomator.core.extractor.EventExtractor;
import edu.unl.webautomator.core.extractor.StateExtractor;
import edu.unl.webautomator.core.provider.EventInputProvider;

/**
 * Created by gigony on 12/10/14.
 */
public interface WebAutomatorComponentFactory {
//    WebBrowser createWebBrowser(WebAutomator automator);
    TestCaseConverter createTestCaseConverter(WebAutomator automator);
    StateExtractor createStateExtractor(WebAutomator automator);
    EventExtractor createEventExtractor(WebAutomator automator);
    EventInputProvider createEventInputProvider(WebAutomator automator);
    EventExecutor createEventExecutor(WebAutomator automator);
}
