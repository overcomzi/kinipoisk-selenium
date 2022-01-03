package org.example.page.block;

import com.github.webdriverextensions.WebComponent;
import io.qameta.allure.Flaky;
import io.qameta.allure.Step;
import org.example.page.ArticlePage;
import org.example.util.WebUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.github.webdriverextensions.Bot.driver;

public class PopularBlock extends WebComponent {

    @FindBy(css = "[class *= 'title']:first-of-type")
    private WebElement title;

    @FindBy(css = "[class *= 'listItem']")
    private List<NewsItem> news;

    @Step("Проверить отображение блока")
    public PopularBlock assertDisplay() {
        Assert.assertTrue(
                getWrappedWebElement().isDisplayed(),
                "Блок 'Популярное' не отображается"
        );
        return this;
    }


    @Step("Проверить, что заголовок блока имеет название {titleText}")
    public PopularBlock assertTitle(String titleText) {
        Assert.assertEquals(title.getText(), titleText);
        return this;
    }

    @Step("Проверить отображение всех новостей в блоке")
    public PopularBlock assertNewsDisplay() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean isAllNewsDisplayed = iterateNews().allMatch(
                newsItem -> newsItem.isDisplayed()
        );
        isAllNewsDisplayed = isAllNewsDisplayed && news.size() >= 10;

        Assert.assertTrue(isAllNewsDisplayed,
                "Отсутствует какие-то новости в блоке");

        return this;
    }

    @Step("Проверить порядковые номера всех новостей")
    public PopularBlock assertNewsOrdinal() {
        AtomicInteger expectedOrdinal = new AtomicInteger(1);
        iterateNews().forEach(
                newsItem -> newsItem.assertOrdinal(expectedOrdinal.getAndIncrement())
        );

        return this;
    }

    public PopularBlock assertNewsIcon() {
        iterateNews().forEach(
                newsItem ->  newsItem.assertIconDisplay()
        );
        return this;
    }

    @Step("Проверить строку комментариев у ссылки")
    public PopularBlock assertNewComment() {
        assertNewsIcon();
        assertCommentCounts();
        return this;
    }

    @Step("Проверить у первой новости отображение картинки")
    public PopularBlock assertFirstNewsImg() {
       getFirstNews().assertImg();
        return this;
    }

    @Step("Провериту первой новости жирный текста заголовка")
    public PopularBlock assertFirstNewsBoldTitle() {
        getFirstNews().assertBoldTitle();
        return this;
    }

    @Step("Проверить у первой новости совпадение ссылки картинки с ссылкой заголовка")
    public PopularBlock assertFirstNewsLinks() {
        getFirstNews().assertCorrectImgLink();
        return this;
    }

    @Step("Проверить у первой новости hover - цвет меняется на rgba(255, 102, 0, 1)")
    public PopularBlock assertFirstNewsHoverTitle() {
        getFirstNews().assertHoverTitle();
        return this;
    }


    private PopularBlock assertCommentCounts() {
        iterateNews().forEach(
                newsItem -> {
                    newsItem.assertCommentCount();
                }
        );
        return this;
    }

    private Stream<NewsItem> iterateNews() {
        return news.stream();
    }

    private NewsItem getFirstNews() {
        return iterateNews().findFirst().get();
    }

    public static class NewsItem extends WebComponent {
        @FindBy(css = "[class *= 'index']")
        private WebElement ordinal;

        @FindBy(css = "[data-tid='d95eeedb']")
        private WebElement commentRow;

        @FindBy(css = "img[class *= 'image']")
        private WebElement image;

        @FindBy(css = "a[href]:first-of-type")
        private WebElement imageLink;

        @FindBy(css = "[class *= 'titleLink']")
        private WebElement title;

        public String getOrdinal() {
            return ordinal.getText();
        }

        @Step("Проверить порядковый номера у новости")
        public NewsItem assertOrdinal(int expectedOrdinal) {
            assertValidOrdinal();
            assertCorrectOrdinal(expectedOrdinal);
            assertOrdinalColor();
            return this;
        }

        @Step("Проверить, что порядковый номер имеет цвет rgba(255, 102, 0, 1)")
        private void assertOrdinalColor() {
            String actualColor = ordinal.getCssValue("color");
            Assert.assertEquals(actualColor, "rgba(255, 102, 0, 1)");
        }

        private NewsItem assertValidOrdinal() {
            String rawOrdinal = getOrdinal();
            boolean isValidOrdinal = rawOrdinal.matches("(10|[1-9])\\.");
            Assert.assertTrue(isValidOrdinal, "Порядковый номер невалидный: " + rawOrdinal);
            return this;
        }

        private NewsItem assertCorrectOrdinal(int expectedOrdinal) {
            String rawOrdinal = getOrdinal();
            int actualOrdinal = 0;
            actualOrdinal = Integer.parseInt(rawOrdinal.replace(".", ""));
            Assert.assertEquals(actualOrdinal, expectedOrdinal, "Порядковый номер не совпадает с фактическим");
            return this;
        }

        @Flaky
        @Step("Проверить наличие иконки комментариев на ссылке новости")
        public NewsItem assertIconDisplay() {
            boolean iconDisplayed = WebUtils.init(driver())
                    .isIconDisplayed(commentRow);
            Assert.assertTrue(iconDisplayed,
                    "В новости не отображается иконка");
            return this;
        }

        @Step("Сверить кол-во реальных комментариев с числом у новости")
        public NewsItem assertCommentCount() {
            String commentCountRaw = getCommentCount();
            assertValidCommentCount(commentCountRaw);
            assertCorrectCommentCount(Integer.parseInt(commentCountRaw));
            return this;
        }

        private void assertCorrectCommentCount(int actualCommentCount) {
            WebUtils utils = WebUtils.init(driver());
            utils.openNewTab(commentRow.getAttribute("href"));
            int expectedTotalCount = new ArticlePage(driver())
                    .getCommentsBlock()
                    .getTotalCountInt();
            utils.closeTab();
            Assert.assertEquals(actualCommentCount, expectedTotalCount);
        }

        private NewsItem assertValidCommentCount(String commentCountRaw) {
            Assert.assertTrue(commentCountRaw.matches("\\d+"),
                    "Кол-во комментариев невалидное: " + commentCountRaw);
            return this;
        }

        public String getCommentCount() {
            return commentRow.getText();
        }

        public void assertImg() {
            Assert.assertTrue(image.isDisplayed(), "" +
                    "Картинка новости не отображается");
        }

        public void assertBoldTitle() {
            String fontWeight = title.getCssValue("font-weight");
            Assert.assertEquals(fontWeight, "500");
        }

        public NewsItem assertCorrectImgLink() {
            String imgLinkHref = imageLink.getAttribute("href");
            String titleLinkHref = title.getAttribute("href");

            Assert.assertEquals(imgLinkHref, titleLinkHref);
            return this;
        }

        public NewsItem assertHoverTitle() {
            new Actions(driver()).moveToElement(title).perform();
            String hoveredColor = title.getCssValue("color");
            Assert.assertEquals(hoveredColor, "rgba(255, 102, 0, 1)");
            return this;
        }
    }
}
