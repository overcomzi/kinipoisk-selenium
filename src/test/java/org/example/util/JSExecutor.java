package org.example.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Set;

import static org.example.config.WebContext.getJSExecutor;

public class JSExecutor {
    private JavascriptExecutor executor;

    public JSExecutor(WebDriver driver) {
        this.executor = (JavascriptExecutor) driver;
    }

    public void scrollTo(WebElement element) {
        executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
    }

    public Object executeScript(String script, Object... args) {
        return executor.executeScript(script, args);
    }

    public Object executeAsyncScript(String script, Object... args) {
        return executor.executeAsyncScript(script, args);
    }

    public ScriptKey pin(String script) {
        return executor.pin(script);
    }

    public void unpin(ScriptKey key) {
        executor.unpin(key);
    }

    public Set<ScriptKey> getPinnedScripts() {
        return executor.getPinnedScripts();
    }

    public Object executeScript(ScriptKey key, Object... args) {
        return executor.executeScript(key, args);
    }

    public String getBeforePropertiesString(WebElement element) {
        String script = "return window.getComputedStyle(arguments[0], '::before').getPropertyValue('display');";
        String iconPropertiesString = (String) getJSExecutor().executeScript(script, element);
        return iconPropertiesString;
    }

    public String getAttribute(WebElement element, String name) {
        return getJSExecutor().executeScript("return arguments[0]." + name + ";", element).toString();
    }
}
