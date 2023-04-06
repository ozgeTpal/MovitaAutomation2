package Test.Adem.stepdefs;

import Locators.Locator;
import Utilities.Browsers;
import Utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import readers.property.PropertyReader;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BaseMovita implements Locator {
    private  WebDriver driver;
    private  WebDriverWait wait;

    /*
    @BeforeTest
    @Parameters("browser")
    public void beforeTest(@Optional("CHROME") String browser) {
        driver = Driver.getDriver(Browsers.valueOf(browser));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
     */

    {
        driver = Driver.getDriver(Browsers.EDGE);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterTest
    public void afterTest() {
        Driver.quitDriver();
    }



   /*   public BaseMovita() {
          driver=Driver.getDriver();
          wait=new WebDriverWait(driver,Duration.ofSeconds(10));
      }*/

    /*{
        driver=Driver.getDriver();
        wait=new WebDriverWait(driver,Duration.ofSeconds(10));
    }*/


    public void open() {
        driver.get(PropertyReader.read().get("url"));
    }


    public void visible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void visible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void assertElement(By locator, String str) {

        WebElement element = driver.findElement(locator);
        String text = element.getText();
        System.out.println(text);
        //bekle(1000);
        Assert.assertEquals(text, str);


    }

    public void bekle(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void getScreenShot(String name) {

        String isim = "screenShots/" + name + " " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) + ".png";

        TakesScreenshot takesScreenshot = ((TakesScreenshot) driver);

        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File target = new File(isim);

        try {
            FileUtils.copyFile(source, target);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void hoverOver(WebElement element,String text){
        new Actions(driver)
                .moveToElement(element)
                .click(homePageMenu(text))
                .build()
                .perform();
    }
    public void hoverAll(By locator){
        List<WebElement> list=driver.findElements(locator);

        for (WebElement element : list) {
            new Actions(driver)
                    .moveToElement(element)
                    .build()
                    .perform();
        }
    }


    @Override
    public WebElement homePageMenu(String text) {
        WebElement element = driver.findElement(By.xpath("//ul[@class='menu-container']//div[text()='" + text + "']"));

        return element;

    }

    public void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        click(element);
    }


    /** Bu metot element e sırasıyla Selenium, Actions ve JS ile click etmeyi dener.
     *
     * @param element WebElement
     */
    public void click(WebElement element) {// totalde 100 ms aralıklarla 10 sn tıklamayı dener, bu metot çalışır.
        wait.until(driver1 -> {// Aslında yukarıda wait e verilen driverla aynıdır. Lambda metodu kullandık. Istersek
            // driver1 yerine "e" ya da istenen değişken adı yazılabilir. list.forEach(e-> sout(e.getText)) kullanımı gibi.
            // Lambda da -> { } kullanılırsa bir değer return etmek zorundadır.
            try {
                element.click();// önce elemente selenium ile click etmeyi dener.
                return true;
            } catch (Exception e) {
                try {// selenium tıklayamazsa Actions Class tan actions la click deneyelim.
                    new Actions(driver1).moveToElement(element).click().perform();
                    return true;
                } catch (Exception e2) {
                    try {// actions da tıklayamazsa en son JS ile click deneyelim.
                        ((JavascriptExecutor) driver1).executeScript("arguments[0].click();", element);
                        return true;
                    } catch (Exception e3) {
                        return false;
                    }
                }

            }
        });

    }

    public void sendKeys(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        sendKeys(element, text);
    }


    /**
     * Bu metot element e sırasıyla Selenium, Actions ve JS ile sendKeys etmeyi dener.
     * @param element WebElement
     * @param text String
     */
    public void sendKeys(WebElement element, String text) {
        wait.until(driver1 -> {// Aslında yukarıda wait e verilen driverla aynıdır. Lambda metodu kullandık.
            try {
                element.clear();
                element.sendKeys(text);
                return true;
            } catch (Exception e) {
                try {// selenium tıklayamazsa action la deneyelim.
                    element.clear();
                    new Actions(driver1).moveToElement(element).sendKeys(text).perform();
                    return true;
                } catch (Exception e2) {
                    try {// action da tıklayamazsa JS ile click deneyelim.
                        element.clear();
                        ((JavascriptExecutor) driver1).executeScript("arguments[0].value=" + text, element);
                        return true;
                    } catch (Exception e3) {
                        return false;
                    }
                }

            }
        });
    }





}
