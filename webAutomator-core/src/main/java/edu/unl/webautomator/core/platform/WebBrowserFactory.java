package edu.unl.webautomator.core.platform;

import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.platform.browser.ChromeWebBrowser;
import edu.unl.webautomator.core.platform.browser.FireFoxWebBrowser;
import edu.unl.webautomator.core.platform.browser.IExplorerWebBrowser;
import edu.unl.webautomator.core.platform.browser.PhantomJsWebBrowser;
import edu.unl.webautomator.core.platform.browser.RemoteWebBrowser;


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
