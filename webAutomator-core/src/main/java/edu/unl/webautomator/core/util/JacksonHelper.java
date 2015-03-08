/*
 * Copyright 2015 Gigon Bae
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package edu.unl.webautomator.core.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by gigony on 12/8/14.
 */
public final class JacksonHelper {
  private static final Logger LOG = LoggerFactory.getLogger(JacksonHelper.class);

  private ObjectMapper mapper = new ObjectMapper();


  private static class SingletonHolder {
    private static final JacksonHelper INSTANCE = new JacksonHelper();
  }

  private JacksonHelper() {
    this.mapper.registerModule(new GuavaModule());
    this.mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //http://wiki.fasterxml.com/JacksonFAQ#Deserializing_Abstract_types
//    Version version = new Version(1, 0, 0, "SNAPSHOT", "edu.unl", "qte"); // maven/OSGi style version
//    SimpleModule module = new SimpleModule("MyModuleName", version);
//    module.addAbstractTypeMapping(Event.class, WebEvent.class);
//    his.mapper.registerModule(module);
//
//    SimpleAbstractTypeResolver abstractTypes = new SimpleAbstractTypeResolver();
//    abstractTypes.addMapping(Event.class, WebEvent.class);
//    DeserializerFactory df = this.mapper.getDeserializationContext().getFactory().withAbstractTypeResolver(abstractTypes);
//    this.mapper.get ((DefaultDeserializationContext) this.mapper.getDeserializationContext()).with(df);


//    DeserializerFactory df = mapper._deserializationContext._factory.withAbstractTypeResolver(resolver);

  }

  public static ObjectMapper getObjectMapper() {
    return SingletonHolder.INSTANCE.mapper;
  }


  public static void saveObjectToJsonFile(final File file, final Object obj) {
    try {
      SingletonHolder.INSTANCE.mapper.writeValue(file, obj);
    } catch (Exception e) {
      LOG.error("Failed to save object to Json file({}): {}", file.toString(), e.getMessage());
    }
  }

  public static <T> T loadObjectFromJsonFile(final File file, final Class<T> klass) {
    try {
      return SingletonHolder.INSTANCE.mapper.readValue(file, klass);
    } catch (Exception e) {
      LOG.error("Failed to load object from Json file({}): {}", file.toString(), e.getMessage());
    }
    return null;
  }

  public static <T> T loadObjectFromJsonFile(final File file, final TypeReference typeRef) {
    try {
      return SingletonHolder.INSTANCE.mapper.readValue(file, typeRef);
    } catch (Exception e) {
      LOG.error("Failed to load object from Json file({}): {}", file.toString(), e.getMessage());
    }
    return null;
  }

  public static String saveObjectToJsonString(final Object obj) {
    try {
      return SingletonHolder.INSTANCE.mapper.writeValueAsString(obj);
    } catch (Exception e) {
      LOG.error("Failed to save object to Json string: {}", e.getMessage());
    }
    return null;
  }

  public static <T> T loadObjectFromJsonString(final String jsonStr, final Class<T> klass) {
    try {
      return SingletonHolder.INSTANCE.mapper.readValue(jsonStr, klass);
    } catch (Exception e) {
      LOG.error("Failed to load object from Json string({}): {}", jsonStr, e.getMessage());
    }
    return null;
  }

  public static <T> T loadObjectFromJsonString(final String jsonStr, final TypeReference typeRef) {
    try {
      return SingletonHolder.INSTANCE.mapper.readValue(jsonStr, typeRef);
    } catch (Exception e) {
      LOG.error("Failed to load object from Json string({}): {}", jsonStr, e.getMessage());
    }
    return null;
  }

  public static void printObjectToJson(final Object obj) {
    try {
      System.out.println(SingletonHolder.INSTANCE.mapper.writeValueAsString(obj));
    } catch (Exception e) {
      LOG.error("Failed to print object to Json: {}", e.getMessage());
    }
  }


}
