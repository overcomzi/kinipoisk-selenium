package org.example.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.config.ConfProperties;
import org.example.page.HomePage;
import org.example.page.block.TodayInCinemaBlock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.example.config.WebContext.set;

@Epic("Блок Билеты в кино")
public class TodayInCinemaTest extends BaseTest {
    @BeforeMethod
    public void openPage() {
        open(ConfProperties.getProperty("urls.home-page"));
        set();
    }

    @Test(description = "Основные проверки")
    @Description("Регрессионные проверки на ссылки и заголовки")
    @Severity(SeverityLevel.BLOCKER)
    public void generalTest() {
        TodayInCinemaBlock todayInCinemaBlock = page(HomePage.class)
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
        page(HomePage.class)
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
        page(HomePage.class)
            .getTodayInCinemaBlock()
                .navigateToLastItem()
                .assertLastItemDisplay()
                .assertValidTextLastItem("всего")
                .assertLastItemValueEquals()
                .assertHoverColorLastItem();
    }

}
