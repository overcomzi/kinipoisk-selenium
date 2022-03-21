package org.example.page.block;

import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.example.models.newTrailers.TrailerItem;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.example.config.WebContext.getJSExecutor;

public class TrailersBlock extends ElementsContainer {
    @FindBy(css = ".film-trailer")
    private List<TrailerSliderItem> trailers;

    @Step("Найти трейлеры, у которых год выпуска некорректный")
    public List<TrailerItem> findTrailersWithIncorrectYearRow() {
        getSelf().scrollIntoView(true);

        return trailers.stream()
                .filter(
                        trailer -> {
                            getJSExecutor().scrollTo(trailer.getSelf());
                            boolean matches = trailer.getYearRow().matches("\\d+");
                            return !matches;
                        }
                )
                .map(trailer -> new TrailerItem(
                        trailer.getFilmId(),
                        trailer.getTitle(),
                        trailer.getGenre(),
                        trailer.getYearRow(),
                        trailer.getHref()
                ))
                .collect(Collectors.toList());
    }

    public static class TrailerSliderItem extends ElementsContainer {
        @FindBy(css = "[class *= 'styles_date']")
        private SelenideElement yearAndGenre;

        @FindBy(css = "[class *= 'styles_title']")
        private SelenideElement title;

        public String getYearRow() {
            String text = yearAndGenre.getText();
            String yearRow = text.split(", ")[0];
            return yearRow;
        }

        public String getGenre() {
            String text = yearAndGenre.getText();
            String genreText = text.split(", ")[1];
            return genreText;
        }

        public String getTitle() {
            return title.getText();
        }

        public long getFilmId() {
            String href = title.attr("href");
            Pattern pattern = Pattern.compile(".*?/(film|series)/(\\d+)/.*?");
            Matcher matcher = pattern.matcher(href);
            if (matcher.find()) {
                return Long.parseLong(matcher.group(2));
            }
            throw new RuntimeException("Film id not found");
        }

        public String getHref() {
            return title.attr("href");
        }
    }
}
