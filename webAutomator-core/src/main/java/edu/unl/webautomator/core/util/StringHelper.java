package edu.unl.webautomator.core.util;

/**
 * Created by gigony on 12/18/14.
 */
public final class StringHelper {
  private StringHelper() {
  }

  public static int getPortNumFromUrl(final String url) {
    int portStart = url.lastIndexOf(":");
    int portEnd = -1;
    if (portStart > 0) {
      portEnd = url.indexOf("/", portStart);
    }
    if (portEnd == -1) {
      portEnd = url.length();
    }
    int port = 80;

    if (portStart > 0) {
      try {
        port = Integer.parseInt(url.substring(portStart + 1, portEnd));
      } catch (NumberFormatException e) {
        port = 80;
      }
    }
    return port;
  }
}
