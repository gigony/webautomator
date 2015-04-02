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

import com.gigony.qte.core.configuration.WebAutomatorConfiguration;
import com.gigony.qte.core.converter.TestCaseConverter;
import com.gigony.qte.core.executor.EventExecutor;
import com.gigony.qte.core.provider.EventInputProvider;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.gigony.qte.core.executor.TestCaseExecutor;
import com.gigony.qte.core.extractor.EventExtractor;
import com.gigony.qte.core.extractor.StateExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gigony on 12/6/14.
 */
public class WebAutomatorModule extends AbstractModule {

  private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorModule.class);
  private WebAutomatorConfiguration config;

  public WebAutomatorModule(final WebAutomatorConfiguration configuration) {
    this.config = configuration;
  }

  @Override
  protected final void configure() {
    bind(WebAutomatorConfiguration.class).toInstance(this.config);
    bind(WebAutomator.class).to(WebAutomatorBase.class);
    install(new FactoryModuleBuilder()
      .implement(TestCaseConverter.class, this.config.<TestCaseConverter>getPluginClass("core.converter.testcase"))
      .implement(StateExtractor.class, this.config.<StateExtractor>getPluginClass("core.extractor.state"))
      .implement(EventExtractor.class, this.config.<EventExtractor>getPluginClass("core.extractor.event"))
      .implement(EventInputProvider.class, this.config.<EventInputProvider>getPluginClass("core.provider.eventinput"))
      .implement(EventExecutor.class, this.config.<EventExecutor>getPluginClass("core.executor.event"))
      .implement(TestCaseExecutor.class, this.config.<TestCaseExecutor>getPluginClass("core.executor.testcase"))
      .build(WebAutomatorComponentFactory.class));
  }


}
