package org.example.config;

import org.example.util.JSExecutor;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class WebContext {
    private static ThreadLocal<Boolean> mobile = new ThreadLocal<>();
    private static ThreadLocal<JSExecutor> js = new ThreadLocal<>();

    public static void set() {
        js.set(new JSExecutor(getWebDriver()));
    }

    public static void setMobile(Boolean value) {
        mobile.set(value);
    }

    public static boolean isMobile() {
        return mobile.get();
    }

    public static JSExecutor getJSExecutor() {
        return js.get();
    }
}
