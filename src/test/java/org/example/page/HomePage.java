package org.example.page;

import org.example.page.block.PopularBlock;
import org.example.page.block.TodayInCinemaBlock;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
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
}
