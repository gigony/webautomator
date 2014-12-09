package edu.unl.webautomator.core.util;

import org.openqa.selenium.By;

/**
 * Created by gigony on 12/9/14.
 */
public class SeleniumHelper {


    public static By convertStringLocatorToBy(String locator){
        if (locator.startsWith("css=")) {
            return By.cssSelector(locator);
        } else if (locator.startsWith("xpath=")) {
            return By.xpath(locator.substring("xpath=".length()));
        } else if (locator.startsWith("//")){
            return By.xpath(locator);
        } else if (locator.startsWith("id=")){
            return By.id(locator.substring("id=".length()));
        } else if (locator.startsWith("name=")) {
            return By.name(locator.substring("name=".length()));
        } else if (locator.startsWith("link=")) {
            return By.linkText(locator.substring("link=".length()));
        } else {
            return By.id(locator);
        }
        // TODO - considering 'class', 'name' or 'tag' is missing here.
    }
}
