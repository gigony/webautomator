package edu.unl.webautomator.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.converter.TestCaseConverter;
import edu.unl.webautomator.core.executor.EventExecutor;
import edu.unl.webautomator.core.extractor.EventExtractor;
import edu.unl.webautomator.core.extractor.StateExtractor;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.platform.WebBrowserFactory;
import edu.unl.webautomator.core.provider.EventInputProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gigony on 12/6/14.
 */
public class WebAutomatorModule extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorModule.class);
    private WebAutomatorConfiguration config;

    public WebAutomatorModule(final WebAutomatorConfiguration configuration) {
        this.config = configuration;
    }

    @Override
    protected final void configure() {
        bind(WebAutomatorConfiguration.class).toInstance(this.config);

        bind(WebAutomator.class).to(WebAutomatorBase.class);
        bind(TestCaseConverter.class).to(this.config.<TestCaseConverter>getPluginClass("core.converter.testcase"));
        bind(StateExtractor.class).to(this.config.<StateExtractor>getPluginClass("core.extractor.state"));
        bind(EventExtractor.class).to(this.config.<EventExtractor>getPluginClass("core.extractor.event"));
        bind(EventInputProvider.class).to(this.config.<EventInputProvider>getPluginClass("core.provider.eventinput"));
        bind(EventExecutor.class).to(this.config.<EventExecutor>getPluginClass("core.executor.event"));
    }

    @Provides
    public final WebBrowser provideWebBrowser() {
        return WebBrowserFactory.create(this.config.getBrowserConfiguration());
    }
}
