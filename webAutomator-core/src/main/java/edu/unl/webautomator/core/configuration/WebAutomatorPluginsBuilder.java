package edu.unl.webautomator.core.configuration;

import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * Created by gigony on 12/8/14.
 */
public class WebAutomatorPluginsBuilder {

    Multimap<String, String> pluginNameMap = LinkedListMultimap.create();

    public WebAutomatorPluginsBuilder() {
    }


    public WebAutomatorPluginsBuilder setPlugin(String pluginName, String pluginClassName) {
        Preconditions.checkNotNull(pluginName);
        Preconditions.checkNotNull(pluginClassName);

        pluginNameMap.removeAll(pluginName);
        pluginNameMap.put(pluginName, pluginClassName);
        return this;
    }

    public WebAutomatorPluginsBuilder appendPlugin(String pluginName, String pluginClassName) {
        Preconditions.checkNotNull(pluginName);
        Preconditions.checkNotNull(pluginClassName);

        pluginNameMap.put(pluginName, pluginClassName);
        return this;
    }


    public void setDefaultPlugins() {
        setPlugin("core.converter.testcase", "edu.unl.webautomator.core.converter.WebTestCaseConverter");
        setPlugin("core.extractor.state", "edu.unl.webautomator.core.extractor.WebStateExtractor");
        setPlugin("core.extractor.event", "edu.unl.webautomator.core.extractor.WebEventExtractor");
        setPlugin("core.provider.eventinput", "edu.unl.webautomator.core.provider.WebEventInputProvider");
        setPlugin("core.executor.event", "edu.unl.webautomator.core.executor.WebEventExecutor");
    }

    public WebAutomatorPlugins build() {
        return new WebAutomatorPlugins(pluginNameMap);
    }
}
