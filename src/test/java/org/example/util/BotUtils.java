package org.example.util;

import com.github.webdriverextensions.Bot;
import com.github.webdriverextensions.WebComponent;
import org.openqa.selenium.WebElement;

public class BotUtils extends Bot {
    public static Object scrollTo(WebElement webElement) {
        return webElement instanceof WebComponent ? executeJavascript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", ((WebComponent)webElement).getWrappedWebElement()) : executeJavascript("arguments[0].scrollIntoView(true);", webElement);
    }
}
