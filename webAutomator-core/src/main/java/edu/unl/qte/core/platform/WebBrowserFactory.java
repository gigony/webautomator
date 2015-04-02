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

package edu.unl.qte.core.platform;

import edu.unl.qte.core.configuration.WebAutomatorConfiguration;
import edu.unl.qte.core.configuration.WebBrowserConfiguration;
import edu.unl.qte.core.platform.browser.*;


/**
 * Created by gigony on 12/9/14.
 */
public final class WebBrowserFactory {
  private WebBrowserFactory() {
  }

  public static WebBrowser create(final WebAutomatorConfiguration configuration) {
    WebBrowserConfiguration browserConfiguration = configuration.getBrowserConfiguration();

    WebBrowserType browserType = browserConfiguration.getBrowserType();
    switch (browserType) {
      case CHROME:
        return new ChromeWebBrowser(configuration);
      case FIREFOX:
        return new FireFoxWebBrowser(configuration);
      case IEXPLORER:
        return new IExplorerWebBrowser(configuration);
      case PHANTOMJS:
        return new PhantomJsWebBrowser(configuration);
      case REMOTE:
        return new RemoteWebBrowser(configuration);
      default:
        throw new RuntimeException("No available web browser (" + browserType.name() + ")");
    }

  }
}