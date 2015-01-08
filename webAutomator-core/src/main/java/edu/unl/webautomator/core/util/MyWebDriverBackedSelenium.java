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
