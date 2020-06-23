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
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author gregshadd
 */
public class SearchProductTest {

    private WebDriver driver;
    private String baseURL;
    private static LoginVO login = null;

    public SearchProductTest() {
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

    @Test
    public void test38SearchbyItemnumber() {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("search-field")).click();
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys("9550010");
        driver.findElement(By.id("formcatsearch")).submit();

        String itemmodeltext = driver.findElement(By.xpath("//*[@id=\"product-body-model-number\"]/span")).getText();
        assertEquals("OLED55CXAUA.AUS", itemmodeltext);
//LG 55" Class - CX Series - 4K UHD OLED TV - $100 SquareTrade Protection Plan Bundle Included
    }

    //@Test
    public void test40SearchbyInvalidProduct() {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("search-field")).click();
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys("gravemarker");
        driver.findElement(By.id("formcatsearch")).submit();

        String notfoundtext = driver.findElement(By.xpath("//*[@id=\"no-results\"]/h2")).getText();
        assertEquals("We're sorry. We were not able to find a match. ", notfoundtext);
    }

    @Test
    public void test41SearchbyInvalidtoValidProduct() throws InterruptedException {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("search-field")).click();
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys("gravemarker");
        driver.findElement(By.id("formcatsearch")).submit();
        driver.findElement(By.id("search-field2")).click();
        driver.findElement(By.id("search-field2")).clear();
        driver.findElement(By.id("search-field2")).sendKeys("bubble gum");
        driver.findElement(By.id("search-field2")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        String prodfoundtext = driver.findElement(By.id("rsltCntMsg")).getText();
        assertEquals("We found 1 results for \"bubble gum\"", prodfoundtext);

    }

    @Test
    public void test42SelectProductviaText() {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("search-field")).click();
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys("dell laptop");
        driver.findElement(By.id("formcatsearch")).submit();
        driver.findElement(By.linkText("Dell XPS 15 Touchscreen Laptop - 9th Gen Intel Core i9-9980HK - GeForce GTX 1650 - 4K UHD")).click();

        String itemmodeltext = driver.findElement(By.xpath("//*[@id=\"product-body-model-number\"]/span")).getText();
        assertEquals("XPS7590-9589SLV-PUS", itemmodeltext);
    }

    @Test
    public void test43SelectProductviaImage() {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys("dell laptops");
        driver.findElement(By.id("formcatsearch")).submit();
        driver.findElement(By.xpath("//img[@alt='Dell Inspiron 15 3000 Touchscreen Laptop - 10th Gen Intel Core i7-1065G7 - 1080p']")).click();

        String itemmodeltext = driver.findElement(By.xpath("//*[@id=\"product-body-model-number\"]/span")).getText();
        assertEquals("i3593-7098BLK-PUS", itemmodeltext);
    }

    @Test
    public void testContinueShopping8() throws Exception {
        driver.get("https://www.costco.com/");
        Thread.sleep(10000);
        try{
            driver.findElement(By.id("RX_Home_Ancillary_0")).click();
        }catch(NoSuchElementException e){
            System.out.println("No grocery tab detected");
        }
        driver.findElement(By.linkText("Frito Lay Classic Mix, Variety Pack, 30-count")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("add-to-cart-btn")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@id='costcoModalText']/div[2]/div/button")).click();
    }

    @Test
    public void testRemoveItem6() throws Exception {
        driver.get("https://www.costco.com/");
        driver.findElement(By.id("header_sign_in")).click();
        driver.findElement(By.id("logonId")).clear();
        driver.findElement(By.id("logonId")).sendKeys(login.getUsername());
        driver.findElement(By.id("logonPassword")).clear();
        driver.findElement(By.id("logonPassword")).sendKeys(login.getPassword());    //data driven
        Thread.sleep(10000);
        driver.findElement(By.xpath("//input[@value='Sign In']")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//input[@value='Shop as Non-Member']")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//a[@id='cart-d']/div/div")).click();
        Thread.sleep(10000);
        try{
            driver.findElement(By.linkText("Remove")).click();
        }catch(NoSuchElementException e){
            System.out.println("No items in cart");
        }
        
    }
    @Test
    public void testCheckout7() throws Exception {
        driver.get("https://www.costco.com/");
        Thread.sleep(10000);
        try{
            driver.findElement(By.id("RX_Home_Ancillary_0")).click();
        }catch(NoSuchElementException e){
            System.out.println("No grocery tab detected");
        }
        Thread.sleep(3000);
        driver.findElement(By.linkText("Frito Lay Classic Mix, Variety Pack, 30-count")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("add-to-cart-btn")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@id='costcoModalText']/div[2]/div[2]/a/button")).click();
        Thread.sleep(15000);
        driver.findElement(By.id("shopCartCheckoutSubmitButton")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@id='logon']/div/div/div/div")).click();
        assertEquals("Sign in to access your Costco.com account.", driver.findElement(By.xpath("//form[@id='LogonForm']/fieldset/div/span")).getText());
    }

    @Test
    public void testAddMoreItems9() throws Exception {
        driver.get("https://www.costco.com/");
        Thread.sleep(3000);
        driver.findElement(By.id("RX_Home_Ancillary_0")).click();
        Thread.sleep(5000);
        driver.findElement(By.linkText("Frito Lay Classic Mix, Variety Pack, 30-count")).click();
        driver.findElement(By.id("add-to-cart-btn")).click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='costcoModalText']/div[2]/div/button"))).click();
        driver.findElement(By.id("RX_Home_Ancillary_0")).click();
        driver.findElement(By.linkText("Clif Bar Energy Bars, Variety Pack, 2.40 oz, 24-count")).click();
        driver.findElement(By.id("add-to-cart-btn")).click();
        driver.findElement(By.xpath("//div[@id='costcoModalText']/div[2]/div[2]/a/button")).click();
        assertEquals("Frito Lay Classic Mix, Variety Pack, 30-count", driver.findElement(By.linkText("Frito Lay Classic Mix, Variety Pack, 30-count")).getText());
        assertEquals("Clif Bar Energy Bars, Variety Pack, 2.40 oz, 24-count", driver.findElement(By.linkText("Clif Bar Energy Bars, Variety Pack, 2.40 oz, 24-count")).getText());
    }


    @Test
    public void testDelivery10() throws Exception {
        driver.get("https://www.costco.com/");
        driver.findElement(By.id("header_sign_in")).click();
        driver.findElement(By.id("logonId")).clear();
        driver.findElement(By.id("logonId")).sendKeys(login.getUsername());
        driver.findElement(By.id("logonPassword")).clear();
        driver.findElement(By.id("logonPassword")).sendKeys(login.getPassword());    //data driven
        driver.findElement(By.xpath("//input[@value='Sign In']")).click();
        driver.findElement(By.xpath("//input[@value='Shop as Non-Member']")).click();
        driver.findElement(By.id("cart-d")).click();
        Thread.sleep(15000);
        driver.findElement(By.id("shopCartCheckoutSubmitButton")).click();
        assertEquals("Shipping Address", driver.findElement(By.xpath("//div[@id='shipping']/div/div/div/div/h2")).getText());
    }

    @Test
    public void testRemoveAllProducts11() throws Exception {
        boolean found = true;
        driver.get("https://www.costco.com/");
        driver.findElement(By.id("header_sign_in")).click();
        driver.findElement(By.id("logonId")).clear();
        driver.findElement(By.id("logonId")).sendKeys(login.getUsername());
        driver.findElement(By.id("logonPassword")).clear();
        driver.findElement(By.id("logonPassword")).sendKeys(login.getPassword());    //data driven
        driver.findElement(By.id("LogonForm")).submit();
        driver.findElement(By.xpath("//input[@value='Shop as Non-Member']")).click();
        Thread.sleep(10000);
        driver.findElement(By.id("cart-d")).click();
        while(found == true){
            try{
                Thread.sleep(15000);
               driver.findElement(By.linkText("Remove")).click();
            }catch(NoSuchElementException e){
                found = false;
                System.out.println(e);
            }
        }
        assertEquals("Your shopping cart is empty. Please add at least one item to your cart before checking out.", driver.findElement(By.id("empty-cart-id")).getText());
    }

    @Test
    public void testVerifyContinueShopping13() throws Exception {
        driver.get("https://www.costco.com/");
        driver.findElement(By.id("header_sign_in")).click();
        driver.findElement(By.id("logonId")).clear();
        driver.findElement(By.id("logonId")).sendKeys(login.getUsername());
        driver.findElement(By.id("logonPassword")).clear();
        driver.findElement(By.id("logonPassword")).sendKeys(login.getPassword());  // data driven
        driver.findElement(By.xpath("//input[@value='Sign In']")).click();
        driver.findElement(By.xpath("//input[@value='Shop as Non-Member']")).click();
        driver.findElement(By.id("cart-d")).click();
        assertEquals("Your shopping cart is empty. Please add at least one item to your cart before checking out.", driver.findElement(By.xpath("//div[@id='empty-cart-id']/div")).getText());
    }

    @Test
    public void testAddItemNotLoggedIn15() throws Exception {
        driver.get("https://www.costco.com/");
        Thread.sleep(3000);
        try{
            driver.findElement(By.id("RX_Home_Ancillary_0")).click();
        }catch(NoSuchElementException e){
            System.out.println("No grocery tab detected");
        }
        driver.findElement(By.linkText("Frito Lay Classic Mix, Variety Pack, 30-count")).click();
        driver.findElement(By.id("add-to-cart-btn")).click();
        Thread.sleep(4000);
        driver.findElement(By.xpath("//div[@id='costcoModalText']/div[2]/div[2]/a/button")).click();
        Thread.sleep(15000);
        driver.findElement(By.id("shopCartCheckoutSubmitButton")).click();
        assertEquals("Sign in to access your Costco.com account.", driver.findElement(By.xpath("//form[@id='LogonForm']/fieldset/div/span")).getText());
    }
    
    @Test
    public void testAddItem() throws Exception {
        // Add item and view cart before logging in
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(20,250)");
        driver.get("https://www.costco.com/boscia-sake-treatment-water%2c-1.79-fl-oz.product.100534870.html"); //bypass base URL and go to the product
        //driver.get(baseUrl); //from base URL
        //driver.findElement(By.id("navigation-dropdown")).click();
        //driver.findElement(By.xpath("//*[@id=\"navigation-dropdown\"]/i")).click();
        //driver.findElement(By.xpath("//a[@id=\"shop-mt-mobile\"]")).click();
        //driver.findElement(By.xpath("//li[@id=\"292617\"]")).click(); // beauty
        //driver.findElement(By.xpath("//li[@id=\"30851\"]")).click(); // skin care
        //driver.findElement(By.xpath("//a[@href=\"https://www.costco.com/boscia-sake-treatment-water%2c-1.79-fl-oz.product.100534870.html\"]")).click(); //X-path manual for product
        //driver.findElement(By.xpath("//*[@id=\"search-results\"]/div[8]/div/ctl:cache/div[3]/div[1]/div/div[3]/div[2]/div[2]/p[2]/a")).click(); //X-path copied for product
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).click(); //X-path manual add quantity 
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).sendKeys("2");
        driver.findElement(By.xpath("//input[@id=\"add-to-cart-btn\"]")).click();  //add to cart button
        driver.findElement(By.xpath("//*[@id=\"costcoModalText\"]/div[2]/div[2]/a/button")).click(); //view cart button
        String quantityText = driver.findElement(By.xpath("//*[@id=\"items-quantity-title\"]")).getText(); //view Cart page (# Items)
        System.out.println("Item Quantity" + quantityText);  // prints items quantity (# Items)
        assertEquals(" (2 Items)", quantityText);
        Thread.sleep(5000);
    }

    @Test
    public void testViewCart() throws Exception {
        // Add item and view cart after logging in
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(20,250)");
        driver.get("https://www.costco.com/boscia-sake-treatment-water%2c-1.79-fl-oz.product.100534870.html"); //bypass base URL and go to the product
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).click(); //X-path manual add quantity 
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).sendKeys("2");
        driver.findElement(By.xpath("//input[@id=\"add-to-cart-btn\"]")).click();  //add to cart button
        driver.findElement(By.xpath("//*[@id=\"costcoModalText\"]/div[2]/div[2]/a/button")).click(); //view cart button
        driver.findElement(By.xpath("//a[@id=\"header_sign_in\"]")).click(); // Sign In/ Register
        driver.findElement(By.id("logonId")).click(); //email address
        driver.findElement(By.id("logonId")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.id("logonId")).sendKeys(Keys.DELETE);
        driver.findElement(By.id("logonId")).sendKeys("my email");
        driver.findElement(By.id("logonPassword")).click(); //password
        driver.findElement(By.id("logonPassword")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.id("logonPassword")).sendKeys(Keys.DELETE);
        driver.findElement(By.id("logonPassword")).sendKeys("my password");
        driver.findElement(By.id("logonPassword")).submit();
        driver.findElement(By.id("cart-d")).click();
        String quantityText = driver.findElement(By.xpath("//*[@id=\"items-quantity-title\"]")).getText(); //view Cart page (# Items)
        System.out.println("Item Quantity" + quantityText);  // prints items quantity (# Items)
        assertEquals(" (2 Items)", quantityText); //verify items
        // Cart contains item before logging in and contains the item added while logged in.
        Thread.sleep(5000);
    }

    @Test
    public void testShipping() throws Exception {
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(20,250)");
        driver.get("https://www.costco.com/CheckoutCartDisplayView?catalogId=10701&storeId=10301&langId=-1&krypto=m6NuXtnbDg8imf1aXkUwwXuz5Geb9RuxuCGFkf%2BBP5QBlXSMbHHWlVj73Yjo%2BqVmQQ2XIJPDsLx7yxRV2GZ7NCuzJD7UgqYlZwcfXIW7Jak%3D&ddkey=http%3ACheckoutCartView"); //bypass base URL and go to the product
        driver.findElement(By.xpath("//a[@id=\"header_sign_in\"]")).click(); // Sign In/ Register
        driver.findElement(By.id("logonId")).click(); //email address
        driver.findElement(By.id("logonId")).sendKeys(Keys.CONTROL + "a");//email address
        driver.findElement(By.id("logonId")).sendKeys(Keys.DELETE);//email address
        driver.findElement(By.id("logonId")).sendKeys("my email");//email address
        driver.findElement(By.id("logonPassword")).click(); //password
        driver.findElement(By.id("logonPassword")).sendKeys(Keys.CONTROL + "a");//password
        driver.findElement(By.id("logonPassword")).sendKeys(Keys.DELETE);//password
        driver.findElement(By.id("logonPassword")).sendKeys("my password");//password
        driver.findElement(By.id("logonPassword")).submit();
        driver.findElement(By.xpath("//a[@id=\"cart-d\"]")).click(); //click on Cart
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"shopCartCheckoutSubmitButton\"]")).click(); //Click on Checkout
        Thread.sleep(5000);
        String shippingCost = driver.findElement(By.xpath("//h3[@class=\"h8-style-guide summary-right\"]")).getText();
        System.out.println("Shipping Cost" + shippingCost);
        assertEquals("$0.00", shippingCost); //verify free shipping for over $75
        Thread.sleep(5000);
    }

    @Test
    public void testOutOfStock() throws Exception {
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
        driver.get("https://www.costco.com/desks.html"); //Out of Stock office desk
        driver.findElement(By.xpath("//a[@href=\"https://www.costco.com/ryland-66%22-executive-desk.product.100023050.html\"]")).click();
        driver.findElement(By.xpath("//li[@id=\"add-to-cart\"]")).click();
        Thread.sleep(2000);
        String outOfStock = driver.findElement(By.xpath("//li[@id=\"add-to-cart\"]")).getText();
        System.out.println("out of stock " + outOfStock);
        assertEquals("", outOfStock); //verify free shipping for over $75
        Thread.sleep(5000);

    }

    @Test
    public void testLimit() throws Exception {
        // Add item and view cart after logging in
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(20,250)");
        driver.get("https://www.costco.com/boscia-sake-treatment-water%2c-1.79-fl-oz.product.100534870.html"); //bypass base URL and go to the product
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).click(); //X-path manual add quantity 
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@id=\"minQtyText\"]")).sendKeys("6");
        driver.findElement(By.xpath("//input[@id=\"add-to-cart-btn\"]")).click();  //add to cart button
        String limitText = driver.findElement(By.xpath("//p[@class=\"error\"]")).getText(); //error: Item 1405494 has a maximum order quantity of 5
        System.out.println("Error: " + limitText);  // prints items quantity (# Items)
        assertEquals("Item 1405494 has a maximum order quantity of 5", limitText); //verify Limit quantity error messege

        Thread.sleep(5000);
    }
    
    @Test
    public void test44SelectProductupdateQuan() throws InterruptedException {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("search-field")).click();
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys("smoke detectors");
        driver.findElement(By.id("formcatsearch")).submit();
        driver.findElement(By.xpath("//img[@alt='Kidde Battery Operated Smoke Alarm, 4-pack']")).click();
        driver.findElement(By.id("minQtyText")).click();
        driver.findElement(By.id("minQtyText")).clear();
        driver.findElement(By.id("minQtyText")).sendKeys("2");
        driver.findElement(By.id("add-to-cart-btn")).click();
        driver.findElement(By.xpath("//div[@id='costcoModalText']/div[2]/div[2]/a/button")).click();

        Thread.sleep(2000);
        String itemquantext = driver.findElement(By.xpath("//*[@id=\"items-quantity-title\"]")).getText();
        //System.out.println("QUANTITY=" +itemquantext );
        assertEquals(" (2 Items)", itemquantext);
        //driver.findElement(By.linkText("Remove")).click();
    }

    @Test
    public void test45DisplayProductInfo() {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("search-field")).click();
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys("bottled water");
        driver.findElement(By.id("formcatsearch")).submit();
        driver.findElement(By.linkText("Perrier Sparkling Mineral Water, 16.9 fl oz, 24-count")).click();
        driver.findElement(By.linkText("Specifications")).click();

        String iteminfotext = driver.findElement(By.xpath("//*[@id=\"pdp-accordion-collapse-2\"]/div/div/div/div[2]")).getText();
        //System.out.println("QUANTITY=" +itemquantext );
        assertEquals("Perrier", iteminfotext);
    }

    @Test
    public void test46GrocerySearchItemSuccess() {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("search-dropdown-select")).click();
        new Select(driver.findElement(By.id("search-dropdown-select"))).selectByVisibleText("Grocery");
        driver.findElement(By.id("search-dropdown-select")).click();
        driver.findElement(By.id("search-field")).click();
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys("cheerios");
        driver.findElement(By.id("formcatsearch")).submit();

        String foundgrocerytext = driver.findElement(By.xpath("//*[@id=\"rsltCntMsg\"]")).getText();
        assertEquals("We found 3 results for \"cheerios\"", foundgrocerytext);
    }
    
    @Test
    public void test47GrocerySearchItemFail() throws InterruptedException {
        driver.get(baseURL);
        //driver.manage().window().maximize();
        driver.findElement(By.id("search-dropdown-select")).click();
        new Select(driver.findElement(By.id("search-dropdown-select"))).selectByVisibleText("Grocery");
        driver.findElement(By.id("search-dropdown-select")).click();
        driver.findElement(By.id("search-field")).click();
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys("grave markers");
        driver.findElement(By.id("formcatsearch")).submit();
        
        //Thread.sleep(4000);
        String notfoundgrocerytext = driver.findElement(By.className("rule-message")).getText();
        //System.out.println("MSG= "+notfoundgrocerytext);
        assertEquals("We did not find a match for \"grave markers\" in Grocery. Shop related products in other departments:", notfoundgrocerytext);
    }

}
