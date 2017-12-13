package com.packtpub.springsecurity;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * http://toolsqa.com/selenium-webdriver/how-to-use-geckodriver/
 * https://sites.google.com/a/chromium.org/chromedriver/home
 *
 * http://www.automationtestinghub.com/selenium-3-0-launch-firefox-with-geckodriver/
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = CalendarApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@SpringBootTest(classes = CalendarApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
public class WebDriverTest {

    @LocalServerPort
    private int port;

//    @Value("${server.contextPath}")
//    private String contextPath;

    private WebDriver driver;
    private static String baseDir = "./build/reports/";

    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

//    @Before
    public void setUp()
            throws Exception {

        this.baseUrl = "https://localhost:" + port;
        System.out.println("******************************");
        System.out.println("port: " + port);
        System.out.println("baseUrl: " + baseUrl);

        driver = SeleniumTestUtilities.getChromeDriver();
//        driver = SeleniumTestUtilities.getFirefoxDriver();
//        driver = SeleniumTestUtilities.getHtmlUnitDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void noop() throws Exception {}

//    @Test
    public void testWebDriver() throws Exception {
        driver.get(baseUrl + "/login");
//        driver.wait(10_000L);
        this.captureScreenshot(driver, "login") ;

        driver.findElement(By.name("username")).sendKeys("admin1@example.com");
        driver.findElement(By.name("password")).sendKeys("admin1");
        driver.findElement(By.name("submit")).click();
    }

    //-----------------------------------------------------------------------//
    //    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
    //-----------------------------------------------------------------------//

    public static String captureScreenshot(WebDriver driver,
                                           String screenshotName)
    throws IOException{

        try {
            TakesScreenshot ts = (TakesScreenshot)driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = baseDir + screenshotName + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            return dest;
        }
        catch (IOException e) {return e.getMessage();}
    }

    //-----------------------------------------------------------------------//

    public static void takeSnapShot(WebDriver webdriver,
                                    String fileWithPath)
            throws Exception{

        // Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination
        File DestFile=new File(baseDir + fileWithPath);

        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);

    }
    //-----------------------------------------------------------------------//

    //-----------------------------------------------------------------------//

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
    //-----------------------------------------------------------------------//


} // The End...
