package org.example.validation;

import java.time.Year;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class Validation {
    public static boolean checkYear(int date) {
        return date > 1900 && date <= Year.now().getValue();
    }

    public static boolean checkRating(double rating) {
        return rating >= 0.0 && rating <= 10.0;
    }

    public static boolean checkFilmLink(String link) {
        return link.matches(".*?/film/\\d+/?");
    }

    public static boolean checkFilmType(String type) {
        return isNotBlank(type);
    }
}
