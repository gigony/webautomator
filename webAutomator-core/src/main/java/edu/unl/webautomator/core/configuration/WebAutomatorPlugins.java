package edu.unl.webautomator.core.configuration;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import edu.unl.webautomator.core.exception.PluginNotFoundException;
import edu.unl.webautomator.core.util.BoonHelper;
import org.boon.json.serializers.CustomObjectSerializer;
import org.boon.json.serializers.JsonSerializerInternal;
import org.boon.primitive.CharBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gigony on 12/8/14.
 */

public final class WebAutomatorPlugins {

    private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorPlugins.class);

    private final ImmutableMultimap<String, String> pluginMap;

    WebAutomatorPlugins(Multimap<String, String> pluginMap) {
        this.pluginMap = ImmutableMultimap.copyOf(pluginMap);
    }


    public static WebAutomatorPlugins defaultPlugins() {
        WebAutomatorPluginsBuilder builder = new WebAutomatorPluginsBuilder();
        builder.setDefaultPlugins();
        return builder.build();
    }

    public String getPluginClassName(String pluginName) {
        Preconditions.checkNotNull(pluginName);
        ImmutableCollection<String> classNames = pluginMap.get(pluginName);

        if (classNames.isEmpty())
            throw new PluginNotFoundException("Plugin for '" + pluginName + "'is not found!");
        if (classNames.size() > 1) {
            LOG.warn("It seems that more than one plugin is attached for '" + pluginName + "'.");
        }
        return classNames.asList().get(0);
    }

    public ImmutableList<String> getPluginClassNameList(String pluginName) {
        Preconditions.checkNotNull(pluginName);
        ImmutableCollection<String> classNames = pluginMap.get(pluginName);

        if (classNames.isEmpty())
            throw new PluginNotFoundException("Plugin for '" + pluginName + "'is not found!");
        return classNames.asList();
    }


    public <T> Class<T> getPluginClass(String pluginName) {
        Preconditions.checkNotNull(pluginName);
        Class<T> klass = null;
        try {
            klass = (Class<T>)Class.forName(pluginName);
        } catch (ClassNotFoundException e) {
            LOG.error("Plugin {} cannot be loaded.",pluginName);
        }
        return klass;
    }

    public static CustomObjectSerializer<WebAutomatorPlugins> serializer(){
        return new CustomObjectSerializer<WebAutomatorPlugins>() {
            @Override
            public Class<WebAutomatorPlugins> type() {
                return WebAutomatorPlugins.class;
            }
            @Override
            public void serializeObject(JsonSerializerInternal serializer, WebAutomatorPlugins instance, CharBuf builder) {
                ImmutableMultimap<String, String> map = instance.pluginMap;
                BoonHelper.serializeMultiMap(serializer,map,builder);
            }
        };
    }
}
