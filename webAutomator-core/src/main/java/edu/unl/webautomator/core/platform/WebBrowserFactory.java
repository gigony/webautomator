package edu.unl.webautomator.core.platform;

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

    public static WebBrowser create(final WebBrowserConfiguration browserConfiguration) {
        WebBrowserType browserType = browserConfiguration.getBrowserType();
        switch (browserType) {
            case CHROME:
                return new ChromeWebBrowser(browserConfiguration);
            case FIREFOX:
                return new FireFoxWebBrowser(browserConfiguration);
            case IEXPLORER:
                return new IExplorerWebBrowser(browserConfiguration);
            case PHANTOMJS:
                return new PhantomJsWebBrowser(browserConfiguration);
            case REMOTE:
                return new RemoteWebBrowser(browserConfiguration);
            default:
                throw new RuntimeException("No available web browser (" + browserType.name() + ")");
        }

    }
}
