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

package edu.unl.webautomator.core.util;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriver;

/**
 * Created by gigony on 1/8/15.
 */
public class MyWebDriverBackedSelenium extends WebDriverBackedSelenium {

  public MyWebDriverBackedSelenium(final WebDriver baseDriver, final String baseUrl) {
    super(baseDriver, baseUrl);
  }


  public final void doCommand(final String command, final String locator) {
    commandProcessor.doCommand(command, new String[]{locator, });
  }

  public final void doCommand(final String command, final String locator, final String input) {
    if (input == null) {
      this.doCommand(command, locator);
    } else {
      commandProcessor.doCommand(command, new String[]{locator, input});
    }
  }
}
