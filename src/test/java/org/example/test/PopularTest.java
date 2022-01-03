package org.example.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.config.ConfProperties;
import org.example.page.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Блок Популярное")
public class PopularTest extends BaseTest{
    public PopularTest() {
        setUrl(ConfProperties.getProperty("urls.home-page"));
    }

    @BeforeMethod
    public void openPage() {
        open();
    }

    @Test(description = "Основные проверки")
    @Description("Регрессионные проверки на ссылки и заголовки")
    @Severity(SeverityLevel.BLOCKER)
    public void generalTest() {
        new HomePage(getDriver())
                .getPopularBlock()
                .assertDisplay()
                .assertTitle("Популярное")
                .assertNewsDisplay()
                .assertNewsOrdinal()
                .assertNewComment();
    }

    @Test(description = "Основные проверки")
    @Description("Регрессионные проверки на ссылки и заголовки")
    @Severity(SeverityLevel.BLOCKER)
    public void firstNewsTest() {
        new HomePage(getDriver())
                .getPopularBlock()
                .assertFirstNewsImg()
                .assertFirstNewsBoldTitle()
                .assertFirstNewsLinks()
                .assertFirstNewsHoverTitle();
    }


}
