package org.example.page;

import org.example.page.block.PopularBlock;
import org.example.page.block.TodayInCinemaBlock;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(css = "[data-tid='5f37709c']")
    private TodayInCinemaBlock todayInCinemaBlock;

    @FindBy(css = "[class *= 'popularPosts'], " +
            "[data-tid='d326019b']")
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
