package org.example.util;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WindowType;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.example.config.WebContext.getJSExecutor;

public class WebUtils {
    private String originalWindow;

    private WebUtils(){}

    public static WebUtils init() {
        return new WebUtils();
    }

    public  boolean isIconDisplayed(SelenideElement element) {
        String iconPropertiesString = getJSExecutor().getBeforePropertiesString(element);
        return !iconPropertiesString.equals("none");
    }

    public void openNewTab(String url) {
        originalWindow = getWebDriver().getWindowHandle();
        getWebDriver().switchTo().newWindow(WindowType.TAB);
        getWebDriver().get(url);
    }

    public void closeTab() {
        getWebDriver().close();
        getWebDriver().switchTo().window(originalWindow);
    }
}
