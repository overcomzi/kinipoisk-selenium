package org.example.test;

import org.example.config.ConfProperties;
import org.example.models.newTrailers.TrailerItem;
import org.example.page.HomePage;
import org.example.page.block.TrailersBlock;
import org.example.services.UnitInformService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.example.config.WebContext.set;

public class NewTrailersTest extends BaseTest {
    private UnitInformService unitInformService = new UnitInformService();

    @BeforeMethod
    public void openPage() {
        open(
                ConfProperties.getProperty("urls.home-page")
        );
        set();
    }

    @Test
    public void scrapTrailerItemInfo() {
        TrailersBlock trailersBlock = page(HomePage.class)
                .getTrailersBlock();
        List<TrailerItem> incorrectTrailers = trailersBlock.findTrailersWithIncorrectYearRow();
        unitInformService.publishTrailerInfo(incorrectTrailers);
    }

}
