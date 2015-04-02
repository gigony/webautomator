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

package edu.unl.qte.core.util;

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
