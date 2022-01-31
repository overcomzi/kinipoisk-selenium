package org.example.util;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebDriver;

import java.util.Set;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.example.config.WebContext.getJSExecutor;

public class JSExecutor {
    private JavascriptExecutor executor;

    public JSExecutor(WebDriver driver) {
        this.executor = (JavascriptExecutor) driver;
    }

    public void scrollTo(SelenideElement element) {
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

    public String getBeforePropertiesString(SelenideElement element) {
        String script = "return window.getComputedStyle(arguments[0], '::before').getPropertyValue('display');";
        String iconPropertiesString = (String) getJSExecutor().executeScript(script, element);
        return iconPropertiesString;
    }

    public String getAttribute(SelenideElement element, String name) {
        return executeJavaScript("return arguments[0]." + name + ";", element).toString();
    }
}
