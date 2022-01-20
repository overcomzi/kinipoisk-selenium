package org.example.page;

import com.github.webdriverextensions.WebDriverExtensionFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import static org.example.config.WebContext.getDriver;

public abstract class BasePage {
    public BasePage() {
        PageFactory.initElements(new WebDriverExtensionFieldDecorator(getDriver()), this);
    }
}
