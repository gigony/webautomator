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
  private final String hostAddr;
  private final int portNum;

  WebProxyConfiguration(final WebProxyType proxyType) {
    this(proxyType, "none", -1);
  }

  @JsonCreator
  WebProxyConfiguration(@JsonProperty("webProxyType") final WebProxyType proxyType,
                        @JsonProperty("host") final String host,
                        @JsonProperty("port") final int port) {
    this.webProxyType = proxyType;
    this.hostAddr = host;
    this.portNum = port;
  }

  public static WebProxyConfiguration noProxy() {
    return new WebProxyConfiguration(WebProxyType.NONE);
  }

  public static WebProxyConfiguration manualProxy(final String host, final int port) {
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

  public final WebProxyType getWebProxyType() {
    return this.webProxyType;
  }

  public final String getHostAddr() {
    return this.hostAddr;
  }

  public final int getPortNum() {
    return this.portNum;
  }

  @Override
  public final String toString() {
    switch (this.webProxyType) {
      case MANUAL:
        return "Manual proxy (" + this.hostAddr + ":" + this.portNum + ")";
      default:
        return "Proxy type: " + this.webProxyType.toString();
    }
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.webProxyType, this.portNum, this.hostAddr);
  }


  @Override
  public final boolean equals(final Object obj) {
    if (obj instanceof WebProxyConfiguration) {
      WebProxyConfiguration that = (WebProxyConfiguration) obj;
      return Objects.equal(this.webProxyType, that.webProxyType)
        && Objects.equal(this.portNum, that.portNum)
        && Objects.equal(this.hostAddr, that.hostAddr);
    }
    return false;
  }
}
