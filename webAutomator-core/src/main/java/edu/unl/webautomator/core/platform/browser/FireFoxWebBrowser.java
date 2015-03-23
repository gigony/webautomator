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

package edu.unl.webautomator.core.platform.browser;

import com.google.common.base.Strings;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.configuration.WebProxyConfiguration;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Created by gigony on 12/9/14.
 */
public class FireFoxWebBrowser extends BasicWebBrowser {
  public FireFoxWebBrowser(final WebAutomatorConfiguration configuration) {
    super(configuration);

  }

  @Override
  protected final void configureWebBrowser(final WebBrowserConfiguration browserConfiguration) {
    FirefoxDriver fireFoxDriver;
    FirefoxProfile profile = new FirefoxProfile();


    WebProxyConfiguration proxyConfiguration = browserConfiguration.getProxyConfiguration();
    if (proxyConfiguration != null) {
      profile.setPreference("network.proxy.http", proxyConfiguration.getHostAddr());
      profile.setPreference("network.proxy.http_port", proxyConfiguration.getPortNum());
      profile.setPreference("network.proxy.type", proxyConfiguration.getWebProxyType().toIntValue());
      profile.setPreference("network.proxy.no_proxies_on", "");
    }

    String lang = browserConfiguration.getLanguage();
    if (!Strings.isNullOrEmpty(lang)) {
      profile.setPreference("intl.accept_languages", lang);
    }

    fireFoxDriver = new FirefoxDriver(profile);

    setWebDriver(fireFoxDriver);
  }

  @Override
  protected final void moveToParentFrameImpl() {
    this.getWebDriver().switchTo().parentFrame();
    if (!getFrameStack().isEmpty()) {
      getFrameStack().pop();
    }
  }
}
