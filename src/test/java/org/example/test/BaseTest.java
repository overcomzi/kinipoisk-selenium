package org.example.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.example.config.ConfProperties;
import org.example.config.WebConfig;
import org.example.config.WebContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.example.config.WebContext.getDriver;

public abstract class BaseTest {
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

    @BeforeMethod()
    @Parameters("platform")
    public void setUp(String platform) {
        setPlatform(platform);
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                WebConfig.getWaitTimeout().getSeconds()
        ));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(
                WebConfig.getWaitTimeout().getSeconds()
        ));
    }

    private void setPlatform(String platform) {
        WebDriver webDriver;
        boolean isMobile = false;
        System.out.println("PLATFORM " + platform );

        switch (platform.toLowerCase()) {
            case "web":
                System.out.println("WEB");
                webDriver = new ChromeDriver();
                break;

            case "mobile":
                isMobile = true;
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Nexus 5");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                webDriver = new ChromeDriver(chromeOptions);
                break;
            default:
                webDriver = new ChromeDriver();
                break;
        }

        WebContext.set(webDriver, isMobile);
    }


    @AfterMethod
    public void onTestFailure(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            makeScreenshotAttachment();
        }
    }

    private void makeScreenshotAttachment() {
        InputStream is = makeScreenshot("Вложение");
        Allure.addAttachment("Вложение", is);
    }

    private InputStream makeScreenshot(String title) {

        byte[] screenshotBytes = ((TakesScreenshot)  getDriver()).getScreenshotAs(OutputType.BYTES);
        InputStream is = new ByteArrayInputStream(screenshotBytes);
        return is;
    }

    @AfterMethod
    public void tearDown() {
       WebContext.removeDriver();
    }
}
