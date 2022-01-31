package org.example.page;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.example.page.block.PopularBlock;
import org.example.page.block.TodayInCinemaBlock;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.FindBy;

public class HomePage {
    @FindBy(css = "section[class *= 'block']")
    private TodayInCinemaBlock todayInCinemaBlock;

    @FindBy(css = "[class *= 'popularPosts'], " +
            "#media-preview-block + div > div:not([class *= 'block'])")
    private PopularBlock popularBlock;

    public HomePage() {
        super();
    }

    public TodayInCinemaBlock getTodayInCinemaBlock() {
        return todayInCinemaBlock;
    }

    public PopularBlock getPopularBlock() {
        return popularBlock;
    }

    @Attachment(type = "image/png")
    public byte[] AttachScreen() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }
}
