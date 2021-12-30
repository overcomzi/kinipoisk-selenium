package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.example.config.ConfProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

import static org.apache.commons.lang3.StringUtils.*;

public class BaseTest {
    private final static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String url;

    @BeforeSuite
    public void setUpSuit() {
        String chromedriverPath = ConfProperties.getProperty("chromedriver");
        if (isEmpty(chromedriverPath)) {
            WebDriverManager.chromedriver().setup();
        } else {
            System.setProperty("webdriver.chrome.driver", chromedriverPath);
        }
    }

    @BeforeMethod
    public void setUp() {
        driver.set(new ChromeDriver());
        driver.get().manage().window().maximize();
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(
                ConfProperties.getProperty("wait.seconds")
        ));
        driver.get().manage().timeouts().scriptTimeout(Duration.ofSeconds(
                ConfProperties.getProperty("wait.seconds")
        ));
    }

    @AfterMethod
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.set(null);
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
