package org.example.page.block;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommentsBlock extends WebBlock {
    @FindBy(css = ".media-comments-title__total-count")
    private WebElement totalCount;

    public int getTotalCountInt() {
       return Integer.parseInt(totalCount.getText());
    }
}
