package com.packtpub.springsecurity.web;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SmokeTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/calendar";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSmoke() throws Exception {
    driver.get(baseUrl + "/calendar/login");
    driver.findElement(By.name("username")).clear();
    driver.findElement(By.name("username")).sendKeys("user1@example.com");
    driver.findElement(By.name("password")).clear();
    driver.findElement(By.name("password")).sendKeys("user1");
    driver.findElement(By.name("submit")).click();
    driver.findElement(By.id("navEventsLink")).click();
    driver.findElement(By.id("navMyEventsLink")).click();
    driver.findElement(By.linkText("Conference Call")).click();
    driver.findElement(By.id("navCreateEventLink")).click();
    driver.findElement(By.id("auto")).click();
    driver.findElement(By.id("submit")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

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
}
