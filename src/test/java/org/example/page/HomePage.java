package org.example.page;

import com.github.webdriverextensions.WebDriverExtensionFieldDecorator;
import com.github.webdriverextensions.WebDriverExtensionsContext;
import org.example.page.block.PopularBlock;
import org.example.page.block.TodayInCinemaBlock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    @FindBy(css = "[data-tid='5f37709c']")
    private TodayInCinemaBlock todayInCinemaBlock;

    @FindBy(css = "[class *= 'popularPosts'], " +
            "[data-tid='d326019b']")
    private PopularBlock popularBlock;

    private boolean isMobile;


    public HomePage(WebDriver driver, boolean isMobile) {
        WebDriverExtensionsContext.setDriver(driver);
        PageFactory.initElements(new WebDriverExtensionFieldDecorator(driver), this);
        if (isMobile) {
            setMobile();
        }
        this.driver = driver;
    }

    private void setMobile() {
        todayInCinemaBlock.setMobile();
        popularBlock.setMobile();
    }

    public HomePage(WebDriver driver) {
        WebDriverExtensionsContext.setDriver(driver);
        PageFactory.initElements(new WebDriverExtensionFieldDecorator(driver), this);
        this.driver = driver;
    }

    public TodayInCinemaBlock getTodayInCinemaBlock() {
        return todayInCinemaBlock;
    }

    public PopularBlock getPopularBlock() {
        return popularBlock;
    }

    public boolean isMobile() {
        return isMobile;
    }
}
