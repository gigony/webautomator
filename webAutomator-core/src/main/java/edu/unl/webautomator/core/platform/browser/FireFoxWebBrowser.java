package edu.unl.webautomator.core.platform.browser;

import com.google.common.base.Strings;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.configuration.WebProxyConfiguration;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Created by gigony on 12/9/14.
 */
public class FireFoxWebBrowser extends BasicWebBrowser {
    public FireFoxWebBrowser(final WebBrowserConfiguration browserConfiguration) {
        super(browserConfiguration);
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
