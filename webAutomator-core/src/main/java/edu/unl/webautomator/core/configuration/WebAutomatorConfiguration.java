package edu.unl.webautomator.core.configuration;

import org.boon.IO;
import org.boon.json.JsonFactory;
import org.boon.json.JsonSerializer;
import org.boon.json.JsonSerializerFactory;
import org.boon.json.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by gigony on 12/6/14.
 */
public class WebAutomatorConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorConfiguration.class);

    WebAutomatorPlugins plugins = WebAutomatorPlugins.defaultPlugins();
    WebBrowserConfiguration browserConfiguration;
    WebProxyConfiguration proxyConfiguration = WebProxyConfiguration.noProxy();
    WebEventTypes webEventTypes;

    WebAutomatorConfiguration() {
    }

    public <T> Class<T> getPluginClass(String pluginName) {
        return plugins.getPluginClass(pluginName);
    }


    public static WebAutomatorConfiguration defaultConfiguration() {

        return null;
    }


    //    public static CustomObjectSerializer<WebAutomatorConfiguration> serializer(){
//        return new CustomObjectSerializer<WebAutomatorConfiguration>() {
//            @Override
//            public Class<WebAutomatorConfiguration> type() {
//                return WebAutomatorConfiguration.class;
//            }
//            @Override
//            public void serializeObject(JsonSerializerInternal serializer, WebAutomatorConfiguration instance, CharBuf builder) {
//
//
//            }
//        };
//
//    }
    public void exportToJson(String fileName) {

        JsonSerializerFactory factory = new JsonSerializerFactory();
        factory.addTypeSerializer(WebAutomatorPlugins.class, WebAutomatorPlugins.serializer());
        JsonSerializer serializer = factory.create();
        File dest = new File(fileName);
        IO.write(IO.path(dest.toString()), serializer.serialize(this).toString());
    }

    public void importFromJson(String fileName) {
        ObjectMapper mapper = new JsonFactory().create();

    }
}
