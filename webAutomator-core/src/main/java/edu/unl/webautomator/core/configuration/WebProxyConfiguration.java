package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import javax.annotation.concurrent.Immutable;


/**
 * Created by gigony on 12/6/14.
 */
@Immutable
public class WebProxyConfiguration {

    private final WebProxyType webProxyType;
    private final String host;
    private final int port;

    WebProxyConfiguration(WebProxyType webProxyType) {
        this(webProxyType, "none", -1);
    }

    @JsonCreator
    WebProxyConfiguration(@JsonProperty("webProxyType") WebProxyType webProxyType,
                          @JsonProperty("host") String host,
                          @JsonProperty("port") int port) {
        this.webProxyType = webProxyType;
        this.host = host;
        this.port = port;
    }

    public static WebProxyConfiguration noProxy() {
        return new WebProxyConfiguration(WebProxyType.NONE);
    }

    public static WebProxyConfiguration manualProxy(String host, int port) {
        Preconditions.checkNotNull(host);
        Preconditions.checkArgument(port >= 0 && port <= 65535,
                "Port number should be ranging from 0 to 65535 (" + port + " was specified).");
        return new WebProxyConfiguration(WebProxyType.MANUAL, host, port);
    }

    public static WebProxyConfiguration autoProxy() {
        return new WebProxyConfiguration(WebProxyType.AUTO);
    }

    public static WebProxyConfiguration browserProxy() {
        return new WebProxyConfiguration(WebProxyType.BROWSER);
    }

    public static WebProxyConfiguration systemProxy() {
        return new WebProxyConfiguration(WebProxyType.SYSTEM);
    }

    public WebProxyType getWebProxyType() {
        return webProxyType;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        switch (webProxyType) {
            case MANUAL:
                return "Manual proxy (" + host + ":" + port + ")";
            default:
                return "Proxy type: " + webProxyType.toString();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(webProxyType, port, host);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WebProxyConfiguration) {
            WebProxyConfiguration that = (WebProxyConfiguration) obj;
            return Objects.equal(this.webProxyType, that.webProxyType)
                    && Objects.equal(this.port, that.port)
                    && Objects.equal(this.host, that.host);
        }
        return false;
    }
}
