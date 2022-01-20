package org.example.page.block;

import com.github.webdriverextensions.Bot;
import io.qameta.allure.Step;
import org.example.util.validation.Validation;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Stream;

import static com.github.webdriverextensions.Bot.waitUntil;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.example.config.WebContext.getJSExecutor;
import static org.example.config.WebContext.isMobile;

public class TodayInCinemaBlock extends WebBlock {
    @FindBy(css = ".film-page-section-title a, " +
            "[class ^= 'styles_title']")
    private WebElement title;

    @FindBy(css = "[class *= 'moreItem']")
    private WebElement titleNumber;

    @FindBy(css = "[class *= 'additionalLink']," +
            "[class *= 'root'] [class *= 'link']")
    private WebElement allTicketsLink;

    @FindBy(css = "[class *= 'carouselItem']:nth-of-type(n):not([aria-hidden = 'true'])")
    private List<Poster> carouselItems;

    @FindBy(css = "[class *= 'carousel']")
    private WebElement carouselWrapper;

    @FindBy(css = "[class *= 'moreButtonLink']")
    private WebElement lastItem;

    @FindBy(css = "[class *= 'total']")
    private WebElement lastItemTotal;

    @FindBy(css = "[class *= 'total'] [class *= 'value']")
    private WebElement lastItemValue;

    @FindBy(css = "[class *= 'total'] [class *= 'title']")
    private WebElement lastItemTitle;

    @FindBy(css = "[class *= 'RightButton']")
    private WebElement nextItemsBtn;

    public TodayInCinemaBlock() {
        super();
    }

    @Step("Проверить отображение блока 'Билеты в кино'")
    public TodayInCinemaBlock assertDisplay() {
        System.out.println("WHERE FIX");
        System.out.println(isMobile());
        Assert.assertTrue(
                getWrappedWebElement().isDisplayed(),
                "Контейнер блока не отображается"
        );
        return this;
    }

    @Step("Проверить, что блок имеет название '{titleText}'")
    public TodayInCinemaBlock assertTitle(String titleText) {
        String actualTitle = getJSExecutor().
                getAttribute(title, "firstChild.data");
        Assert.assertEquals(actualTitle, titleText);
        return this;
    }

    @Step("Проверить у названия блока корректность ссылки '{titleLink}'")
    public TodayInCinemaBlock assertTitleLink(String titleLink) {
        if (isMobile()) {
            return this;
        }
        String actualTitleLink = title.getAttribute("href");
        Assert.assertTrue(actualTitleLink.matches(".*?" + titleLink + "/?"),
                "Ссылка заголовка имеет неправильный путь '" + actualTitleLink + "' (должно быть '" + titleLink + "')");
        return this;
    }

    @Step("Проверить отображение ссылки Все билеты")
    public TodayInCinemaBlock assertAllTicketsDisplay() {
        Assert.assertTrue(allTicketsLink.isDisplayed(), "Ссылка 'Все билеты' не отображается");
        return this;
    }

    @Step("Проверить коррекность ссылки '{linkText}' на ссылке Все билеты")
    public TodayInCinemaBlock assertAllTicketsLink(String linkText) {
        String actualLink = allTicketsLink.getAttribute("href");
        Assert.assertTrue(actualLink.matches(".*?" + linkText + "/?"),
                "Ссылка заголовка имеет неправильный путь '" + actualLink + "' (должно быть '" + linkText + "')");
        return this;
    }

    @Step("Проверить отображение карусели")
    public TodayInCinemaBlock assertCarouselDisplay() {
        Assert.assertTrue(carouselWrapper.isDisplayed(),
                "Контйнер карусели не отображается");
        return this;
    }

    @Step("Проверить отображение элементов карусели")
    public TodayInCinemaBlock assertCarouselItemsDisplay() {
        iterateCarousel()
                .forEach(poster -> {
                    getJSExecutor().scrollTo(poster.getWrappedWebElement());
                    poster.assertDisplay();
                });
        resetCarousel();
        return this;
    }

    @Step("Прокрутить карусель в начало")
    public TodayInCinemaBlock resetCarousel() {
        Bot.scrollTo(iterateCarousel().findFirst().get());
        return this;
    }

    @Step("Проверить наличие заголовков у элементов карусели")
    public TodayInCinemaBlock assertCarouselItemTitle() {
        iterateCarousel()
                .forEach(poster -> {
                    getJSExecutor().scrollTo(poster.getWrappedWebElement());
                            poster.assertTitle();
                        }
                );
        resetCarousel();
        return this;
    }

    @Step("Проверить год и жанр первых элементов карусели")
    public TodayInCinemaBlock assertItemYearType() {
        iterateCarousel()
                .forEach(poster -> {
                    getJSExecutor().scrollTo(poster.getWrappedWebElement());
                    poster.assertYearAndType();
                });
        resetCarousel();
        return this;
    }

    @Step("Проверить отображение картинки у элементов карусели")
    public TodayInCinemaBlock assertItemImg() {
        iterateCarousel()
                .forEach(poster -> {
                    getJSExecutor().scrollTo(poster.getWrappedWebElement());
                        poster.assertImg();
                });

        resetCarousel();
        return this;

    }

    @Step("Проверить корректность рейтинга у элементов карусели")
    public TodayInCinemaBlock assertItemRating() {
        iterateCarousel()
                .forEach(poster -> {
                    getJSExecutor().scrollTo(poster.getWrappedWebElement());
                    poster.assertRating();
                });

        resetCarousel();
        return this;
    }

    @Step("Проверить корректность ссылки у элементов карусели")
    public TodayInCinemaBlock assertItemLink() {
        iterateCarousel()
                .forEach(poster -> {
                    getJSExecutor().scrollTo(poster.getWrappedWebElement());
                    poster.assertLink();
                });

        resetCarousel();
        return this;
    }


    @Step("Листать карусель до конца (вправо)")
    public TodayInCinemaBlock navigateToLastItem() {
        while (nextItemsBtn.isDisplayed()) {
            nextCarouselItems();
        }
        return this;
    }

    public TodayInCinemaBlock nextCarouselItems() {
        if (nextItemsBtn.isDisplayed()) {
            nextItemsBtn.click();
        }
        return this;
    }

    @Step("Проверить отображение последнего элемента карусели")
    public TodayInCinemaBlock assertLastItemDisplay() {
        try {
            Assert.assertTrue(lastItem.isDisplayed());
            System.out.println("LAST ITEM: " + lastItem.isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Последний элемент карусели не отображается");
        }
        return this;
    }

    @Step("Проверить валидность текста последнего элемента карусели")
    public TodayInCinemaBlock assertValidTextLastItem(String titleText) {
        boolean isValidValue = false;
        boolean isValidTitle = false;
        String rawValue = "";
        String rawTitle = "";
        try {
            rawValue = lastItemValue.getText();
            rawTitle = lastItemTitle.getText();
            isValidValue = rawValue.matches("\\d+");
            isValidTitle = rawTitle.equals(titleText);
        } catch (NoSuchElementException e) {
            Assert.fail("Последний элемент карусели не отображается");
        }

        Assert.assertTrue(isValidValue,
                "Значение последнего элемента некорректное: " + rawValue);
        Assert.assertTrue(isValidTitle,
                "Текст последнего элемента некорректен: " + rawTitle);
        return this;
    }

    @Step("Проверить совпадение числа в заголовке и последнем элементе карусели")
    public TodayInCinemaBlock assertLastItemValueEquals() {
        if (isMobile()) {
            return this;
        }
        try {
            String titleNum = titleNumber.getText();
            String lastItemValue = this.lastItemValue.getText();
            Assert.assertEquals(titleNum, lastItemValue);
        } catch (NoSuchElementException e) {
            Assert.fail("Последний элемент карусели не отображается");
        }
        return this;
    }

    @Step("Проверить, что при наведении на последний элемент цвет фона меняется на rgba(255, 102, 0, 1)")
    public TodayInCinemaBlock assertHoverColorLastItem() {
        if (isMobile()) {
            return this;
        }

        try {
            Actions actions = new Actions(getWrappedDriver());
            actions.moveToElement(lastItem)
                    .perform();
            waitUntil(
                    driver -> lastItemTotal.getCssValue("background-color")
                            .equals("rgba(255, 102, 0, 1)")
            );
            String bgcolorHovered = lastItemTotal.getCssValue("background-color");
            Assert.assertEquals(bgcolorHovered, "rgba(255, 102, 0, 1)");
        } catch (NoSuchElementException e) {
            Assert.fail("Последний элемент карусели не отображается");
        }
        return this;
    }

    private Stream<Poster> iterateCarousel() {
        return carouselItems.stream();
    }

    public static class Poster extends WebBlock {
        @FindBy(css = "img[class *= 'poster']")
        private WebElement imgFrame;

        @FindBy(css = "[class *= 'ratingPosterNameplate']")
        private WebElement rating;

        @FindBy(css = "[class *= 'posterLink'], [class ^= 'styles_link']")
        private WebElement link;

        @FindBy(css = "[class *= 'styles_title'] > span, [class *= 'styles_title'] > span > span")
        private WebElement title;

        @FindBy(css = "[class *= 'subtitle'] span, " +
                "div[class *= 'caption']")
        private WebElement yearAndType;

        public Poster() {
            super();
        }

        public String getTitle() {
            return title.getText();
        }

        public String getYearAndType() {
            return yearAndType.getText().trim();
        }

        public Poster assertYearAndType() {
            String rawText = getYearAndType();
            String[] words = rawText.split(", ");

            assertYear(words[0]);
            assertType(words[1]);
            return this;
        }

        private void assertType(String typeText) {
            boolean isCorrectType = isNotBlank(typeText);
            Assert.assertTrue(isCorrectType,
                    "Неверно указан жанр у постера: " + typeText);
        }

        private void assertYear(String yearText) {
            boolean isValidYear = true;
            int year = 0;
            try {
                year = Integer.parseInt(yearText);
            } catch (NumberFormatException e) {
                isValidYear = false;
            }

            boolean isCorrectYear = false;
            if (isValidYear) {
                isCorrectYear = Validation.checkYear(year);
            }

            Assert.assertTrue(isCorrectYear, "Неверно указан год у постера: " + yearText);
        }

        public String getRating() {
            return rating.getText();
        }

        public String getLink() {
            return link.getAttribute("href");
        }

        public Poster assertLink() {
            String rawLink = getLink();

            Assert.assertTrue(Validation.checkFilmLink(rawLink),
                    "Ссылка имеет некорретный путь: " + rawLink);
            return this;
        }

        public Poster assertRating() {
            String rawRating = getRating();
            if (rawRating.equals("–")) {
                Assert.assertTrue(true);
                return this;
            }

            try {
                Assert.assertTrue(Validation.checkRating(Double.parseDouble(rawRating)),
                        "У постера " + this + " неверено указан рейтинг: " + rawRating);
            } catch (NumberFormatException e) {
                Assert.fail("У постера " + this + " неверено указан рейтинг: " + rawRating);
            }

            return this;
        }

        public Poster assertDisplay() {
            Assert.assertTrue(getWrappedWebElement().isDisplayed(), "Не отображается постер: " + getWrappedWebElement());
            return this;
        }

        public Poster assertImg() {
            Assert.assertTrue(imgFrame.isDisplayed(), "" +
                    "Не отображается картинка у постера: " + this);
            return this;
        }

        @Override
        public String toString() {
            return "Poster{" +
                    ", rating=" + getRating() +
                    ", link=" + getLink() +
                    ", title=" + getTitle() +
                    ", yearAndType=" + getYearAndType() +
                    '}';
        }

        public Poster assertTitle() {
            String titleText = getTitle();
            Assert.assertTrue(isNotBlank(titleText),
                    "Постер: " + this + " имеет неверный заголовок: " + titleText);
            return this;
        }
    }

}
