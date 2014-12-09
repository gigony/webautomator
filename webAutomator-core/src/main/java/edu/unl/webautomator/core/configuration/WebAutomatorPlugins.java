package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import edu.unl.webautomator.core.exception.PluginNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gigony on 12/8/14.
 */


public final class WebAutomatorPlugins {

    private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorPlugins.class);

    private final ImmutableMultimap<String, String> plugins;

    @JsonCreator
    WebAutomatorPlugins(@JsonProperty("plugins") Multimap<String, String> plugins) {
        this.plugins = ImmutableMultimap.copyOf(plugins);
    }

    public static WebAutomatorPlugins defaultPlugins() {
        WebAutomatorPluginsBuilder builder = new WebAutomatorPluginsBuilder();
        builder.setDefaultPlugins();
        return builder.build();
    }

    public ImmutableMultimap<String, String> getPlugins() {
        return plugins;
    }

    public String getPluginClassName(String pluginName) {
        Preconditions.checkNotNull(pluginName);
        ImmutableCollection<String> classNames = plugins.get(pluginName);

        if (classNames.isEmpty())
            throw new PluginNotFoundException("Plugin for '" + pluginName + "'is not found!");
        if (classNames.size() > 1) {
            LOG.warn("It seems that more than one plugin is attached for '" + pluginName + "'.");
        }
        return classNames.asList().get(0);
    }

    public ImmutableList<String> getPluginClassNameList(String pluginName) {
        Preconditions.checkNotNull(pluginName);
        ImmutableCollection<String> classNames = plugins.get(pluginName);

        if (classNames.isEmpty())
            throw new PluginNotFoundException("Plugin for '" + pluginName + "'is not found!");
        return classNames.asList();
    }

    public <T> Class<T> getPluginClass(String pluginName) {
        Preconditions.checkNotNull(pluginName);
        Class<T> klass = null;
        try {
            String pluginClassName = getPluginClassName(pluginName);
            LOG.info("Get plugin: {} (from {}).", pluginName, pluginClassName);
            klass = (Class<T>) Class.forName(pluginClassName);
        } catch (ClassNotFoundException e) {
            LOG.error("Plugin {} cannot be loaded.", pluginName);
        }
        return klass;
    }
}
