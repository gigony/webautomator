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

package com.gigony.qte.core.util;

import org.openqa.selenium.By;

/**
 * Created by gigony on 12/9/14.
 */
public final class SeleniumHelper {
  private SeleniumHelper() {
  }


  public static By convertStringLocatorToBy(final String locator) {
    if (isCssLocator(locator)) {
      return By.cssSelector(locator.substring("css=".length()));
    } else if (locator.startsWith("xpath=")) {
      return By.xpath(locator.substring("xpath=".length()));
    } else if (locator.startsWith("//")) {
      return By.xpath(locator);
    } else if (locator.startsWith("id=")) {
      return By.id(locator.substring("id=".length()));
    } else if (locator.startsWith("name=")) {
      return By.name(locator.substring("name=".length()));
    } else if (locator.startsWith("link=")) {
      return By.linkText(locator.substring("link=".length()));
    } else {
      return By.id(locator);
    }
    // @TODO - considering 'class', 'name' or 'tag' is missing here.
  }

  public static boolean isCssLocator(final String locator) {
    return locator.startsWith("css=");
  }

  public static String extractCssLocator(final String locator) {
    if (isCssLocator(locator)) {
      return locator.substring("css=".length());
    }
    throw new RuntimeException(String.format("'%s' is not CSS locator.", locator));
  }

  public static boolean isXPathLocator(final String locator) {
    return locator.startsWith("xpath=") || locator.startsWith("//");
  }

  public static String extractXPathLocator(final String locator) {
    if (locator.startsWith("xpath=")) {
      return locator.substring("xpath=".length());
    } else if (locator.startsWith("//")) {
      return locator;
    }
    throw new RuntimeException(String.format("'%s' is not XPATH locator.", locator));
  }

}
