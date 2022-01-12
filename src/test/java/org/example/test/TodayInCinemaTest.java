package org.example.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.config.ConfProperties;
import org.example.page.HomePage;
import org.example.page.block.TodayInCinemaBlock;
import org.example.util.WebUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Блок Билеты в кино")
public class TodayInCinemaTest extends BaseTest {
    @BeforeMethod
    public void openPage() {
        WebUtils.init().open(ConfProperties.getProperty("urls.home-page"));
    }

    @Test(description = "Основные проверки")
    @Description("Регрессионные проверки на ссылки и заголовки")
    @Severity(SeverityLevel.BLOCKER)
    public void generalTest() {
        TodayInCinemaBlock todayInCinemaBlock = new HomePage()
                .getTodayInCinemaBlock()
                .assertDisplay()
                .assertTitle("Билеты в кино")
                .assertTitleLink("/afisha/new/city")
                .assertAllTicketsDisplay()
                .assertAllTicketsLink("/afisha/new/city")
                .assertCarouselDisplay();
    }

    @Test()
    public void carouselElementsTest() {
        new HomePage()
                .getTodayInCinemaBlock()
                .assertCarouselItemsDisplay()
                .assertCarouselItemTitle()
                .assertItemYearType()
                .assertItemImg()
                .assertItemRating()
                .assertItemLink();
    }

    @Test()
    public void carouselLastElementTest() {
        new HomePage()
            .getTodayInCinemaBlock()
                .navigateToLastItem()
                .assertLastItemDisplay()
                .assertValidTextLastItem("всего")
                .assertLastItemValueEquals()
                .assertHoverColorLastItem();
    }

}
