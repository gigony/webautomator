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

package com.gigony.qte.core.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gigony.qte.core.exception.PluginNotFoundException;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gigony on 12/8/14.
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
  getterVisibility = JsonAutoDetect.Visibility.NONE,
  setterVisibility = JsonAutoDetect.Visibility.NONE,
  isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public final class WebAutomatorPlugins {

  private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorPlugins.class);

  private final ImmutableMultimap<String, String> plugins;

  @JsonCreator
  WebAutomatorPlugins(@JsonProperty("plugins") final Multimap<String, String> pluginsMap) {
    this.plugins = ImmutableMultimap.copyOf(pluginsMap);
  }

  public static WebAutomatorPluginsBuilder builder() {
    return new WebAutomatorPluginsBuilder();
  }

  public static WebAutomatorPlugins defaultPlugins() {
    WebAutomatorPluginsBuilder builder = new WebAutomatorPluginsBuilder();
    return builder.build();
  }

  public ImmutableMultimap<String, String> getPlugins() {
    return this.plugins;
  }


  public String getPluginClassName(final String pluginName) {
    Preconditions.checkNotNull(pluginName);
    ImmutableCollection<String> classNames = this.plugins.get(pluginName);

    if (classNames.isEmpty()) {
      throw new PluginNotFoundException("Plugin for '" + pluginName + "'is not found!");
    }
    if (classNames.size() > 1) {
      LOG.warn("It seems that more than one plugin is attached for '" + pluginName + "'.");
    }
    return classNames.asList().get(0);
  }

  public ImmutableList<String> getPluginClassNameList(final String pluginName) {
    Preconditions.checkNotNull(pluginName);
    ImmutableCollection<String> classNames = this.plugins.get(pluginName);

    if (classNames.isEmpty()) {
      throw new PluginNotFoundException("Plugin for '" + pluginName + "'is not found!");
    }
    return classNames.asList();
  }

  public <T> Class<T> getPluginClass(final String pluginName) {
    Preconditions.checkNotNull(pluginName);
    Class<T> klass = null;
    try {
      String pluginClassName = this.getPluginClassName(pluginName);
      LOG.info("Get plugin: {} (from {}).", pluginName, pluginClassName);
      klass = (Class<T>) Class.forName(pluginClassName);
    } catch (ClassNotFoundException e) {
      LOG.error("Plugin {} cannot be loaded.", pluginName);
    }
    return klass;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.plugins);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof WebAutomatorPlugins) {
      WebAutomatorPlugins that = (WebAutomatorPlugins) obj;
      return Objects.equal(this.plugins, that.plugins);
    }
    return false;
  }

  public static class WebAutomatorPluginsBuilder {

    private Multimap<String, String> pluginNameMap = LinkedListMultimap.create();

    public WebAutomatorPluginsBuilder() {
      this.setDefaultPlugins();
    }


    public final WebAutomatorPluginsBuilder setPlugin(final String pluginName, final String pluginClassName) {
      Preconditions.checkNotNull(pluginName);
      Preconditions.checkNotNull(pluginClassName);

      this.pluginNameMap.removeAll(pluginName);
      this.pluginNameMap.put(pluginName, pluginClassName);
      return this;
    }

    public final WebAutomatorPluginsBuilder appendPlugin(final String pluginName, final String pluginClassName) {
      Preconditions.checkNotNull(pluginName);
      Preconditions.checkNotNull(pluginClassName);

      this.pluginNameMap.put(pluginName, pluginClassName);
      return this;
    }


    private void setDefaultPlugins() {
      this.setPlugin("core.converter.testcase", "com.gigony.qte.core.converter.WebTestCaseConverter");
      this.setPlugin("core.extractor.state", "com.gigony.qte.core.extractor.WebStateExtractor");
      this.setPlugin("core.extractor.event", "com.gigony.qte.core.extractor.WebEventExtractor");
      this.setPlugin("core.provider.eventinput", "com.gigony.qte.core.provider.WebEventInputProvider");
      this.setPlugin("core.executor.event", "com.gigony.qte.core.executor.WebEventExecutor");
      this.setPlugin("core.executor.testcase", "com.gigony.qte.core.executor.WebTestCaseExecutor");
    }

    public final WebAutomatorPlugins build() {
      return new WebAutomatorPlugins(this.pluginNameMap);
    }
  }
}
