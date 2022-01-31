package org.example.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.config.ConfProperties;
import org.example.page.HomePage;
import org.example.page.block.PopularBlock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static org.example.config.WebContext.isMobile;
import static org.example.config.WebContext.set;

@Epic("Блок Популярное")
public class PopularTest extends BaseTest {
    @BeforeMethod
    public void openPage() {
        open(
                ConfProperties.getProperty("urls.home-page")
        );
        set();
    }

    @Test(description = "Основные проверки")
    @Description("Регрессионные проверки на ссылки и заголовки")
    @Severity(SeverityLevel.BLOCKER)
    public void generalTest() {
        PopularBlock popularBlock = page(HomePage.class)
                .getPopularBlock()
                .assertDisplay()
                .assertTitle("Популярное");
        if (isMobile()) {
            popularBlock.assertSelectedTitle("Главное сегодня");
        }
        popularBlock
                .assertNewsDisplay()
                .assertNewsOrdinal()
                .assertNewComment();
    }

    @Test(description = "Основные проверки")
    @Description("Регрессионные проверки на ссылки и заголовки")
    @Severity(SeverityLevel.BLOCKER)
    public void firstNewsTest() {
        page(HomePage.class)
                .getPopularBlock()
                .assertFirstNewsImg()
                .assertFirstNewsBoldTitle()
                .assertFirstNewsLinks()
                .assertFirstNewsHoverTitle();
    }
}
