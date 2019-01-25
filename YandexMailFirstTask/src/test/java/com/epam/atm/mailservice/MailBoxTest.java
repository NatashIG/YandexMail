package com.epam.atm.mailservice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;


public class MailBoxTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nataha\\Desktop\\AT_CDP\\WebDriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver();
        driver.get("https://yandex.by/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeMethod
    public void login() {
        WebElement enterMailBox = driver.findElement(By.xpath("//a[contains(@class,'button desk-notif-card')]"));
        enterMailBox.click();

        WebElement loginField = driver.findElement(By.name("login"));
        loginField.sendKeys("stellapolare5922");
        
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();

        WebElement passwordField = driver.findElement(By.id("passp-field-passwd"));
        passwordField.sendKeys("wdlearning");

        loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();

        WebElement userName = driver.findElement(By.className("mail-User-Name"));

        Assert.assertTrue(userName.isDisplayed(), "Login is unsuccessful");
    }

   @Test
    public void createNewEmail() {
        WebElement writeLetter = driver.findElement(By.xpath("//span[contains(@class,'mail-ComposeButton-Text')]"));
        writeLetter.click();

        WebElement addressee = driver.findElement(By.name("to"));
        addressee.sendKeys("stella5922@gmail.com");

        WebElement subject = driver.findElement(By.xpath("//input[contains(@class,'mail-Compose-Field-Input-Controller')]"));
        subject.sendKeys("Hello Stella!");

        WebElement emailBody = driver.findElement(By.xpath("//div[@id='cke_1_contents']/textarea"));
        emailBody.sendKeys("Hello!");

        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement savedDraft = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-key='view=compose-autosave-status']"))); //wait.until(ExpectedConditions.visibilityOf(savedDraft));

        WebElement draftsFolder = driver.findElement(By.xpath("//a[@href='#draft']"));
        draftsFolder.click();

        WebElement emailDraft = driver.findElement(By.xpath("//div[@class='mail-MessageSnippet-Content']"));
        Assert.assertTrue(emailDraft.isDisplayed(), "The draft has not been saved");

        emailDraft.click();

        WebElement draftAddressee = driver.findElement(By.name("to"));
        Assert.assertTrue(draftAddressee.getText().contains("stella5922"), "Wrong addressee");

        //WebElement draftSubject = driver.findElement(By.xpath("//input[contains(@class,'mail-Compose-Field-Input-Controller')]")); // пробовала еще это input[contains(@name,'subj')] и это div[@class='mail-Compose-Field-Input']
        //Assert.assertEquals(draftSubject.getText(),"Hello Stella!", "Wrong subject");

        //WebElement draftBody = driver.findElement(By.xpath("//div[@id='cke_50_contents']/textarea")); // div[@id='cke_50_contents']/textarea число в 'cke_50_contents' все время меняется, пробовала textarea[@dir='ltr'], textarea[@id='editor10']
        //Assert.assertEquals(draftBody.getText(), "Hello!","Wrong body");

        WebElement sendButton = driver.findElement(By.xpath("//button[@type='submit']"));
        sendButton.click();

        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        WebElement inboxFolder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'mail-Done')]")));

        WebElement draftsFolder1 = driver.findElement(By.xpath("//a[@href='#draft']"));
        draftsFolder1.click();

        WebElement draftsFolderContent = driver.findElement(By.xpath("//div[contains(@class,'ns-view-messages-list')]"));
        Assert.assertTrue(draftsFolderContent.getText().contains("В папке «Черновики» нет писем."), "The letter has not been sent");

        WebElement sentFolder = driver.findElement(By.xpath("//a[@href='#sent']"));
        sentFolder.click();

        WebElement emailSent = driver.findElement(By.xpath("//div[@class='mail-MessageSnippet-Content']"));
        Assert.assertTrue(emailSent.isDisplayed(), "The letter has not been sent");
    }

   @AfterMethod
   public void logOff() {
       WebElement userAvatar = driver.findElement(By.xpath("//span[contains(@class,'mail-User-Avatar')]"));
       userAvatar.click();

       WebElement logOff = driver.findElement(By.xpath("//a[@data-metric='Выйти из сервисов Яндекса']"));
       logOff.click();

       driver.manage().deleteAllCookies();
   }

   @AfterClass
   public void quit() {
       driver.quit();
    }
}
