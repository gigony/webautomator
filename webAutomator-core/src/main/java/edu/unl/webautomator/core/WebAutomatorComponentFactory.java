package edu.unl.webautomator.core;

import edu.unl.webautomator.core.converter.WebTestCaseConverter;
import edu.unl.webautomator.core.executor.WebEventExecutor;
import edu.unl.webautomator.core.extractor.WebEventExtractor;
import edu.unl.webautomator.core.extractor.WebStateExtractor;
import edu.unl.webautomator.core.provider.WebEventInputProvider;

/**
 * Created by gigony on 12/10/14.
 */
public interface WebAutomatorComponentFactory {
  //    WebBrowser createWebBrowser(WebAutomator automator);
  WebTestCaseConverter createTestCaseConverter(WebAutomator automator);

  WebStateExtractor createStateExtractor(WebAutomator automator);

  WebEventExtractor createEventExtractor(WebAutomator automator);

  WebEventInputProvider createEventInputProvider(WebAutomator automator);

  WebEventExecutor createEventExecutor(WebAutomator automator);
}
