package org.example.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;

public class WebUtils {
    private WebDriver driver;
    private String originalWindow;

    private WebUtils(WebDriver driver) {
        this.driver = driver;
    }

    public static WebUtils init(WebDriver driver) {
        return new WebUtils(driver);
    }
    public String getAttribute(WebElement element, String name) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return arguments[0]." + name + ";", element).toString();
    }

    public boolean isIconDisplayed(WebElement wrapper) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "return window.getComputedStyle(arguments[0], '::before').getPropertyValue('display');";
        String iconPropertiesString = (String) js.executeScript(script, wrapper);
        return !iconPropertiesString.equals("none");
    }

    public void openNewTab(String url) {
        originalWindow = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
    }

    public void closeTab() {
        driver.close();
        driver.switchTo().window(originalWindow);
    }
}
