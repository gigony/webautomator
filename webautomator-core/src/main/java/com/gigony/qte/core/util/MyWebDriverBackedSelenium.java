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

import com.fasterxml.jackson.core.type.TypeReference;
import com.gigony.qte.core.exception.VerifyException;
import com.gigony.qte.core.model.WebEventElement;
import com.google.common.collect.Sets;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gigony on 1/8/15.
 */
public class MyWebDriverBackedSelenium extends WebDriverBackedSelenium {
  private static final Logger LOG = LoggerFactory.getLogger(MyWebDriverBackedSelenium.class);
  private static Map<String, String[]> apis;
  private static Map<String, String> getterApis;
  //  private static Map<String, Integer> argMap;
  private static Set<String> locationBasedInputActionSet;
  private static Set<String> locationBasedActionSet;

  static {
    String apiDoc = IOHelper.getResourceAsString("seleniumAPI.json");
    apis = JacksonHelper.loadObjectFromJsonString(apiDoc, new TypeReference<Map<String, String[]>>() {
    });

    String getterDoc = IOHelper.getResourceAsString("getterAPI.json");
    getterApis = JacksonHelper.loadObjectFromJsonString(getterDoc, new TypeReference<Map<String, String>>() {
    });


    locationBasedInputActionSet = Sets.newHashSet("clickAt", "doubleClickAt", "contextMenuAt", "fireEvent", "keyPress", "keyDown",
      "keyUp", "mouseDownAt", "mouseDownRightAt", "mouseUpAt", "mouseUpRightAt", "mouseMoveAt", "type", "typeKeys",
      "select", "addSelection", "removeSelection", "dragdrop", "dragAndDrop", "setCursorPosition", "assignId");
    locationBasedActionSet = Sets.newHashSet("click", "doubleClick", "contextMenu", "clickAt", "doubleClickAt",
      "contextMenuAt", "fireEvent", "focus", "keyPress", "keyDown", "keyUp", "mouseOver", "mouseOut", "mouseDown",
      "mouseDownRight", "mouseDownAt", "mouseDownRightAt", "mouseUp", "mouseUpRight", "mouseUpAt", "mouseUpRightAt",
      "mouseMove", "mouseMoveAt", "type", "typeKeys", "check", "uncheck", "select", "addSelection", "removeSelection",
      "removeAllSelections", "submit", "selectFrame", "dragdrop", "dragAndDrop", "setCursorPosition", "assignId");

   /* argMap = new HashMap<String, Integer>();
    argMap.put("shiftKeyDown", 0);
    argMap.put("shiftKeyUp", 0);
    argMap.put("metaKeyDown", 0);
    argMap.put("metaKeyUp", 0);
    argMap.put("altKeyDown", 0);
    argMap.put("altKeyUp", 0);
    argMap.put("controlKeyDown", 0);
    argMap.put("controlKeyUp", 0);
    argMap.put("getSpeed", 0);
    argMap.put("getLog", 0);
    argMap.put("deselectPopUp", 0);
    argMap.put("chooseCancelOnNextConfirmation", 0);
    argMap.put("chooseOkOnNextConfirmation", 0);
    argMap.put("goBack", 0);
    argMap.put("refresh", 0);
    argMap.put("close", 0);
    argMap.put("isAlertPresent", 0);
    argMap.put("isPromptPresent", 0);
    argMap.put("isConfirmationPresent", 0);
    argMap.put("getAlert", 0);
    argMap.put("getConfirmation", 0);
    argMap.put("getPrompt", 0);
    argMap.put("getLocation", 0);
    argMap.put("getTitle", 0);
    argMap.put("getBodyText", 0);
    argMap.put("getAllButtons", 0);
    argMap.put("getAllLinks", 0);
    argMap.put("getAllFields", 0);
    argMap.put("getMouseSpeed", 0);
    argMap.put("windowFocus", 0);
    argMap.put("windowMaximize", 0);
    argMap.put("getAllWindowIds", 0);
    argMap.put("getAllWindowNames", 0);
    argMap.put("getAllWindowTitles", 0);
    argMap.put("getHtmlSource", 0);
    argMap.put("getCookie", 0);
    argMap.put("deleteAllVisibleCookies", 0);
    argMap.put("captureScreenshotToString", 0);
    argMap.put("shutDownSeleniumServer", 0);
    argMap.put("retrieveLastRemoteControlLogs", 0);

    argMap.put("click", 1);
    argMap.put("doubleClick", 1);
    argMap.put("contextMenu", 1);
    argMap.put("focus", 1);
    argMap.put("mouseOver", 1);
    argMap.put("mouseOut", 1);
    argMap.put("mouseDown", 1);
    argMap.put("mouseDownRight", 1);
    argMap.put("mouseUp", 1);
    argMap.put("mouseUpRight", 1);
    argMap.put("mouseMove", 1);
    argMap.put("setSpeed", 1);
    argMap.put("check", 1);
    argMap.put("uncheck", 1);
    argMap.put("removeAllSelections", 1);
    argMap.put("submit", 1);
    argMap.put("open", 1);
    argMap.put("selectWindow", 1);
    argMap.put("selectPopUp", 1);
    argMap.put("selectFrame", 1);
    argMap.put("answerOnNextPrompt", 1);
    argMap.put("getValue", 1);
    argMap.put("getText", 1);
    argMap.put("highlight", 1);
    argMap.put("getEval", 1);
    argMap.put("isChecked", 1);
    argMap.put("getTable", 1);
    argMap.put("getSelectedLabels", 1);
    argMap.put("getSelectedLabel", 1);
    argMap.put("getSelectedValues", 1);
    argMap.put("getSelectedValue", 1);
    argMap.put("getSelectedIndexes", 1);
    argMap.put("getSelectedIndex", 1);
    argMap.put("getSelectedIds", 1);
    argMap.put("getSelectedId", 1);
    argMap.put("isSomethingSelected", 1);
    argMap.put("getSelectOptions", 1);
    argMap.put("getAttribute", 1);
    argMap.put("isTextPresent", 1);
    argMap.put("isElementPresent", 1);
    argMap.put("isVisible", 1);
    argMap.put("isEditable", 1);
    argMap.put("getAttributeFromAllWindows", 1);
    argMap.put("setMouseSpeed", 1);
    argMap.put("dragAndDropToObject", 1);
    argMap.put("getElementIndex", 1);
    argMap.put("getElementPositionLeft", 1);
    argMap.put("getElementPositionTop", 1);
    argMap.put("getElementWidth", 1);
    argMap.put("getElementHeight", 1);
    argMap.put("getCursorPosition", 1);
    argMap.put("getExpression", 1);
    argMap.put("getXpathCount", 1);
    argMap.put("getCssCount", 1);
    argMap.put("allowNativeXpath", 1);
    argMap.put("ignoreAttributesWithoutValue", 1);
    argMap.put("setTimeout", 1);
    argMap.put("waitForPageToLoad", 1);
    argMap.put("getCookieByName", 1);
    argMap.put("isCookiePresent", 1);
    argMap.put("setBrowserLogLevel", 1);
    argMap.put("runScript", 1);
    argMap.put("removeScript", 1);
    argMap.put("useXpathLibrary", 1);
    argMap.put("setContext", 1);
    argMap.put("captureScreenshot", 1);
    argMap.put("captureNetworkTraffic", 1);
    argMap.put("captureEntirePageScreenshotToString", 1);
    argMap.put("keyDownNative", 1);
    argMap.put("keyUpNative", 1);
    argMap.put("keyPressNative", 1);

    argMap.put("clickAt", 2);
    argMap.put("doubleClickAt", 2);
    argMap.put("contextMenuAt", 2);
    argMap.put("fireEvent", 2);
    argMap.put("keyPress", 2);
    argMap.put("keyDown", 2);
    argMap.put("keyUp", 2);
    argMap.put("mouseDownAt", 2);
    argMap.put("mouseDownRightAt", 2);
    argMap.put("mouseUpAt", 2);
    argMap.put("mouseUpRightAt", 2);
    argMap.put("mouseMoveAt", 2);
    argMap.put("type", 2);
    argMap.put("typeKeys", 2);
    argMap.put("select", 2);
    argMap.put("addSelection", 2);
    argMap.put("removeSelection", 2);
    argMap.put("openWindow", 2);
    argMap.put("getWhetherThisFrameMatchFrameExpression", 2);
    argMap.put("getWhetherThisWindowMatchWindowExpression", 2);
    argMap.put("waitForPopUp", 2);
    argMap.put("dragdrop", 2);
    argMap.put("dragAndDrop", 2);
    argMap.put("setCursorPosition", 2);
    argMap.put("isOrdered", 2);
    argMap.put("assignId", 2);
    argMap.put("waitForCondition", 2);
    argMap.put("waitForFrameToLoad", 2);
    argMap.put("createCookie", 2);
    argMap.put("deleteCookie", 2);
    argMap.put("addLocationStrategy", 2);
    argMap.put("captureEntirePageScreenshot", 2);
    argMap.put("rollup", 2);
    argMap.put("addScript", 2);
    argMap.put("attachFile", 2);
    argMap.put("addCustomRequestHeader", 2);*/
  }

  public MyWebDriverBackedSelenium(final WebDriver baseDriver, final String baseUrl) {
    super(baseDriver, baseUrl);
  }

  public final String doCommand(final WebEventElement elem) {
    String result = null;
    String command = elem.getEventType();

    //TODO implement STORE
    // Refer to https://code.google.com/p/selenium/source/browse/ide/plugins/java-format/src/content/formats/java-backed-junit4.js
    if (this.isStoreCommand(command)) {
      this.doStoreCommand(elem);
    } else if (this.isAssertNotCommand(command)) {
      this.doAssertNotCommand(elem);
    } else if (this.isAssertCommand(command)) {
      this.doAssertCommand(elem);
    } else if (this.isVerifyNotCommand(command)) {
      this.doVerifyNotCommand(elem);
    } else if (this.isVerifyCommand(command)) {
      this.doVerifyCommand(elem);
    } else if (this.isWaitForNotCommand(command)) {
      this.doWaitForNotCommand(elem);
    } else if (this.isWaitForCommand(command)) {
      this.doWaitForCommand(elem);
    } else {
      commandProcessor.doCommand(command, elem.getArgs().toArray(new String[0]));
    }
    return result;
  }

  private void assertEquals(final String actual, final String expected) {
    if (!actual.equals(expected)) {
      throw new AssertionError(String.format("actual: %s, expected: %s", actual, expected));
    }
  }

  private void assertNotEquals(final String actual, final String expected) {
    if (actual.equals(expected)) {
      throw new AssertionError(String.format("actual: %s, expected: %s", actual, expected));
    }
  }

  private void verifyEquals(final String actual, final String expected) {
    if (!actual.equals(expected)) {
      throw new VerifyException(String.format("actual: %s, expected: %s", actual, expected));
    }
  }

  private void verifyNotEquals(final String actual, final String expected) {
    if (actual.equals(expected)) {
      throw new VerifyException(String.format("actual: %s, expected: %s", actual, expected));
    }
  }


  private String getReturnType(final String substring) {
    String returnType = getterApis.get("is" + substring);
    if (returnType != null) {
      return returnType;
    }

    returnType = getterApis.get("get" + substring);
    if (returnType != null) {
      return returnType;
    }
    return null;
  }

  private String getReturnValue(final String substring, final String returnType, final List<String> args) {
    if ("boolean".equals(returnType)) {
      return String.valueOf(commandProcessor.getBoolean("is" + substring, args.toArray(new String[0])));
    } else if ("String".equals(returnType)) {
      return commandProcessor.getString("get" + substring, args.toArray(new String[0]));
    } else if ("String[]".equals(returnType)) {
      return StringUtils.join(commandProcessor.getStringArray("get" + substring, args.toArray(new String[0])), ',');
    } else if ("Number".equals(returnType)) {
      return commandProcessor.getNumber("get" + substring, args.toArray(new String[0])).toString();
    }

    return null;
  }


  private void doStoreCommand(final WebEventElement elem) {
    throw new UnsupportedOperationException("Store command is not supported!");
  }

  private void doAssertCommand(final WebEventElement elem) {
    String substring = elem.getEventType().substring(6);
    String returnType = this.getReturnType(substring);
    String result = this.getReturnValue(substring, returnType, elem.getArgs());

    if (result == null) {
      throw new RuntimeException("null value was returned!");
    }

    if ("boolean".equals(returnType)) {
      this.assertEquals(result, "true");
    } else {
      this.assertEquals(result, elem.getArgs().get(elem.getArgSize() - 1));
    }
  }


  private void doAssertNotCommand(final WebEventElement elem) {
    String substring = elem.getEventType().substring(9);
    String returnType = this.getReturnType(substring);
    String result = this.getReturnValue(substring, returnType, elem.getArgs());

    if (result == null) {
      throw new RuntimeException("null value was returned!");
    }

    if ("boolean".equals(returnType)) {
      this.assertNotEquals(result, "true");
    } else {
      this.assertNotEquals(result, elem.getArgs().get(elem.getArgSize() - 1));
    }

  }

  private void doVerifyCommand(final WebEventElement elem) {
    String substring = elem.getEventType().substring(6);
    String returnType = this.getReturnType(substring);
    String result = this.getReturnValue(substring, returnType, elem.getArgs());

    if (result == null) {
      throw new RuntimeException("null value was returned!");
    }

    if ("boolean".equals(returnType)) {
      this.verifyEquals(result, "true");
    } else {
      this.verifyEquals(result, elem.getArgs().get(elem.getArgSize() - 1));
    }

  }

  private void doVerifyNotCommand(final WebEventElement elem) {
    String substring = elem.getEventType().substring(9);
    String returnType = this.getReturnType(substring);
    String result = this.getReturnValue(substring, returnType, elem.getArgs());

    if (result == null) {
      throw new RuntimeException("null value was returned!");
    }

    if ("boolean".equals(returnType)) {
      this.verifyNotEquals(result, "true");
    } else {
      this.verifyNotEquals(result, elem.getArgs().get(elem.getArgSize() - 1));
    }
  }

  private void doWaitForCommand(final WebEventElement elem) {
    String substring = elem.getEventType().substring(7);
    String returnType = this.getReturnType(substring);

    if (returnType == null) { // if ordinary waitFor function
      commandProcessor.doCommand(elem.getEventType(), elem.getArgs().toArray(new String[0]));
      return;
    }

    String expectedValue = elem.getArgs().get(elem.getArgSize() - 1);

    for (int second = 0;; second++) {
      if (second >= 60) {
        throw new AssertionError("timeout");
      }
      try {
        String result = this.getReturnValue(substring, returnType, elem.getArgs());
        if (expectedValue.equals(result)) {
          break;
        }
      } catch (Exception e) {
        LOG.debug("WaitForNot command... {}", e.toString());
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }

  private void doWaitForNotCommand(final WebEventElement elem) {
    String substring = elem.getEventType().substring(10);
    String returnType = this.getReturnType(substring);

    if (returnType == null) { // if ordinary waitFor function
      commandProcessor.doCommand(elem.getEventType(), elem.getArgs().toArray(new String[0]));
      return;
    }

    String expectedValue = elem.getArgs().get(elem.getArgSize() - 1);

    for (int second = 0;; second++) {
      if (second >= 60) {
        throw new AssertionError("timeout");
      }
      try {
        String result = this.getReturnValue(substring, returnType, elem.getArgs());
        if (!expectedValue.equals(result)) {
          break;
        }
      } catch (Exception e) {
        LOG.debug("WaitForNot command... {}", e.toString());
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }

  private boolean isStoreCommand(final String command) {
    return command.startsWith("store");
  }

  private boolean isAssertCommand(final String command) {
    return command.startsWith("assert");
  }

  private boolean isAssertNotCommand(final String command) {
    return command.startsWith("assertNot");
  }

  private boolean isVerifyCommand(final String command) {
    return command.startsWith("verify");
  }

  private boolean isVerifyNotCommand(final String command) {
    return command.startsWith("verifyNot");
  }

  private boolean isWaitForCommand(final String command) {
    return command.startsWith("waitFor");
  }

  private boolean isWaitForNotCommand(final String command) {

    return command.startsWith("waitForNot");
  }


//  public final String doCommand(final String command) {
//    return commandProcessor.doCommand(command, new String[]{});
//  }
//
//  public final String doCommand(final String command, final String locator) {
//    return commandProcessor.doCommand(command, new String[]{locator, });
//  }
//
//  public final String doCommand(final String command, final String locator, final String input) {
//    Integer argCount = argMap.get(command);
//    if (argCount == null) {
//      throw new RuntimeException(String.format("Command '%s' is not supported!", command));
//    }
//
//    switch (argCount) {
//      case 0:
//        return this.doCommand(command);
//      case 1:
//        return this.doCommand(command, locator);
//      case 2:
//        return commandProcessor.doCommand(command, new String[]{locator, input});
//      default:
//        throw new RuntimeException("Do nothing!");
//    }
//  }

  public static final boolean exists(final String apiName) {
    return apis.containsKey(apiName);
  }

  public static final int getArgCount(final String command) {
    String[] result = apis.get(command);
    if (result == null) {
      throw new RuntimeException(String.format("Event type '%s' does not exist!", command));
    } else {
      return result.length;
    }
  }

  public static boolean isLocationBasedInputAction(final String eventType) {
    return locationBasedInputActionSet.contains(eventType);
  }

  public static boolean isLocationBasedAction(final String eventType) {
    return locationBasedActionSet.contains(eventType);
  }

  public static boolean requireInput(final WebEventElement eventElem) {
    String command = eventElem.getEventType();
    if (!exists(command)) {
      return false;
    }

    int argFullSize = getArgCount(command);
    return eventElem.getArgSize() != argFullSize;
  }
}

/*

void click(String locator)
void doubleClick(String locator)
void contextMenu(String locator)
void clickAt(String locator, String coordString)
void doubleClickAt(String locator, String coordString)
void contextMenuAt(String locator, String coordString)
void fireEvent(String locator, String eventName)
void focus(String locator)
void keyPress(String locator, String keySequence)
void shiftKeyDown()
void shiftKeyUp()
void metaKeyDown()
void metaKeyUp()
void altKeyDown()
void altKeyUp()
void controlKeyDown()
void controlKeyUp()
void keyDown(String locator, String keySequence)
void keyUp(String locator, String keySequence)
void mouseOver(String locator)
void mouseOut(String locator)
void mouseDown(String locator)
void mouseDownRight(String locator)
void mouseDownAt(String locator, String coordString)
void mouseDownRightAt(String locator, String coordString)
void mouseUp(String locator)
void mouseUpRight(String locator)
void mouseUpAt(String locator, String coordString)
void mouseUpRightAt(String locator, String coordString)
void mouseMove(String locator)
void mouseMoveAt(String locator, String coordString)
void type(String locator, String value)
void typeKeys(String locator, String value)
void setSpeed(String value)
String getSpeed()
String getLog()
void check(String locator)
void uncheck(String locator)
void select(String selectLocator, String optionLocator)
void addSelection(String locator, String optionLocator)
void removeSelection(String locator, String optionLocator)
void removeAllSelections(String locator)
void submit(String formLocator)
void open(String url, String ignoreResponseCode)
void open(String url)
void openWindow(String url, String windowID)
void selectWindow(String windowID)
void selectPopUp(String windowID)
void deselectPopUp()
void selectFrame(String locator)
boolean getWhetherThisFrameMatchFrameExpression(String currentFrameString, String target)
boolean getWhetherThisWindowMatchWindowExpression(String currentWindowString, String target)
void waitForPopUp(String windowID, String timeout)
void chooseCancelOnNextConfirmation()
void chooseOkOnNextConfirmation()
void answerOnNextPrompt(String answer)
void goBack()
void refresh()
void close()
boolean isAlertPresent()
boolean isPromptPresent()
boolean isConfirmationPresent()
String getAlert()
String getConfirmation()
String getPrompt()
String getLocation()
String getTitle()
String getBodyText()
String getValue(String locator)
String getText(String locator)
void highlight(String locator)
String getEval(String script)
boolean isChecked(String locator)
String getTable(String tableCellAddress)
String[] getSelectedLabels(String selectLocator)
String getSelectedLabel(String selectLocator)
String[] getSelectedValues(String selectLocator)
String getSelectedValue(String selectLocator)
String[] getSelectedIndexes(String selectLocator)
String getSelectedIndex(String selectLocator)
String[] getSelectedIds(String selectLocator)
String getSelectedId(String selectLocator)
boolean isSomethingSelected(String selectLocator)
String[] getSelectOptions(String selectLocator)
String getAttribute(String attributeLocator)
boolean isTextPresent(String pattern)
boolean isElementPresent(String locator)
boolean isVisible(String locator)
boolean isEditable(String locator)
String[] getAllButtons()
String[] getAllLinks()
String[] getAllFields()
String[] getAttributeFromAllWindows(String attributeName)
void dragdrop(String locator, String movementsString)
void setMouseSpeed(String pixels)
Number getMouseSpeed()
void dragAndDrop(String locator, String movementsString)
void dragAndDropToObject(String locatorOfObjectToBeDragge
void windowFocus()
void windowMaximize()
String[] getAllWindowIds()
String[] getAllWindowNames()
String[] getAllWindowTitles()
String getHtmlSource()
void setCursorPosition(String locator, String position)
Number getElementIndex(String locator)
boolean isOrdered(String locator1, String locator2)
Number getElementPositionLeft(String locator)
Number getElementPositionTop(String locator)
Number getElementWidth(String locator)
Number getElementHeight(String locator)
Number getCursorPosition(String locator)
String getExpression(String expression)
Number getXpathCount(String xpath)
Number getCssCount(String css)
void assignId(String locator, String identifier)
void allowNativeXpath(String allow)
void ignoreAttributesWithoutValue(String ignore)
void waitForCondition(String script, String timeout)
void setTimeout(String timeout)
void waitForPageToLoad(String timeout)
void waitForFrameToLoad(String frameAddress, String timeout)
String getCookie()
String getCookieByName(String name)
boolean isCookiePresent(String name)
void createCookie(String nameValuePair, String optionsString)
void deleteCookie(String name, String optionsString)
void deleteAllVisibleCookies()
void setBrowserLogLevel(String logLevel)
void runScript(String script)
void addLocationStrategy(String strategyName, String functionDefinition)
void captureEntirePageScreenshot(String filename, String kwargs)
void rollup(String rollupName, String kwargs)
void addScript(String scriptContent, String scriptTagId)
void removeScript(String scriptTagId)
void useXpathLibrary(String libraryName)
void setContext(String context)
void attachFile(String fieldLocator, String fileLocator)
void captureScreenshot(String filename)
String captureScreenshotToString()
String captureNetworkTraffic(String type)
void addCustomRequestHeader(String key, String value)
String captureEntirePageScreenshotToString(String kwargs)
void shutDownSeleniumServer()
String retrieveLastRemoteControlLogs()
void keyDownNative(String keycode)
void keyUpNative(String keycode)
void keyPressNative(String keycode)
 */


/*
void shiftKeyDown()
void shiftKeyUp()
void metaKeyDown()
void metaKeyUp()
void altKeyDown()
void altKeyUp()
void controlKeyDown()
void controlKeyUp()
String getSpeed()
String getLog()
void deselectPopUp()
void chooseCancelOnNextConfirmation()
void chooseOkOnNextConfirmation()
void goBack()
void refresh()
void close()
boolean isAlertPresent()
boolean isPromptPresent()
boolean isConfirmationPresent()
String getAlert()
String getConfirmation()
String getPrompt()
String getLocation()
String getTitle()
String getBodyText()
String[] getAllButtons()
String[] getAllLinks()
String[] getAllFields()
Number getMouseSpeed()
void windowFocus()
void windowMaximize()
String[] getAllWindowIds()
String[] getAllWindowNames()
String[] getAllWindowTitles()
String getHtmlSource()
String getCookie()
void deleteAllVisibleCookies()
String captureScreenshotToString()
void shutDownSeleniumServer()
String retrieveLastRemoteControlLogs()

void click(String locator)
void doubleClick(String locator)
void contextMenu(String locator)
void focus(String locator)
void mouseOver(String locator)
void mouseOut(String locator)
void mouseDown(String locator)
void mouseDownRight(String locator)
void mouseUp(String locator)
void mouseUpRight(String locator)
void mouseMove(String locator)
void setSpeed(String value)
void check(String locator)
void uncheck(String locator)
void removeAllSelections(String locator)
void submit(String formLocator)
void open(String url)
void selectWindow(String windowID)
void selectPopUp(String windowID)
void selectFrame(String locator)
void answerOnNextPrompt(String answer)
String getValue(String locator)
String getText(String locator)
void highlight(String locator)
String getEval(String script)
boolean isChecked(String locator)
String getTable(String tableCellAddress)
String[] getSelectedLabels(String selectLocator)
String getSelectedLabel(String selectLocator)
String[] getSelectedValues(String selectLocator)
String getSelectedValue(String selectLocator)
String[] getSelectedIndexes(String selectLocator)
String getSelectedIndex(String selectLocator)
String[] getSelectedIds(String selectLocator)
String getSelectedId(String selectLocator)
boolean isSomethingSelected(String selectLocator)
String[] getSelectOptions(String selectLocator)
String getAttribute(String attributeLocator)
boolean isTextPresent(String pattern)
boolean isElementPresent(String locator)
boolean isVisible(String locator)
boolean isEditable(String locator)
String[] getAttributeFromAllWindows(String attributeName)
void setMouseSpeed(String pixels)
void dragAndDropToObject(String locatorOfObjectToBeDragge
Number getElementIndex(String locator)
Number getElementPositionLeft(String locator)
Number getElementPositionTop(String locator)
Number getElementWidth(String locator)
Number getElementHeight(String locator)
Number getCursorPosition(String locator)
String getExpression(String expression)
Number getXpathCount(String xpath)
Number getCssCount(String css)
void allowNativeXpath(String allow)
void ignoreAttributesWithoutValue(String ignore)
void setTimeout(String timeout)
void waitForPageToLoad(String timeout)
String getCookieByName(String name)
boolean isCookiePresent(String name)
void setBrowserLogLevel(String logLevel)
void runScript(String script)
void removeScript(String scriptTagId)
void useXpathLibrary(String libraryName)
void setContext(String context)
void captureScreenshot(String filename)
String captureNetworkTraffic(String type)
String captureEntirePageScreenshotToString(String kwargs)
void keyDownNative(String keycode)
void keyUpNative(String keycode)
void keyPressNative(String keycode)

void clickAt(String locator, String coordString)
void doubleClickAt(String locator, String coordString)
void contextMenuAt(String locator, String coordString)
void fireEvent(String locator, String eventName)
void keyPress(String locator, String keySequence)
void keyDown(String locator, String keySequence)
void keyUp(String locator, String keySequence)
void mouseDownAt(String locator, String coordString)
void mouseDownRightAt(String locator, String coordString)
void mouseUpAt(String locator, String coordString)
void mouseUpRightAt(String locator, String coordString)
void mouseMoveAt(String locator, String coordString)
void type(String locator, String value)
void typeKeys(String locator, String value)
void select(String selectLocator, String optionLocator)
void addSelection(String locator, String optionLocator)
void removeSelection(String locator, String optionLocator)
void open(String url, String ignoreResponseCode)
void openWindow(String url, String windowID)
boolean getWhetherThisFrameMatchFrameExpression(String currentFrameString, String target)
boolean getWhetherThisWindowMatchWindowExpression(String currentWindowString, String target)
void waitForPopUp(String windowID, String timeout)
void dragdrop(String locator, String movementsString)
void dragAndDrop(String locator, String movementsString)
void setCursorPosition(String locator, String position)
boolean isOrdered(String locator1, String locator2)
void assignId(String locator, String identifier)
void waitForCondition(String script, String timeout)
void waitForFrameToLoad(String frameAddress, String timeout)
void createCookie(String nameValuePair, String optionsString)
void deleteCookie(String name, String optionsString)
void addLocationStrategy(String strategyName, String functionDefinition)
void captureEntirePageScreenshot(String filename, String kwargs)
void rollup(String rollupName, String kwargs)
void addScript(String scriptContent, String scriptTagId)
void attachFile(String fieldLocator, String fileLocator)
void addCustomRequestHeader(String key, String value)
 */

// testCase.js
/*
ommand.prototype.getDefinition = function() {
	if (this.command == null) return null;
	var commandName = this.command.replace(/AndWait$/, '');
	var api = Command.loadAPI();
	var r = /^(assert|verify|store|waitFor)(.*)$/.exec(commandName);
	if (r) {
		var suffix = r[2];
		var prefix = "";
		if ((r = /^(.*)NotPresent$/.exec(suffix)) != null) {
			suffix = r[1] + "Present";
			prefix = "!";
		} else if ((r = /^Not(.*)$/.exec(suffix)) != null) {
			suffix = r[1];
			prefix = "!";
		}
		var booleanAccessor = api[prefix + "is" + suffix];
		if (booleanAccessor) {
			return booleanAccessor;
		}
		var accessor = api[prefix + "get" + suffix];
		if (accessor) {
			return accessor;
		}
	}
	return api[commandName];
}

CommandDefinition.getAlternative = function(command, alternative) {
  if (command == null) return '';
  var alt = alternative;
  var r = /^(.*?)(AndWait)?$/.exec(command);
  var commandName = r[1];
  var prefix = '';
  var suffix = r[2] ? r[2] : '';
  var negate = false;
  r = /^(assert|verify|store|waitFor)(.*)$/.exec(commandName);
  if (r) {
    prefix = r[1];
    var commandName = r[2];
    if ((r = /^(.*)NotPresent$/.exec(commandName)) != null) {
      negate = true;
    } else if ((r = /^Not(.*)$/.exec(commandName)) != null) {
      negate = true;
    }
    if (negate) {
      if (alt.match(/Present$/)) {
        alt = alt.replace(/Present$/, 'NotPresent');
      } else {
        prefix += 'Not';
      }
    }
  }

  return prefix + (prefix.length > 0 ? alt.charAt(0).toUpperCase() : alt.charAt(0).toLowerCase()) + alt.substr(1) + suffix;
};


 */
