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

package edu.unl.webautomator.core.platform.browser;

import com.google.common.base.Preconditions;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.model.WebDocument;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.util.JSoupHelper;
import edu.unl.webautomator.core.util.MyWebDriverBackedSelenium;
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
  private WebAutomatorConfiguration configuration;

  private WebDriver webDriver;
  private MyWebDriverBackedSelenium selenium;

  private LinkedList<String> frameStack = new LinkedList<String>();


  public BasicWebBrowser(final WebAutomatorConfiguration configuration) {
    WebBrowserConfiguration browserConfiguration = configuration.getBrowserConfiguration();
    this.configureWebBrowser(browserConfiguration);
    this.configuration = configuration;
    Preconditions.checkNotNull(this.webDriver);
  }

  protected abstract void configureWebBrowser(WebBrowserConfiguration browserConfiguration);

  protected final void setWebDriver(final WebDriver driver) {
    this.webDriver = driver;
    this.selenium = new MyWebDriverBackedSelenium(driver, "http://");
  }

  public final WebDriver getWebDriver() {
    return this.webDriver;
  }

  public final MyWebDriverBackedSelenium getSelenium() {
    return this.selenium;
  }

  protected final Deque<String> getFrameStack() {
    return this.frameStack;
  }


  @Override
  public final String getPageSource() {
    return this.webDriver.getPageSource();
  }

  @Override
  public final String getCurrentUrl() {
    return this.webDriver.getCurrentUrl();
  }

  @Override
  public final void open(final String uri, final long timeout) {
    this.webDriver.get(uri);
    this.selenium.waitForPageToLoad(String.valueOf(timeout));
    this.frameStack.clear();
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
      buf.append(WebDocument.FRAME_SPLITTER);
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

    String[] names = frameId.split(WebDocument.FRAME_SPLITTER_MATCHER);
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
  public final String getJsonPageSourceWithFrameContent() {
    return this.getPageDomWithFrameContent().toJson();
  }

  /**
   * @return
   */
  @Override
  public final WebDocument getPageDomWithFrameContent() {
    final String content = this.getPageSource();

    Document dom = JSoupHelper.parse(content);
    dom.outputSettings().prettyPrint(false);
    String uri = this.getCurrentUrl();
    WebDocument webDoc = new WebDocument(uri, dom);

    Elements frameElements = dom.getElementsByTag("frame");

    // append iframe elements
    frameElements.addAll(dom.getElementsByTag("iframe"));

    for (Element elem : frameElements) {
      String id = JSoupHelper.getIdOrName(elem);

      // process only frames having id or name
      if (!"".equals(id)) {
        this.moveToRelativeFrame(id);

        // ignore if current frame's id should be ignored.
        String currentFrameId = this.getFrameId();
        if (!this.configuration.isIgnoringFrame(currentFrameId)) {
          WebDocument frameDoc = this.getPageDomWithFrameContent();
          webDoc.appendFrame(id, frameDoc);
        }

        this.moveToParentFrame();
      }
    }
    return webDoc;
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

