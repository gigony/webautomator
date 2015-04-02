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
import com.gigony.qte.core.platform.WebBrowserType;
import com.gigony.qte.core.util.JacksonHelper;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Set;

/**
 * Created by gigony on 12/6/14.
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
  getterVisibility = JsonAutoDetect.Visibility.NONE,
  setterVisibility = JsonAutoDetect.Visibility.NONE,
  isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class WebAutomatorConfiguration {

  private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorConfiguration.class);
  private WebAutomatorPlugins pluginConfiguration;
  private WebBrowserConfiguration browserConfiguration;
  private WebEventTypes eventTypes;
  private ImmutableSet<String> ignoringFrameIds;
  private long pageLoadTimeOut = 30000;


  WebAutomatorConfiguration() {
  }

  @JsonCreator
  WebAutomatorConfiguration(@JsonProperty("pluginConfiguration") final WebAutomatorPlugins pluginConfig,
                            @JsonProperty("browserConfiguration") final WebBrowserConfiguration browserConfig,
                            @JsonProperty("eventTypes") final WebEventTypes eTypes,
                            @JsonProperty("ignoringFrameIds") final ImmutableSet<String> ignoringFrameIds,
                            @JsonProperty("pageLoadTimeOut") final int pageLoadTimeOut) {
    this.pluginConfiguration = pluginConfig;
    this.browserConfiguration = browserConfig;
    this.eventTypes = eTypes;
    this.ignoringFrameIds = ignoringFrameIds;
    this.pageLoadTimeOut = pageLoadTimeOut;
  }

  public final WebAutomatorPlugins getPluginConfiguration() {
    return this.pluginConfiguration;
  }

  public final WebBrowserConfiguration getBrowserConfiguration() {
    return this.browserConfiguration;
  }


  public final WebEventTypes getEventTypes() {
    return this.eventTypes;
  }

//  /**
//   * Setting different event types are allowed.
//   * @param eTypes
//   */
//    public final void setEventTypes(final WebEventTypes eTypes) {
//      this.eventTypes = eTypes;
//    }

  public final <T> Class<T> getPluginClass(final String pluginName) {
    return this.pluginConfiguration.getPluginClass(pluginName);
  }

  public static WebAutomatorConfiguration create(final WebBrowserType browserType) {
    return builder().setWebBrowserConfiguration(
      WebBrowserConfiguration.builder(browserType)
        .build()
    ).build();
  }

  public static WebAutomatorConfiguration create(final WebBrowserType browserType, final String webDriverBinPath) {
    return builder().setWebBrowserConfiguration(
      WebBrowserConfiguration.builder(browserType)
        .setWebDriverBinaryPath(webDriverBinPath)
        .build()
    ).build();
  }

  public static WebAutomatorConfigurationBuilder builder() {
    return new WebAutomatorConfigurationBuilder();
  }

  public static WebAutomatorConfiguration defaultConfiguration() {
    return new WebAutomatorConfigurationBuilder().build();
  }

  public static WebAutomatorConfiguration importFromJson(final File jsonFile) {
    return JacksonHelper.loadObjectFromJsonFile(jsonFile, WebAutomatorConfiguration.class);
  }

  public final void exportToJson(final File jsonFile) {
    JacksonHelper.saveObjectToJsonFile(jsonFile, this);
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.pluginConfiguration, this.browserConfiguration, this.eventTypes);
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj instanceof WebAutomatorConfiguration) {
      WebAutomatorConfiguration that = (WebAutomatorConfiguration) obj;
      return Objects.equal(this.pluginConfiguration, that.pluginConfiguration)
        && Objects.equal(this.browserConfiguration, that.browserConfiguration)
        && Objects.equal(this.eventTypes, that.eventTypes);

    }
    return false;
  }

  public final long getPageLoadTimeOut() {
    return this.pageLoadTimeOut;
  }

  public final boolean isIgnoringFrame(final String frameId) {
    return this.ignoringFrameIds.contains(frameId);
  }


  public static class WebAutomatorConfigurationBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorConfigurationBuilder.class);

    private WebAutomatorConfiguration config;

    public WebAutomatorConfigurationBuilder() {
      this.config = new WebAutomatorConfiguration();

      this.config.pluginConfiguration = WebAutomatorPlugins.defaultPlugins();
      this.config.browserConfiguration = WebBrowserConfiguration.defaultBrowser();
      this.config.ignoringFrameIds = ImmutableSet.of();
      this.config.eventTypes = WebEventTypes.defaultEventTypes();
    }


    public final WebAutomatorConfigurationBuilder setWebAutomatorPlugins(final WebAutomatorPlugins plugins) {
      Preconditions.checkNotNull(plugins);
      this.config.pluginConfiguration = plugins;

      return this;
    }


    public final WebAutomatorConfigurationBuilder setWebBrowserConfiguration(final WebBrowserConfiguration browserConfiguration) {
      Preconditions.checkNotNull(browserConfiguration);
      this.config.browserConfiguration = browserConfiguration;

      return this;
    }


    public final WebAutomatorConfigurationBuilder setWebEventTypes(final WebEventTypes eventTypes) {
      Preconditions.checkNotNull(eventTypes);
      this.config.eventTypes = eventTypes;

      return this;
    }

    public final WebAutomatorConfigurationBuilder setIgnoringFrameIds(final String... frameIds) {
      Set<String> frameIdSet = Sets.newHashSet();
      for (String frameId : frameIds) {
        frameIdSet.add(frameId);
      }
      this.config.ignoringFrameIds = ImmutableSet.copyOf(frameIdSet);
      return this;
    }

    public final WebAutomatorConfigurationBuilder setPageLoadTimeOut(final long timeOut) {
      this.config.pageLoadTimeOut = timeOut;

      return this;
    }

    public final WebAutomatorConfiguration build() {
      return this.config;
    }
  }
}
