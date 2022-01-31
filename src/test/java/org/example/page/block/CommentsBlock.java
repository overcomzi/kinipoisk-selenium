package org.example.page.block;

import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class CommentsBlock extends ElementsContainer {
    @FindBy(css = ".media-comments-title__total-count")
    private SelenideElement totalCount;

    public int getTotalCountInt() {
       return Integer.parseInt(totalCount.getText());
    }
}
