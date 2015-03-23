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

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.platform.WebBrowserType;

/**
 * Quick Test Enabler
 * Created by gigony on 12/6/14.
 */
public final class QTE {
  private QTE() {
  }

  public static WebAutomator webAutomator() {
    WebAutomatorConfiguration config = WebAutomatorConfiguration.defaultConfiguration();
    Injector injector = Guice.createInjector(new WebAutomatorModule(config));
    WebAutomator webAutomator = injector.getInstance(WebAutomator.class);
    return webAutomator;
  }

  public static WebAutomator webAutomator(final WebBrowserType browserType) {
    WebAutomatorConfiguration config = WebAutomatorConfiguration.create(browserType);
    Injector injector = Guice.createInjector(new WebAutomatorModule(config));
    WebAutomator webAutomator = injector.getInstance(WebAutomator.class);
    return webAutomator;
  }

  public static WebAutomator webAutomator(final WebBrowserType browserType, final String webDriverBinPath) {
    WebAutomatorConfiguration config = WebAutomatorConfiguration.create(browserType, webDriverBinPath);
    Injector injector = Guice.createInjector(new WebAutomatorModule(config));
    WebAutomator webAutomator = injector.getInstance(WebAutomator.class);
    return webAutomator;
  }

  public static WebAutomator webAutomator(final WebAutomatorConfiguration configuration) {
    Injector injector = Guice.createInjector(new WebAutomatorModule(configuration));
    WebAutomator webAutomator = injector.getInstance(WebAutomator.class);
    return webAutomator;
  }
}
