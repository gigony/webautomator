package edu.unl.webautomator.core;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.platform.WebBrowserType;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class WebAutomatorTest {

  @Test
  public final void openChromeBrowserTest() {

    StaticWebServer.start("/fixture/frames/");

    WebAutomator automator = QTE.webAutomator(WebBrowserType.CHROME,
      "/Users/gigony/Development/Repository/github/webautomator/webautomator-core/build/webdrivers/chromedriver");

    WebBrowser browser = automator.getWebBrowser();
    WebDriver driver = automator.getWebDriver();
    WebDriverBackedSelenium selenium = automator.createSelenium("http://www.nate.com");

    try {


      selenium.open("http://localhost:8080/frameset.html");
      selenium.waitForPageToLoad("30000");

      String html = driver.getPageSource();

      Document a = browser.getPageDomWithFrameContent();

      Element el = a.getElementById("a.aaa.d");

      System.out.println(el);

      System.out.println(browser.getFrameContent("a.aaa.d"));

    } catch (Throwable e) {
      e.printStackTrace();
    } finally {
      automator.quit();
    }


//        ImmutableList<EventType> eventTypeList = automator.getConfiguration().getEventTypes().getIncludingEventTypeMap().get("click").asList();
//        for (EventType t:eventTypeList) {
//            List<WebElement> elements = driver.findElements(SeleniumHelper.convertStringLocatorToBy(t.getEventLocator()));
//            for (WebElement elem:elements) {
//                System.out.println("====" + elem.getText() + "===");
//                System.out.println(elem.getAttribute("innerHTML"));
//
////ElementFinder
////                WebElement element = driver.findElement(By.id("foo"));
////                String contents = (String)((JavascriptExecutor)driver).executeScript("return      arguments[0].innerHTML;", element);
//            }
//        }
  }

  @Test
  public final void openPhantomBrowserTest() {

    StaticWebServer.start("/fixture/frames/");

    WebAutomator automator = QTE.webAutomator(WebBrowserType.PHANTOMJS);
    WebBrowser browser = automator.getWebBrowser();
    WebDriver driver = automator.getWebDriver();
    WebDriverBackedSelenium selenium = automator.createSelenium("http://localhost:8080");

    selenium.open("frameset.html");

    String html = browser.getPageSourceWithFrameContent();

    System.out.println(html);
    automator.quit();

  }

  @Test
  public final void openFireFoxBrowserTest() {

    WebAutomator automator = QTE.webAutomator(WebBrowserType.FIREFOX);
    WebBrowser browser = automator.getWebBrowser();
    WebDriver driver = automator.getWebDriver();
    WebDriverBackedSelenium selenium = automator.createSelenium("http://www.nate.com");

    selenium.open("http://www.nate.com");

    String html = browser.getPageSourceWithFrameContent();

    System.out.println(html);
    automator.quit();

  }

  @Test
  public final void frameContentTest() {

    StaticWebServer.start("/fixture/frames/");

    WebAutomator automator = QTE.webAutomator(WebBrowserType.PHANTOMJS);
    WebBrowser browser = automator.getWebBrowser();
    WebDriver driver = automator.getWebDriver();
    WebDriverBackedSelenium selenium = automator.createSelenium("http://localhost:8080");


    selenium.open("frameset.html");

    String html = driver.getPageSource();

    Document a = browser.getPageDomWithFrameContent();


    Element el = a.getElementById("a.aaa.d");

    System.out.println(el);

    System.out.println(browser.getFrameContent("a.aaa.d"));
  }

  @Test
  public final void configureEventTypesTest() {
    StaticWebServer.start("/fixture/frames/");

    WebAutomator automator = QTE.webAutomator(WebBrowserType.PHANTOMJS);
    WebBrowser browser = automator.getWebBrowser();
    WebDriver driver = automator.getWebDriver();
    WebDriverBackedSelenium selenium = automator.createSelenium("http://localhost:8080");

    selenium.open("frameset.html");
    selenium.waitForPageToLoad("30000");

    String html = browser.getPageSourceWithFrameContent();


    System.out.println(html);
    automator.quit();

  }

}
