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

import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.configuration.WebProxyConfiguration;
import edu.unl.webautomator.core.configuration.WebProxyType;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Deque;

/**
 * Created by gigony on 12/9/14.
 */
public class PhantomJsWebBrowser extends BasicWebBrowser {
  public PhantomJsWebBrowser(final WebAutomatorConfiguration configuration) {
    super(configuration);

  }

  @Override
  protected final void configureWebBrowser(final WebBrowserConfiguration browserConfiguration) {
    PhantomJSDriver phantomJsDriver;

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--webdriver-loglevel=WARN"});
    capabilities.setCapability("takesScreenshot", true);


    WebProxyConfiguration proxyConf = browserConfiguration.getProxyConfiguration();
    if (proxyConf != null && proxyConf.getWebProxyType() != WebProxyType.NONE) {
      String proxyAddr = String.format("--proxy=%s:%d", proxyConf.getHostAddr(), proxyConf.getPortNum());
      String proxyType = "--proxy-type=http";
      String[] capArgs = new String[]{proxyAddr, proxyType};
      capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, capArgs);
    }

    phantomJsDriver = new PhantomJSDriver(capabilities);

    setWebDriver(phantomJsDriver);


  }

  /**
   * Implementation of #moveToParentFrameImpl is different because PhantomJs doesn't seem to work well
   * with 'parentFrame()' method.
   */
  @Override
  protected final void moveToParentFrameImpl() {

    Deque<String> frameStack = getFrameStack();
    if (!frameStack.isEmpty()) {
      frameStack.pop();
    }
    moveToAbsoluteFrame(getFrameId());
  }
}
