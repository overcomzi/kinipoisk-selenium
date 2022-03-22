package org.example.test;

import org.example.config.ConfProperties;
import org.example.db.dao.TrailerItemDao;
import org.example.models.newTrailers.TrailerItem;
import org.example.page.HomePage;
import org.example.page.block.TrailersBlock;
import org.example.services.UnitInformService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.example.config.WebContext.set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

public class NewTrailersTest extends BaseTest {
    @Mock
    private TrailerItemDao trailerItemDao;
    @InjectMocks
    private UnitInformService unitInformService;

    @BeforeMethod
    public void openPage() {
        MockitoAnnotations.initMocks(this);
        open(
                ConfProperties.getProperty("urls.home-page")
        );
        set();
        mockSetting();
    }

    @Test
    public void scrapTrailerItemInfo() {
        TrailersBlock trailersBlock = page(HomePage.class)
                .getTrailersBlock();
        List<TrailerItem> incorrectTrailers = trailersBlock.findTrailersWithIncorrectYearRow();
        System.out.println("scrap " + incorrectTrailers);
        unitInformService.publishTrailerInfo(incorrectTrailers);
    }

    @Test
    public void checkTrailerInfo() {
        unitInformService.publicTrailerInfo(
                new TrailerItem(2, "2", "2", "2", "2")
        );
        TrailerItem trailer = unitInformService.getTrailer(1);
        System.out.println("Get mock trailer: " + trailer);
    }

    private void mockSetting() {
        Mockito.when(
                trailerItemDao.getTrailer(anyLong())
        ).thenReturn(
                new TrailerItem(1, "1", "1", "1", "1")
        );
        Mockito.when(trailerItemDao.insertTrailer(any(TrailerItem.class)))
                .thenReturn(true);
    }

}
