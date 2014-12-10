package edu.unl.webautomator.core.platform.browser;

import com.google.common.base.Preconditions;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.platform.WebBrowser;
import org.openqa.selenium.WebDriver;

/**
 * Created by gigony on 12/9/14.
 */
public abstract class BasicWebBrowser implements WebBrowser {

    private WebDriver webDriver;


    public BasicWebBrowser(final WebBrowserConfiguration browserConfiguration) {
        this.configureWebBrowser(browserConfiguration);
        Preconditions.checkNotNull(this.webDriver);
    }

    protected abstract void configureWebBrowser(WebBrowserConfiguration browserConfiguration);

    protected final void setWebDriver(final WebDriver driver) {
        this.webDriver = driver;
    }

    public final WebDriver getWebDriver() {
        return this.webDriver;
    }
}
