package org.example.page;

import com.github.webdriverextensions.WebDriverExtensionFieldDecorator;
import com.github.webdriverextensions.WebDriverExtensionsContext;
import org.example.page.block.CommentsBlock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ArticlePage {
    private WebDriver driver;

    @FindBy(css = "[class $= 'comments-section']")
    private CommentsBlock commentsBlock;

    public ArticlePage(WebDriver driver) {
        WebDriverExtensionsContext.setDriver(driver);
        PageFactory.initElements(new WebDriverExtensionFieldDecorator(driver), this);
        this.driver = driver;
    }

    public CommentsBlock getCommentsBlock() {
        return commentsBlock;
    }
}
