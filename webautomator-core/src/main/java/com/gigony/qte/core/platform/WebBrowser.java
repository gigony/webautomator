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

package com.gigony.qte.core.platform;

import com.gigony.qte.core.util.MyWebDriverBackedSelenium;
import com.gigony.qte.core.model.WebDocument;
import org.openqa.selenium.WebDriver;

/**
 * Created by gigony on 12/9/14.
 */
public interface WebBrowser {

  WebDriver getWebDriver();

  MyWebDriverBackedSelenium getSelenium();

  MyWebDriverBackedSelenium setBaseUrlWithSelenium(String baseUrl);


  // Functions from WebDriver

  String getPageSource();

  String getCurrentUrl();

  void open(String uri, long timeout);


  // ####################################
  // Improved functions with WebDriver
  // ####################################


  String getFrameId();

  void changeFrameStack(String frameSelector);

  void moveToDefaultFrame();

  void moveToRelativeFrame(String frameId);

  void moveToAbsoluteFrame(String frameId);

  void moveToParentFrame();


  String getJsonPageSourceWithFrameContent();

  WebDocument getPageDomWithFrameContent();

  String getFrameContent(String frameID);


  /**
   * Return normalized ('script' tag is removed) page source.
   *
   * @return HTML code
   */
  String getNormalizedPageSource();


}
