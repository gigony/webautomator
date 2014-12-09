package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import edu.unl.webautomator.core.platform.WebBrowserType;

/**
 * Created by gigony on 12/6/14.
 */
public class WebBrowserConfiguration {
    WebBrowserType browserType;
    String remoteHubUrl;

    WebBrowserConfiguration() {
    }

    @JsonCreator
    WebBrowserConfiguration(@JsonProperty("browserType") WebBrowserType browserType,
                            @JsonProperty("remoteHubUrl") String remoteHubUrl) {
        this.browserType = browserType;
        this.remoteHubUrl = remoteHubUrl;
    }

    public static WebBrowserConfiguration defaultBrowser() {
        return new WebBrowserConfigurationBuilder().build();
    }

    public WebBrowserType getBrowserType() {
        return browserType;
    }

    public String getRemoteHubUrl() {
        return remoteHubUrl;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(browserType, remoteHubUrl);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WebBrowserConfiguration) {
            WebBrowserConfiguration that = (WebBrowserConfiguration) obj;
            return Objects.equal(browserType, that.browserType) &&
                    Objects.equal(remoteHubUrl, that.remoteHubUrl);
        }
        return false;
    }


}
