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

package com.gigony.qte.core;

import com.gigony.qte.core.converter.WebTestCaseConverter;
import com.gigony.qte.core.executor.WebEventExecutor;
import com.gigony.qte.core.extractor.WebEventExtractor;
import com.gigony.qte.core.extractor.WebStateExtractor;
import com.gigony.qte.core.provider.WebEventInputProvider;
import com.gigony.qte.core.executor.WebTestCaseExecutor;

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

  WebTestCaseExecutor createTestCaseExecutor(WebAutomator automator);
}
