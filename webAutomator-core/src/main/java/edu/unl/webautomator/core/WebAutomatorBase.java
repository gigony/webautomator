package edu.unl.webautomator.core;

import com.google.inject.Inject;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.converter.TestCaseConverter;
import edu.unl.webautomator.core.executor.EventExecutor;
import edu.unl.webautomator.core.extractor.EventExtractor;
import edu.unl.webautomator.core.extractor.StateExtractor;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.provider.EventInputProvider;
import org.openqa.selenium.WebDriver;

/**
 * Created by gigony on 12/6/14.
 */
public class WebAutomatorBase implements WebAutomator {

    private final WebAutomatorConfiguration configuration;
    private final WebBrowser webBrowser;
    private final WebDriver webDriver;
    private final TestCaseConverter testCaseConverter;
    private final StateExtractor stateExtractor;
    private final EventExtractor eventExtractor;
    private final EventInputProvider eventInputProvider;
    private final EventExecutor eventExecutor;


    @Inject
    WebAutomatorBase(final WebAutomatorConfiguration config, final WebBrowser browser, final TestCaseConverter converter, final StateExtractor sExtractor, final EventExtractor eExtractor, final EventInputProvider eInputProvider, final EventExecutor eExecutor) {
        this.configuration = config;
        this.webBrowser = browser;
        this.webDriver = browser.getWebDriver();
        this.testCaseConverter = converter;
        this.stateExtractor = sExtractor;
        this.eventExtractor = eExtractor;
        this.eventInputProvider = eInputProvider;
        this.eventExecutor = eExecutor;
    }

    @Override
    public final WebAutomatorConfiguration getConfiguration() {
        return this.configuration;
    }

    @Override
    public final WebBrowser getWebBrowser() {
        return this.webBrowser;
    }

    @Override
    public final WebDriver getWebDriver() {
        return this.webDriver;
    }

    @Override
    public final WebDriverBackedSelenium createSelenium(final String baseUrl) {
        return new WebDriverBackedSelenium(this.getWebDriver(), baseUrl);
    }

    @Override
    public final TestCaseConverter getTestCaseConverter() {
        return this.testCaseConverter;
    }

    @Override
    public final StateExtractor getStateExtractor() {
        return this.stateExtractor;
    }

    @Override
    public final EventExtractor getEventExtractor() {
        return this.eventExtractor;
    }

    @Override
    public final EventInputProvider getEventInputProvider() {
        return this.eventInputProvider;
    }

    @Override
    public final EventExecutor getEventExecutor() {
        return this.eventExecutor;
    }

    @Override
    public final void quit() {
        this.getWebBrowser().getWebDriver().quit();

    }
}
