package org.example.util;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;

import static org.example.config.WebContext.getDriver;
import static org.example.config.WebContext.getJSExecutor;

public class WebUtils {
    private String originalWindow;

    private WebUtils(){}

    public static WebUtils init() {
        return new WebUtils();
    }

    public  boolean isIconDisplayed(WebElement element) {
        String iconPropertiesString = getJSExecutor().getBeforePropertiesString(element);
        return !iconPropertiesString.equals("none");
    }

    public  void open(String url) {
        getDriver().get(url);
    }

    public void openNewTab(String url) {
        originalWindow = getDriver().getWindowHandle();
        getDriver().switchTo().newWindow(WindowType.TAB);
        getDriver().get(url);
    }

    public void closeTab() {
        getDriver().close();
        getDriver().switchTo().window(originalWindow);
    }
}
