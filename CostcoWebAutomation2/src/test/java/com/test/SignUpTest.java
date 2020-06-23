package com.test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.itexps.costcowebautomation2.FileUtil;
import com.itexps.costcowebautomation2.LoginVO;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author gregshadd
 */
public class SignUpTest {

    private WebDriver driver;
    private String baseURL;
    private static LoginVO login = null;

    public SignUpTest() {
    }

    @BeforeClass
    public static void setUpClass() {
         login=FileUtil.getLoginData();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\QA\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        baseURL = "https://www.costco.com";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    //@Test
    public void test36CreateAccount() {
        driver.get(baseURL);
        WebElement register = driver.findElement(By.id("header_sign_in"));
        register.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");
        WebElement createAccount = driver.findElement(By.xpath("//*[@id=\"LogonForm\"]/fieldset/div[8]/a"));
        createAccount.click();
        js.executeScript("window.scrollBy(0,250)", "");
        WebElement why = driver.findElement(By.className("tooltip-without-question-icon"));
        why.click();
    }
    //@Test
    public void test53UpdateAccountInfo() {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("header_sign_in")).click();
        driver.findElement(By.id("logonId")).click();
        driver.findElement(By.id("logonId")).clear();
        driver.findElement(By.id("logonId")).sendKeys("youremail@you.com");
        driver.findElement(By.id("logonPassword")).click();
        driver.findElement(By.id("logonPassword")).clear();
        driver.findElement(By.id("logonPassword")).sendKeys("123###XXX");
        driver.findElement(By.xpath("//input[@value='Sign In']")).click();
        driver.findElement(By.xpath("//a[@id='myaccount-d']/i")).click();
        driver.findElement(By.linkText("Account Details")).click();
        driver.findElement(By.id("edit-mobile-phone")).click();
        driver.findElement(By.id("update-mobile")).clear();
        driver.findElement(By.id("update-mobile")).sendKeys("6309951266");
        driver.findElement(By.id("update-mobile")).submit();
        //driver.findElement(By.id("update-mobile")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("edit-mobile-phone")).click();
        driver.findElement(By.id("update-primary-mobile-phone-btn")).click();
        
        String mobileudatetext1 = driver.findElement(By.id("mobile-phone-value")).getText();
        String mobileudatetext = driver.findElement(By.xpath("//*[@id=\"notification-message\"]/p")).getText();
        System.out.println("MOBILE IS NOW: " +mobileudatetext1);
        assertEquals("Information Updated",mobileudatetext );

    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
