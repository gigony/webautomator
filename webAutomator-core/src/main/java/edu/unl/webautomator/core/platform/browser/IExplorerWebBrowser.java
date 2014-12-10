package edu.unl.webautomator.core.platform.browser;

import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * Created by gigony on 12/9/14.
 */
public class IExplorerWebBrowser extends BasicWebBrowser {
    public IExplorerWebBrowser(final WebBrowserConfiguration browserConfiguration) {
        super(browserConfiguration);
    }

    @Override
    protected final void configureWebBrowser(final WebBrowserConfiguration browserConfiguration) {
        InternetExplorerDriver iExplorerDriver = new InternetExplorerDriver();

        setWebDriver(iExplorerDriver);
    }
}
