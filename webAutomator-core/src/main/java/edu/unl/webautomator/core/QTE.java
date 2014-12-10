package edu.unl.webautomator.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.platform.WebBrowserType;

/**
 * Quick Test Enabler
 * Created by gigony on 12/6/14.
 */
public final class QTE {
    private QTE() {
    }

    public static WebAutomator webAutomator() {
        WebAutomatorConfiguration config = WebAutomatorConfiguration.defaultConfiguration();
        Injector injector = Guice.createInjector(new WebAutomatorModule(config));
        WebAutomator webAutomator = injector.getInstance(WebAutomator.class);
        return webAutomator;
    }

    public static WebAutomator webAutomator(final WebBrowserType browserType, final String webDriverBinPath) {
        WebAutomatorConfiguration config = WebAutomatorConfiguration.create(browserType, webDriverBinPath);
        Injector injector = Guice.createInjector(new WebAutomatorModule(config));
        WebAutomator webAutomator = injector.getInstance(WebAutomator.class);
        return webAutomator;
    }

    public static WebAutomator webAutomator(final WebAutomatorConfiguration configuration) {
        Injector injector = Guice.createInjector(new WebAutomatorModule(configuration));
        WebAutomator webAutomator = injector.getInstance(WebAutomator.class);
        return webAutomator;
    }
}
