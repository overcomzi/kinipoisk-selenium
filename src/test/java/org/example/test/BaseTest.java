package org.example.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.testng.ScreenShooter;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.io.FileUtils;
import org.example.config.ConfProperties;
import org.example.config.browsers.ChromeWebDriver;
import org.example.config.browsers.MobileWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.WebDriverRunner.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.example.config.WebConfig.getWaitTimeout;
import static org.example.config.WebContext.setMobile;

@Listeners({ScreenShooter.class})
public abstract class BaseTest extends TestListenerAdapter {
    private String url;

    @BeforeSuite
    public void setUpSuit() {
        String chromedriverPath = ConfProperties.getProperty("chromedriver");
        if (isEmpty(chromedriverPath)) {
            WebDriverManager.chromedriver().setup();
        } else {
            System.setProperty("webdriver.chrome.driver", chromedriverPath);
        }
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    ;

    @BeforeMethod()
    @Parameters("platform")
    public void setUp(String platform) {
        Configuration.timeout = getWaitTimeout().getSeconds() * 1000;

        setPlatform(platform);
    }

    @AfterMethod
    public void tearDown() {
        closeWebDriver();
    }

    @Override
    @AfterMethod
    public void onTestFailure(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            System.out.println(testResult.getStatus());
            try {
                screenshot();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void screenshot() throws IOException {
        File screenshot = Screenshots.getLastScreenshot();
        File dest = new File("target/allure-results/screenshots/" + screenshot.getName());
        FileUtils.copyFile(screenshot, dest);
        Allure.addAttachment("Вложение", FileUtils.openInputStream(dest));
        return;
    }

    private void setPlatform(String platform) {
        switch (platform.toLowerCase()) {
            case "mobile":
                setMobile(true);
                setWebDriver(new MobileWebDriver().createDriver(new DesiredCapabilities()));
                break;
            default:
                setMobile(false);
                setWebDriver(new ChromeWebDriver().createDriver(new DesiredCapabilities()));
                break;
        }

    }


}
