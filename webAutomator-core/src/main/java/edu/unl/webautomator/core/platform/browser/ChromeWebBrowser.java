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
import edu.unl.webautomator.core.configuration.WebProxyType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

/**
 * Created by gigony on 12/9/14.
 */
public class ChromeWebBrowser extends BasicWebBrowser {
    public ChromeWebBrowser(final WebAutomatorConfiguration configuration) {
        super(configuration);

    }

    @Override
    protected final void configureWebBrowser(final WebBrowserConfiguration browserConfiguration) {
        ChromeDriver chromeDriver;
        WebProxyConfiguration proxyConfiguration = browserConfiguration.getProxyConfiguration();
        ChromeOptions chromeOption = new ChromeOptions();

        String webDriverBinPath = browserConfiguration.getWebDriverBinaryPath();

        if (Strings.isNullOrEmpty(webDriverBinPath) || !(new File(webDriverBinPath).exists())) {
            webDriverBinPath = System.getProperty("webdriver.chrome.driver");
        }

        if (!Strings.isNullOrEmpty(webDriverBinPath)) {
            System.setProperty("webdriver.chrome.driver", webDriverBinPath);
        }

        if (proxyConfiguration != null && proxyConfiguration.getWebProxyType() != WebProxyType.NONE) {
            String proxyOptionStr = String.format("--proxy-server=http://%s:%d", proxyConfiguration.getHostAddr(), proxyConfiguration.getPortNum());
            chromeOption.addArguments(proxyOptionStr);
        }

        String language = browserConfiguration.getLanguage();
        if (!Strings.isNullOrEmpty(language)) {
            chromeOption.addArguments(String.format("--lang=%s", language));
        }
        chromeDriver = new ChromeDriver(chromeOption);

        setWebDriver(chromeDriver);
    }

    @Override
    protected final void moveToParentFrameImpl() {
        this.getWebDriver().switchTo().parentFrame();
        if (!getFrameStack().isEmpty()) {
            getFrameStack().pop();
        }
    }
}
