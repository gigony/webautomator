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
