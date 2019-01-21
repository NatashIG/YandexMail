package com.epam.atm.mailservice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class GeneralConditions {

     WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nataha\\Desktop\\AT_CDP\\WebDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://mail.yandex.by/");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @BeforeMethod
    public void login() {
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        WebElement enterMail = driver.findElement(By.xpath("//a[contains(@class,'HeadBanner-Button-Enter')]"));
        enterMail.click();

        WebElement loginField = driver.findElement(By.name("login"));
        loginField.sendKeys("stellapolare5922");

        WebElement loginWithName = driver.findElement(By.xpath("//button[@type='submit']"));
        loginWithName.click();

        WebElement passwordField = driver.findElement(By.id("passp-field-passwd"));
        passwordField.sendKeys("wdlearning");

        WebElement login = driver.findElement(By.xpath("//button[@type='submit']"));
        login.click();

    }
    @AfterMethod
    public void logoff(){
        WebElement userAvatar = driver.findElement(By.xpath("//span[contains(@class, 'mail-User-Avatar')]"));
        userAvatar.click();

        WebElement logOff = driver.findElement(By.xpath("//a[@data-metric='Выйти из сервисов Яндекса']"));
        logOff.click();
    }
    @AfterClass
    public void quit(){
        driver.quit();
    }
}