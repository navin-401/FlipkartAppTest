import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class FlipkartAppTest {
    public WebDriver driver;
    @BeforeMethod
    public void setUp(){
        //Set the path to the ChromeDriver executable

        System.setProperty("webdriver.chrome.driver" , "D:\\Getting started with Selenium\\chromedriver-win64\\chromedriver.exe");

        // Launch Chrome Browser

        driver = new ChromeDriver();
        //Navigating to Flipkart Application

        driver.get("https://www.flipkart.com");

        //Maximising the window

        driver.manage().window().maximize();
    }
    @AfterMethod
    public void tearDown(){
        //Closing the browser

        driver.quit();
    }
    //HomePage Verification
    @Test(priority = 1)
    public void homePage(){
        String expectedUrl = "https://www.flipkart.com/";
        String currentUrl = driver.getCurrentUrl();

        //Waiting the webdriver to load the webpage
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        //Verifying the Expected Url With Actual Url
        Assert.assertEquals(expectedUrl , currentUrl , "HomePage doesnot matches");
    }

    //Search and Add to Cart Checkout

    @Test(priority = 2)
    public void addToCart(){
        //Finding the search box
        WebElement searchElement = driver.findElement(By.xpath("//div[@class = \"_2SmNnR\"]/input"));
        //Entering the text
        searchElement.sendKeys("Laptop");
        //Clicking the enter button on keyboard
        searchElement.sendKeys(Keys.ENTER);

        //Selecting one item
        WebElement oneSelect = driver.findElement(By.xpath("//div[@class = \"_1YokD2 _3Mn1Gg\"]/div[2]"));
        oneSelect.click();

        //getting current window
        String parentWindowHandle = driver.getWindowHandle();

        //number of open windows tabs in a browser
        Set<String> windowHandlesSet = driver.getWindowHandles();

        //moving to the expcted window , Where wu perform actions
        for(String windowHandle : windowHandlesSet){
            if(!windowHandle.equals(parentWindowHandle)){
                driver.switchTo().window(windowHandle);
                WebElement addToCartBtn = driver.findElement(By.xpath("//button[@class = \"_2KpZ6l _2U9uOA _3v1-ww\"]"));
                addToCartBtn.click();

                String expectedNaviagteUrl = "https://www.flipkart.com/acer-aspire-3-intel-core-i3-12th-gen-1215u-8-gb-512-gb-ssd-windows-11-home-a315-59-thin-light-laptop/p/itmb08df4e787239?pid=COMGRSXP6D9ANEXN&lid=LSTCOMGRSXP6D9ANEXNBYCJYM&marketplace=FLIPKART&q=Laptop&store=6bo%2Fb5g&srno=s_1_1&otracker=search&otracker1=search&fm=organic&iid=en_ehklz1h9277z9eHqlWt0kmY5bdEjXjXH0_dAgcn78KWf8ezKVOJ06A3YPjrzO-1bWvCM1oYOSW552ztn-nIRWPUFjCTyOHoHZs-Z5_PS_w0%3D&ppt=hp&ppn=homepage&ssid=gah5hdmwts0000001709886293781&qH=146bdebb324a64d3";
                String actualNavigateUrl = driver.getCurrentUrl();
                Assert.assertEquals(actualNavigateUrl ,expectedNaviagteUrl  , "Navigated to Cart Url");

                WebElement placeOrderBtn = driver.findElement(By.xpath("//button[@class = \"_2KpZ6l _2ObVJD _3AWRsL\"]"));
                placeOrderBtn.click();
            }
        }

    }

    //User Authentication

    @Test(priority = 3)
    public void Login(){
        WebElement loginBtnEl = driver.findElement(By.xpath("//span[text() = \"Login\"]"));
        loginBtnEl.click();
        WebElement enterMobNumEl = driver.findElement(By.xpath("//span[text() = \"Enter Email/Mobile number\"]"));
        enterMobNumEl.sendKeys("1234567890");
        WebElement requestOtp = driver.findElement(By.xpath("//button[@class = \"_2KpZ6l _2HKlqd _3AWRsL\"]"));
        requestOtp.click();
        WebElement enterOtpEl = driver.findElement(By.xpath("//div[@class = \"HSKgdN\"]"));
        enterOtpEl.sendKeys("123456");
        WebElement verifyBtn = driver.findElement(By.xpath("//button[@class = \"_2KpZ6l _14EHzR _3dESVI\"]"));
        verifyBtn.click();
        WebElement expectedUrl = "";
        WebElement actualUrl = "";
        Assert.assertEquals(expectedUrl , actualUrl , "Logged in Successfully");
    }
}
