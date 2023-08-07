package Test_Cases;
import Page_Objects.Add_Items_Cart;
import Page_Objects.Signup_User;
import Page_Objects.Place_Order;
import Utils.CommonUtil;
import Utils.ExcelUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JavaScriptAlerts
{
    WebDriver driver;
    @Test (priority=0)
    public void Launch() throws Exception
    {
        System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
        driver = new ChromeDriver();
    }
    @Test (priority=1)
    public void AcceptAlert()
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//*[@id='content']/div/ul/li[1]/button")).click();
        String Text1=driver.switchTo().alert().getText();
      //  System.out.println("Text is "+a);
        Assertions.assertEquals("I am a JS Alert", Text1);
        driver.switchTo().alert().accept();
    }

    @Test (priority=2)
    public void DismissAlert() throws InterruptedException
    {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='content']/div/ul/li[2]/button")).click();
        String Text2=driver.switchTo().alert().getText();
     //   System.out.println("Text is "+a);
        Assertions.assertEquals("I am a JS Confirm", Text2);
        driver.switchTo().alert().dismiss();
    }

    @Test (priority=3)
    public void EnterTextInPrompt() throws InterruptedException
    {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='content']/div/ul/li[3]/button")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().sendKeys("Surendra Assessment");
        driver.switchTo().alert().accept();
        //   System.out.println("Text is "+a);
       // Assertions.assertEquals("I am a JS Confirm", Text2);
       // driver.switchTo().alert().dismiss();
    }

}
