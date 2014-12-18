package edu.unl.webautomator.core.platform.browser;

import com.google.common.base.Preconditions;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.model.State;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.util.JSoupHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by gigony on 12/9/14.
 */
public abstract class BasicWebBrowser implements WebBrowser {

  private WebDriver webDriver;

  private LinkedList<String> frameStack = new LinkedList<String>();


  public BasicWebBrowser(final WebBrowserConfiguration browserConfiguration) {
    this.configureWebBrowser(browserConfiguration);
    Preconditions.checkNotNull(this.webDriver);
  }

  protected abstract void configureWebBrowser(WebBrowserConfiguration browserConfiguration);

  protected final void setWebDriver(final WebDriver driver) {
    this.webDriver = driver;
  }

  public final WebDriver getWebDriver() {
    return this.webDriver;
  }

  protected final Deque<String> getFrameStack() {
    return this.frameStack;
  }


  @Override
  public final String getPageSource() {
    return this.webDriver.getPageSource();
  }

  @Override
  public final String getFrameId() {
    Iterator<String> iterator = this.frameStack.descendingIterator();
    StringBuffer buf = new StringBuffer();
    String frameName;

    if (iterator.hasNext()) {
      frameName = iterator.next();
      buf.append(frameName);
    }

    while (iterator.hasNext()) {
      frameName = iterator.next();
      buf.append('.');
      buf.append(frameName);
    }

    return buf.toString();
  }

  @Override
  public final void moveToDefaultFrame() {
    this.webDriver.switchTo().defaultContent();
    this.frameStack.clear();
  }

  @Override
  public final void moveToRelativeFrame(final String frameId) {
    this.webDriver.switchTo().frame(frameId);
    this.frameStack.push(frameId);
  }

  @Override
  public final void moveToAbsoluteFrame(final String frameId) {
    Preconditions.checkNotNull(frameId);

    this.moveToDefaultFrame();

    if ("".equals(frameId)) {
      return;
    }

    String[] names = frameId.split("\\.+");
    for (String name : names) {
      this.moveToRelativeFrame(name);
    }
  }

  @Override
  public final void moveToParentFrame() {
    this.moveToParentFrameImpl();


  }

  protected abstract void moveToParentFrameImpl();


  @Override
  public final String getNormalizedPageSource() {
    final String html = this.getPageSource();

    return null;
  }

  @Override
  public final String getPageSourceWithFrameContent() {

    Document dom = this.getPageDomWithFrameContent("");
    return dom.toString();
  }

  @Override
  public final Document getPageDomWithFrameContent() {
    return this.getPageDomWithFrameContent("");
  }

  /**
   * Because new Ids were added to frames,
   * it may cause some problems when you are attempting to use frame ids inside top-level frames
   *
   * @param currentFrameLocation
   * @return
   */
  private Document getPageDomWithFrameContent(final String currentFrameLocation) {
    final String content = this.getPageSource();

    Document dom = Jsoup.parse(content);

    Elements frameElements = dom.getElementsByTag("frame");

    // append iframe elements
    frameElements.addAll(dom.getElementsByTag("iframe"));

    for (Element elem : frameElements) {
      String id = JSoupHelper.getIdOrName(elem);

      if (!"".equals(id)) {
        this.moveToRelativeFrame(id);

        final String frameLocation = "".equals(currentFrameLocation)
          ? id : currentFrameLocation + "." + id;

        Document frameDom = this.getPageDomWithFrameContent(frameLocation);

        elem.appendChild(frameDom.child(0));

        // set new id to frame
        elem.attr("id", frameLocation);

        this.moveToParentFrame();
      }
    }
    return dom;
  }

  @Override
  public final String getFrameContent(final String frameId) {
    LinkedList<String> oldStack = (LinkedList<String>) this.frameStack.clone();

    this.moveToAbsoluteFrame(frameId);
    String html = this.getPageSource();

    // return to original frame
    this.frameStack = oldStack;
    this.moveToAbsoluteFrame(this.getFrameId());
    return html;
  }

}

