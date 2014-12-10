package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.collect.*;
import edu.unl.webautomator.core.platform.WebBrowserType;

import java.util.Map;


/**
 * Created by gigony on 12/6/14.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class WebBrowserConfiguration {
    private WebBrowserType browserType;
    private String remoteHubUrl;
    private String language;
    private WebProxyConfiguration proxyConfiguration;
    private ImmutableMap<WebBrowserType, String> webDriverBinaryPaths;


    WebBrowserConfiguration() {
    }

    @JsonCreator
    WebBrowserConfiguration(@JsonProperty("browserType") final WebBrowserType webBrowserType,
                            @JsonProperty("remoteHubUrl") final String browserRemoteHubUrl,
                            @JsonProperty("language") final String lang,
                            @JsonProperty("proxyConfiguration") final WebProxyConfiguration webProxyConfiguration,
                            @JsonProperty("webDriverBinaryPaths") final ImmutableMap<WebBrowserType, String> binaryPaths) {
        this.browserType = webBrowserType;
        this.remoteHubUrl = browserRemoteHubUrl;
        this.language = lang;
        this.proxyConfiguration = webProxyConfiguration;
        this.webDriverBinaryPaths = binaryPaths;
    }

    public static WebBrowserConfigurationBuilder builder() {
        return new WebBrowserConfigurationBuilder();
    }

    public static WebBrowserConfigurationBuilder builder(final WebBrowserType browserType) {
        return new WebBrowserConfigurationBuilder(browserType);
    }

    public static WebBrowserConfiguration defaultBrowser() {
        return new WebBrowserConfigurationBuilder().build();
    }

    public final WebBrowserType getBrowserType() {
        return this.browserType;
    }

    public final String getRemoteHubUrl() {
        return this.remoteHubUrl;
    }

    public final String getLanguage() {
        return this.language;
    }

    public final WebProxyConfiguration getProxyConfiguration() {
        return this.proxyConfiguration;
    }

    public final ImmutableMap<WebBrowserType, String> getWebDriverBinaryPaths() {
        return this.webDriverBinaryPaths;
    }
    public final String getWebDriverBinaryPath() {
        return this.webDriverBinaryPaths.get(this.browserType);
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.browserType, this.remoteHubUrl, this.language, this.proxyConfiguration);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof WebBrowserConfiguration) {
            WebBrowserConfiguration that = (WebBrowserConfiguration) obj;
            return Objects.equal(this.browserType, that.browserType)
                    && Objects.equal(this.remoteHubUrl, that.remoteHubUrl)
                    && Objects.equal(this.language, that.language)
                    && Objects.equal(this.proxyConfiguration, that.proxyConfiguration);
        }
        return false;
    }

    public static class WebBrowserConfigurationBuilder {

        private WebBrowserConfiguration browserConfiguration;
        private Map<WebBrowserType, String> webDriverBinaryPaths;

        public WebBrowserConfigurationBuilder() {
            this.browserConfiguration = new WebBrowserConfiguration();
            this.browserConfiguration.browserType = WebBrowserType.CHROME;
            this.browserConfiguration.proxyConfiguration = WebProxyConfiguration.noProxy();
            this.webDriverBinaryPaths = Maps.newLinkedHashMap();
        }

        public WebBrowserConfigurationBuilder(final WebBrowserType browserType) {
            this.browserConfiguration = new WebBrowserConfiguration();
            this.browserConfiguration.browserType = browserType;
            this.browserConfiguration.proxyConfiguration = WebProxyConfiguration.noProxy();
            this.webDriverBinaryPaths = Maps.newLinkedHashMap();
        }

        public final WebBrowserConfigurationBuilder setBrowserType(final WebBrowserType browserType) {
            this.browserConfiguration.browserType = browserType;
            return this;
        }

        public final WebBrowserConfigurationBuilder setRemoteHubUrl(final String remoteHubUrl) {
            this.browserConfiguration.remoteHubUrl = remoteHubUrl;
            return this;
        }

        public final WebBrowserConfigurationBuilder setLanguage(final String language) {
            this.browserConfiguration.language = language;
            return this;
        }

        public final WebBrowserConfigurationBuilder setProxyConfiguration(final WebProxyConfiguration proxyConfiguration) {
            this.browserConfiguration.proxyConfiguration = proxyConfiguration;
            return this;
        }

        public final WebBrowserConfigurationBuilder setWebDriverBinaryPath(final String webDriverBinPath) {
            WebBrowserType browserType = this.browserConfiguration.browserType;
            this.webDriverBinaryPaths.put(browserType, webDriverBinPath);
            return this;
        }

        public final WebBrowserConfiguration build() {
            this.browserConfiguration.webDriverBinaryPaths = ImmutableMap.copyOf(this.webDriverBinaryPaths);
            return this.browserConfiguration;
        }
    }


}
