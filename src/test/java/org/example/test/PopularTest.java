package org.example.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.config.ConfProperties;
import org.example.page.HomePage;
import org.example.page.block.PopularBlock;
import org.example.util.WebUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.example.config.WebContext.isMobile;

@Epic("Блок Популярное")
public class PopularTest extends BaseTest {
    @BeforeMethod
    public void openPage() {
        WebUtils.init().open(
                ConfProperties.getProperty("urls.home-page")
        );
    }

    @Test(description = "Основные проверки")
    @Description("Регрессионные проверки на ссылки и заголовки")
    @Severity(SeverityLevel.BLOCKER)
    public void generalTest() {
        PopularBlock popularBlock = new HomePage()
                .getPopularBlock()
                .assertDisplay()
                .assertTitle("Популярное");
        if (isMobile()) {
            popularBlock.assertSelectedTitle("Главное сегодня");
        }
        popularBlock
                .assertNewsDisplay();
        if (!isMobile()) {
            popularBlock.assertNewsOrdinal();
        }
//        popularBlock.assertNewComment();
    }

    @Test(description = "Основные проверки")
    @Description("Регрессионные проверки на ссылки и заголовки")
    @Severity(SeverityLevel.BLOCKER)
    public void firstNewsTest() {
        new HomePage()
                .getPopularBlock()
                .assertFirstNewsImg()
                .assertFirstNewsBoldTitle()
                .assertFirstNewsLinks()
                .assertFirstNewsHoverTitle();
    }


}
