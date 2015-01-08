package edu.unl.webautomator.core.platform.browser;

import com.google.common.base.Preconditions;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
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
