package edu.unl.webautomator.core.platform.browser;

import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.configuration.WebProxyConfiguration;
import edu.unl.webautomator.core.configuration.WebProxyType;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by gigony on 12/9/14.
 */
public class PhantomJsWebBrowser extends BasicWebBrowser {
    public PhantomJsWebBrowser(final WebBrowserConfiguration browserConfiguration) {
        super(browserConfiguration);
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
}
