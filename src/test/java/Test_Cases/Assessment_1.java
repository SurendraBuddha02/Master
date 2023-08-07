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
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Assessment_1
{
    WebDriver driver;
    String [][] Excel_CIB_value;
    String [][] Excel_Cart_Details;
    String Username;
    String alertText;
    CommonUtil CommonUtil;
    ExcelUtils ExcelUtils;
    Signup_User Signup_User;
    Place_Order Place_Order;
    Add_Items_Cart Add_Items_Cart;
    static ExtentTest test;
    static ExtentReports report;


    @Parameters("browser")
    @Test (priority=0)
    public void Launch(String browser) throws Exception
    {
        if(browser.equalsIgnoreCase("firefox"))
        {
            //create firefox instance
            System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        }
            //Check if parameter passed as 'chrome'
        else if(browser.equalsIgnoreCase("Chrome"))
        {
            System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver.exe");
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        ExcelUtils=new ExcelUtils();
        Signup_User=new Signup_User();
        CommonUtil=new CommonUtil(driver);
        Add_Items_Cart =new Add_Items_Cart();
        Place_Order=new Place_Order();
        String timestamp = new SimpleDateFormat("YYY.MM.dd.hh.mm.ss").format(new Date());
        report = new ExtentReports(System.getProperty("user.dir") + "\\Reports\\Assessment1_" + timestamp + ".html", false);
      //  System.out.println("report:" + System.getProperty("user.dir") + "\\Reports\\Assessment1_" + timestamp + ".html");

        //Reading Values from Excel
        Excel_CIB_value = ExcelUtils.readExcelDataFileToArray("src/test/resources/CIB_Assessment.xlsx", "Login");
        Excel_Cart_Details = ExcelUtils.readExcelDataFileToArray("src/test/resources/CIB_Assessment.xlsx", "Cart Details");
        driver.get(Excel_CIB_value[1][0]);
    }

    @Test (priority=1)
    public void Signup() throws Exception
    {
        Username=RandomStringUtils.randomAlphanumeric(10);
       // System.out.println("Name:"+RandomStringUtils.randomAlphanumeric(10));
        CommonUtil.clickOnElement(Signup_User.Signup);
        CommonUtil.typeOnElement(Signup_User.Signup_Username,Username);
      //  System.out.println("Password:"+Excel_CIB_value[1][1]);
        Thread.sleep(1000);
        CommonUtil.typeOnElement(Signup_User.Signup_Password,Excel_CIB_value[1][1]);
        test=report.startTest("Assessment_1");
        test.log(LogStatus.PASS,"Sign Up Page",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        CommonUtil.clickOnElement(Signup_User.Signup_Btn);
        Thread.sleep(2000);

        //Verifying Alert Text
        try
        {
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
            System.out.println("Alert Text: " + alertText);
            alert.accept();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (alertText.equalsIgnoreCase("Sign up successful."))
        {
            test.log(LogStatus.PASS,"Signup Successfull",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }
        else
        {
            test.log(LogStatus.FAIL,"Signup not Successfull",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }
    }

    @Test(priority=2)
    public void Add_Items_to_Cart() throws Exception
    {
        CommonUtil.clickOnElement(Add_Items_Cart.Log_In);
        CommonUtil.typeOnElement(Add_Items_Cart.Login_Username,Username);
        CommonUtil.typeOnElement(Add_Items_Cart.Login_Password,Excel_CIB_value[1][1]);
        CommonUtil.clickOnElement(Add_Items_Cart.Login_Btn);
        Thread.sleep(2000);
        CommonUtil.clickOnElement(Add_Items_Cart.Monitors);
        CommonUtil.clickOnElement(Add_Items_Cart.Asus_Full_HD);
        CommonUtil.clickOnElement(Add_Items_Cart.Add_to_cart);
        Thread.sleep(1000);

        //Verifying Alert Text
        try
        {
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
            System.out.println("Alert Text: " + alertText);
            alert.accept();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (alertText.equalsIgnoreCase("Product added."))
        {
            test.log(LogStatus.PASS,"Asus Full HD Successfully added to cart",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }
        else
        {
            test.log(LogStatus.FAIL,"Asus Full HD not Successfully added to cart",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }

        //Retrieving Asus FullHD Monitor Price
        String Price=CommonUtil.getText(Add_Items_Cart.Asus_Full_HD_Price);
        String[] arrSplit = Price.split(" ");
       // System.out.println("Price:"+arrSplit[0]);
        String[] Asus_Price=arrSplit[0].split("\\$");
        int AsusPrice = Integer.parseInt(Asus_Price[1]);
        System.out.println("AsusPrice:"+AsusPrice);

        CommonUtil.clickOnElement(Add_Items_Cart.Home);
        CommonUtil.clickOnElement(Add_Items_Cart.Phones);
        CommonUtil.clickOnElement(Add_Items_Cart.Nexus_6);
        CommonUtil.clickOnElement(Add_Items_Cart.Add_to_cart);
        Thread.sleep(1000);
        //Verifying Alert Text
        try
        {
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
            System.out.println("Alert Text: " + alertText);
            alert.accept();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (alertText.equalsIgnoreCase("Product added."))
        {
            test.log(LogStatus.PASS,"Nexus 6 (1st Item) Successfully added to cart",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }
        else
        {
            test.log(LogStatus.FAIL,"Nexus 6   not Successfully added to cart",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }

        //Adding 2nd Item for Nexus 6
        CommonUtil.clickOnElement(Add_Items_Cart.Add_to_cart);
        Thread.sleep(1000);
        //Verifying Alert Text
        try
        {
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
            System.out.println("Alert Text: " + alertText);
            alert.accept();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if (alertText.equalsIgnoreCase("Product added."))
        {
            test.log(LogStatus.PASS,"Nexus 6 (2nd Item) Successfully added to cart",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }
        else
        {
            test.log(LogStatus.FAIL,"Nexus 6  not Successfully added to cart",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }

        //Retrieving Nexus 6 Price
        String Nexus6_price=CommonUtil.getText(Add_Items_Cart.Asus_Full_HD_Price);
        String[] Nexus6 = Nexus6_price.split(" ");
        //System.out.println("Price:"+Nexus6[0]);
        String[] Nexus6price=Nexus6[0].split("\\$");
        int Nexus_Price = Integer.parseInt(Nexus6price[1]);
      //  System.out.println("Nexus6price:"+Nexus_Price);

        //Calculating Total Price
        int Expected_Total_Price=AsusPrice+Nexus_Price*2;
        System.out.println("Expected_Total_Price:"+Expected_Total_Price);

        CommonUtil.clickOnElement(Add_Items_Cart.Cart);
        Thread.sleep(2000);
        String Actual_Total_price=CommonUtil.getText(Add_Items_Cart.Total_Price);
        int Act_Total_Price=Integer.parseInt(Actual_Total_price);
        System.out.println("Actual_Total_Price:"+Act_Total_Price);

        if (Expected_Total_Price==Act_Total_Price)
        {
            test.log(LogStatus.PASS,"Total Price of all items calculated successfully",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }
        else
        {
            test.log(LogStatus.FAIL,"Total Price of all items not calculated successfully",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));
        }


    }

    @Test(priority=2)
    public void Place_order() throws Exception
    {
        CommonUtil.clickOnElement(Place_Order.Place_Order);
        CommonUtil.typeOnElement(Place_Order.name,Excel_Cart_Details[1][0]);
        CommonUtil.typeOnElement(Place_Order.Country,Excel_Cart_Details[1][1]);
        CommonUtil.typeOnElement(Place_Order.city,Excel_Cart_Details[1][2]);
        CommonUtil.typeOnElement(Place_Order.card,Excel_Cart_Details[1][3]);
        CommonUtil.typeOnElement(Place_Order.month,Excel_Cart_Details[1][4]);
        CommonUtil.typeOnElement(Place_Order.year,Excel_Cart_Details[1][5]);
        CommonUtil.clickOnElement(Place_Order.Purchase);
        Thread.sleep(1000);
        test.log(LogStatus.PASS,"Order ID Created Successfully",test.addBase64ScreenShot(CommonUtil.captureScreenshot("TestCase1")));

    }


    @AfterTest
    public void Aftertest()
    {
        report.endTest(test);
        report.flush();
    }

}


