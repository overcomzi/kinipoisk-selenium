package org.example.page.block;

import com.github.webdriverextensions.WebComponent;
import io.qameta.allure.Flaky;
import io.qameta.allure.Step;
import org.example.page.ArticlePage;
import org.example.util.WebUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.github.webdriverextensions.Bot.scrollTo;
import static org.example.config.WebContext.getDriver;
import static org.example.config.WebContext.isMobile;

public class PopularBlock extends WebBlock {
    @FindBy(css = "[class *= 'title']:first-of-type," +
            "[data-tid='db03ddb6']:nth-of-type(2)")
    private WebElement title;

    @FindBy(css = "[data-tid='db03ddb6']:first-of-type")
    private WebElement selectedTitle;

    @FindBy(css = "[class *= 'listItem'], [data-tid='31b6667b']")
    private List<NewsItem> news;

    public PopularBlock() {
        super();
    }

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

    public PopularBlock assertFullTitle(String titleText, String selectedText) {
        assertTitle(titleText);
        assertSelectedTitle(selectedText);
        return this;
    }

    public PopularBlock assertSelectedTitle(String selectedText) {
        Assert.assertEquals(selectedTitle.getText(), selectedText);
        return this;
    }

    @Step("Проверить отображение всех новостей в блоке")
    public PopularBlock assertNewsDisplay() {
        boolean isAllNewsDisplayed = iterateNews().allMatch(
                WebComponent::isDisplayed
        );
        boolean isCorrectSize = news.size() >= 10;
        if (isMobile()) {
            isCorrectSize = news.size() >= 5;
        }

        isAllNewsDisplayed = isAllNewsDisplayed && isCorrectSize;

        Assert.assertTrue(isAllNewsDisplayed,
                "Отсутствует какие-то новости в блоке");

        return this;
    }

    @Step("Проверить порядковые номера всех новостей")
    public PopularBlock assertNewsOrdinal() {
        if (isMobile()) {
            return this;
        }
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
        if (isMobile()) {
            return this;
        }
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

        @FindBy(css = "img[class *= 'image'], img[class *= 'featuredImage']")
        private WebElement image;

        @FindBy(css = "a[href]:first-of-type")
        private WebElement imageLink;

        @FindBy(css = "[class *= 'titleLink']")
        private WebElement title;

        private boolean isMobile = false;

        public boolean isMobile() {
            return isMobile;
        }

        public void setMobile() {
            isMobile = true;
        }

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
            try {
                boolean iconDisplayed =
                        WebUtils.init().isIconDisplayed(commentRow);
                Assert.assertTrue(iconDisplayed,
                        "В новости не отображается иконка");
            } catch (NoSuchElementException e) {
                Assert.fail("В новости не отображается иконка");
            }
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
            WebUtils utils = WebUtils.init();
            utils.openNewTab(commentRow.getAttribute("href"));
            int expectedTotalCount = new ArticlePage()
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
            scrollTo(getWrappedWebElement());
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
            new Actions(getDriver()).moveToElement(title).perform();
            String hoveredColor = title.getCssValue("color");
            Assert.assertEquals(hoveredColor, "rgba(255, 102, 0, 1)");
            return this;
        }
    }
}
