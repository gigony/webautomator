package com.gigony.qte.core;

import com.gigony.qte.core.model.WebDocument;
import com.gigony.qte.core.platform.WebBrowser;
import com.gigony.qte.core.platform.WebBrowserType;
import com.gigony.qte.core.util.JSoupHelper;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class WebAutomatorTest {


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

      WebDocument a = browser.getPageDomWithFrameContent();

      Element el = a.getFrame("a>aaa>d").getDocument();

      System.out.println(el);

      System.out.println(browser.getFrameContent("a>aaa>d"));

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

    String html = browser.getJsonPageSourceWithFrameContent();

    System.out.println(html);
    automator.quit();

  }


  public final void openFireFoxBrowserTest() {

    WebAutomator automator = QTE.webAutomator(WebBrowserType.FIREFOX);
    WebBrowser browser = automator.getWebBrowser();
    WebDriver driver = automator.getWebDriver();
    WebDriverBackedSelenium selenium = automator.createSelenium("http://www.nate.com");

    selenium.open("http://www.nate.com");

    WebDocument dom = browser.getPageDomWithFrameContent();
    String json = dom.toJson();
    System.out.println(json);

    WebDocument dom2 = WebDocument.getDocumentFromJson(json);
    String json2 = dom2.toJson();
//    json2 = json2.replaceAll("[\\s\\n]+", " ");
    System.out.println(json2);
    System.out.println(json.equals(json2));

    WebDocument dom3 = WebDocument.getDocumentFromJson(json2);
    String json3 = dom3.toJson();
//    json3 = json3.replaceAll("( )\\\\[[\\s\\\\n]+", " ");
    System.out.println(json3);


    char[] htmlArr = json2.toCharArray();
    char[] targetArr = json3.toCharArray();
    int len = Math.min(htmlArr.length, targetArr.length);
    for (int i = 0; i < len; i++) {
      if (htmlArr[i] != targetArr[i]) {
        System.out.println(String.format("%d: %c - %c", i, htmlArr[i], targetArr[i]));
      }
    }
    System.out.println(json2.equals(json3));

    System.out.println(JSoupHelper.areElementSame(dom2.getDocument(), dom3.getDocument()));
//    System.out.println(dom.getDocument().outputSettings().syntax().name());
//    System.out.println(dom2.getDocument().outputSettings().syntax().name());
//    System.out.println(dom3.getDocument().outputSettings().syntax().name());


//    Document dom = JSoupHelper.getDomFromFrameContentString(html);
//
//    System.out.println(html);
//    System.out.println("#########################");
//    String target = dom.toString();
//    System.out.println(target);
//    System.out.println(html.equals(target));
//    System.out.println(html.length() + "," + target.length());

//    char[] htmlArr = html.toCharArray();
//    char[] targetArr = target.toCharArray();
//    int len = Math.min(htmlArr.length, targetArr.length);
//    for (int i = 0; i < len; i++) {
//      if (htmlArr[i] != targetArr[i]) {
//        System.out.println(String.format("%d: %c - %c", i, htmlArr[i], targetArr[i]));
//      }
//    }
//    Document dom2 = JSoupHelper.getDomFromFrameContentString(target);
//    Document dom2b = JSoupHelper.getDomFromFrameContentString(target);
//    String target2 = dom2.toString();
//    System.out.println(target.equals(target2));
//    Document dom3 = JSoupHelper.getDomFromFrameContentString(target2);
//    String target3 = dom3.toString();
//    System.out.println(target3.equals(target2));
//
//    System.out.println(JSoupHelper.areElementSame(dom2, dom2b));
//    System.out.println(JSoupHelper.areElementSame(dom, dom2));
//    System.out.println(JSoupHelper.areElementSame(dom2, dom3));

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

    WebDocument a = browser.getPageDomWithFrameContent();

    Element el = a.getFrame("a>aaa>d").getDocument();

    System.out.println(el);

    System.out.println(browser.getFrameContent("a>aaa>d"));
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

    String html = browser.getJsonPageSourceWithFrameContent();


    System.out.println(html);
    automator.quit();

  }

}
