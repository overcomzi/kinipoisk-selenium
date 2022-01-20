package org.example.config;

import com.github.webdriverextensions.WebDriverExtensionsContext;
import org.example.util.JSExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebContext {
    private static ThreadLocal<Boolean> mobile = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    private static ThreadLocal<JSExecutor> js = new ThreadLocal<>();


    public static void set(WebDriver driver, boolean isMobile) {
        WebDriverExtensionsContext.setDriver(driver);
        wait.set(new WebDriverWait(driver, WebConfig.getWaitTimeout()));
        mobile.set(isMobile);
        js.set(new JSExecutor(driver));
    }

    public static WebDriver getDriver() {
        return WebDriverExtensionsContext.getDriver();
    }

    public static void removeDriver() {
        getDriver().close();
        WebDriverExtensionsContext.removeDriver();
    }

    public static boolean isMobile() {
        return mobile.get();
    }

    public static JSExecutor getJSExecutor() {
        return js.get();
    }

    public static WebDriverWait getWait() {
        return wait.get();
    }
}
