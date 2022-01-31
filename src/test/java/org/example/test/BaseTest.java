package org.example.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.codeborne.selenide.testng.ScreenShooter;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.apache.commons.io.FileUtils;
import org.example.config.browsers.ChromeWebDriver;
import org.example.config.browsers.MobileWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static org.example.config.WebConfig.getWaitTimeout;
import static org.example.config.WebContext.setMobile;

@Listeners({ScreenShooter.class})
public abstract class BaseTest extends TestListenerAdapter {
    private String url;

    @BeforeSuite
    public void setUpSuit() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        Configuration.timeout = getWaitTimeout().getSeconds() * 1000;
    }

    @BeforeMethod()
    @Parameters("platform")
    public void setUp(String platform) {
        setPlatform(platform);
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
        Configuration.driverManagerEnabled = false;
        switch (platform.toLowerCase()) {
            case "mobile":
                setMobile(true);
                Configuration.browser = MobileWebDriver.class.getName();
                break;
            default:
                setMobile(false);
                Configuration.browser = ChromeWebDriver.class.getName();
                break;
        }
    }
}
