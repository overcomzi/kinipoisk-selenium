package org.example.page.block;

import com.github.webdriverextensions.WebComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.example.config.WebContext.getWait;

public abstract class WebBlock extends WebComponent {
    public WebElement waitVisibility(WebElement element) {
       return getWait().until(ExpectedConditions.visibilityOf(element));
    }
}
