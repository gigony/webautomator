package edu.unl.webautomator.core.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by gigony on 12/8/14.
 */
public class JacksonHelper {
    private static final Logger LOG = LoggerFactory.getLogger(JacksonHelper.class);

    private ObjectMapper mapper = new ObjectMapper();

    private static class SingletonHolder {
        private static final JacksonHelper instance = new JacksonHelper();
    }


    private JacksonHelper() {
        mapper.registerModule(new GuavaModule());
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }


    public static void saveObjectToJson(File file, Object obj) {
        try {
            SingletonHolder.instance.mapper.writeValue(file, obj);
        } catch (Exception e) {
            LOG.error("Failed to save object to Json file({}): {}", file.toString(), e.getMessage());
        }
    }

    public static <T> T loadObjectFromJson(File file, Class<T> klass) {
        try {
            return SingletonHolder.instance.mapper.readValue(file, klass);
        } catch (Exception e) {
            LOG.error("Failed to load object from Json file({}): {}", file.toString(), e.getMessage());
        }
        return null;
    }


}
