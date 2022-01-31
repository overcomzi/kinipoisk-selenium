package org.example.page;

import org.example.page.block.CommentsBlock;
import org.openqa.selenium.support.FindBy;

public class ArticlePage {

    @FindBy(css = "[class $= 'comments-section']")
    private CommentsBlock commentsBlock;

    public ArticlePage() {
        super();
    }

    public CommentsBlock getCommentsBlock() {
        return commentsBlock;
    }
}
