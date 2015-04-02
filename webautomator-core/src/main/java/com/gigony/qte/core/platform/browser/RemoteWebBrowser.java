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

package com.gigony.qte.core.platform.browser;

import com.google.common.base.Preconditions;
import com.gigony.qte.core.configuration.WebBrowserConfiguration;
import com.gigony.qte.core.configuration.WebAutomatorConfiguration;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gigony on 12/9/14.
 */
public class RemoteWebBrowser extends BasicWebBrowser {
  private static final Logger LOG = LoggerFactory.getLogger(RemoteWebBrowser.class);

  public RemoteWebBrowser(final WebAutomatorConfiguration configuration) {
    super(configuration);

  }

  @Override
  protected final void configureWebBrowser(final WebBrowserConfiguration browserConfiguration) {
    Preconditions.checkNotNull(browserConfiguration.getRemoteHubUrl());

    RemoteWebDriver remoteWebDriver;

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setPlatform(Platform.ANY);
    URL url;
    try {
      url = new URL(browserConfiguration.getRemoteHubUrl());
    } catch (MalformedURLException e) {
      throw new RuntimeException("Malformed hub url is given! :" + e.getMessage());
    }

    HttpCommandExecutor executor = null;
    try {
      executor = new HttpCommandExecutor(url);
    } catch (Exception e) {
      throw new RuntimeException("Unknown error occurred while creating HttpCommandExecutor object!");
    }

    remoteWebDriver = new RemoteWebDriver(executor, capabilities);

    setWebDriver(remoteWebDriver);
  }

  @Override
  protected final void moveToParentFrameImpl() {
    this.getWebDriver().switchTo().parentFrame();
    if (!getFrameStack().isEmpty()) {
      getFrameStack().pop();
    }
  }
}
