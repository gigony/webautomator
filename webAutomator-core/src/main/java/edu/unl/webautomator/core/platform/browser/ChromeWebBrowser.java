package edu.unl.webautomator.core.platform.browser;

import com.google.common.base.Strings;
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
    public ChromeWebBrowser(final WebBrowserConfiguration browserConfiguration) {
        super(browserConfiguration);

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
