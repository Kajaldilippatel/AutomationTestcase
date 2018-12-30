import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AutomationTestingProgram {
    protected static WebDriver driver;
    @BeforeTest
    public void openBrowser(){
        //Creating web driver object and open site
        System.setProperty("webdriver.chrome.driver","src\\main\\java\\chromedriver.exe");
        //Create object of chormeDriver
        driver =new ChromeDriver();
        //Give implicityWait
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        //open website in fullscreen
        driver.manage().window().fullscreen();
        //open Website
        driver.get("https://demo.nopcommerce.com/");
    }
  @AfterTest
   public void closeBrowser(){
        //close website
        driver.quit();
    }
    @Test
    public void UserShouldBeAbleToRegisterSuccessfully(){
        //Expected Result
        String expectedRegisterSuccessMessage="Your registration completed";
        //Click on Register link
        clickElement(By.linkText("Register"));
        //Select gender
        clickElement(By.id("gender-female"));
        //Enter Firstname
        enterText(By.id("FirstName"),"FirstNameTest");
        //Enter Lastname
        enterText(By.id("LastName"),"LastNameTest");
        //Select date of birth
        Select dateday= new Select(driver.findElement(By.name("DateOfBirthDay")));
        dateday.selectByIndex(3);
        //Select date of month
        Select month= new Select(driver.findElement(By.name("DateOfBirthMonth")));
        month.selectByVisibleText("May");
        //Select date of Year
        Select year= new Select(driver.findElement(By.name("DateOfBirthYear")));
        year.selectByValue("1920");
        //date function use to enter unique email id every time
        DateFormat dateFormat=new SimpleDateFormat("MMddyyyyHHmmss");
        Date date=new Date();
        String date1=dateFormat.format(date);
        //Enter Email
        enterText(By.id("Email"),"testing+"+date1+"@test.com");
        //Enter Company name
        enterText(By.id("Company"),"TestingCompany");
        //Enter Newsletter option
        driver.findElement(By.id("Newsletter")).isSelected();
        //Enter Password
        enterText(By.id("Password"),"testing");
        //Enter ConfirmPassword
        enterText(By.id("ConfirmPassword"),"testing");
        //Click Register button
        clickElement(By.id("register-button"));

        String actualRegisterSuccessMessage=driver.findElement(By.xpath("//div[@class='result']")).getText();
        //assert use to compare expected and actual result are same or not
         Assert.assertEquals(expectedRegisterSuccessMessage,actualRegisterSuccessMessage,"Registration Fail");

        //Click logout button
        clickElement(By.linkText("Log out"));
    }
    @Test
    public void RegisteredUserShouldBeAbleToSendEmailWithProductSuccessfully(){
        String expectedEmailSuccessMessage="Your message has been sent.";
        //click on login button
        clickElement(By.xpath("//a[@href='/login']"));
        //enter email id
        enterText(By.id("Email"),"test123456test@gmail.com");
        //enter password
        enterText(By.id("Password"),"123456");
        //click login button
        clickElement(By.xpath("//input[@class='button-1 login-button']"));
        //select product to email a friend
        clickElement(By.xpath("//div[@class='product-item'][@data-productid='4']/div/a/img"));
        //click on email a friend
        clickElement(By.xpath("//div[@class='email-a-friend']/input"));
        //enter friend email address
        enterText(By.xpath("//input[@class='friend-email']"),"Abcd@gmail.com");
        //enter message
        enterText(By.xpath("//textarea[@class='your-email']"),"Hi");
        //click on send email button
        clickElement(By.xpath("//input[@name='send-email']"));

        String actualEmailSuccessMessage=driver.findElement(By.xpath("//div[@class='page email-a-friend-page']/div/div[2]")).getText();
        Assert.assertEquals(expectedEmailSuccessMessage,actualEmailSuccessMessage,"Registered user should be able to send email with product successfully test case fail");
        //click on logout button
        clickElement(By.linkText("Log out"));
    }
    @Test
    public void UnRegisteredUserShouldNOTBeAbleToSendEmail(){
        String expectedNotEmailSuccessMessage="Only registered customers can use email a friend feature";
        //Select Product
        clickElement(By.xpath("//div[@class='product-item'][@data-productid='4']/div/a/img"));
        //click email a friend link
        clickElement(By.xpath("//div[@class='email-a-friend']/input"));
        //enter friend email id
        enterText(By.xpath("//input[@class='friend-email']"),"Abcd@gmail.com");
        //enter your email id
        enterText(By.xpath("//input[@class='your-email']"),"abc1@gmail.com");
        //enter text
        enterText(By.xpath("//textarea[@class='your-email']"),"Hi");
        //click send email button
        clickElement(By.xpath("//input[@name='send-email']"));

        String actualNotEmailSuccessMessage=driver.findElement(By.xpath("//div[@class='message-error validation-summary-errors']/ul/li")).getText();
        Assert.assertEquals(expectedNotEmailSuccessMessage,actualNotEmailSuccessMessage,"Un-registered use should NOT be able to send email test case fail");

    }
    @Test
    public void userNeedToAcceptTermsAnsConditionAsPerBelowScreen(){
        String expectedConditionMessage="Please accept the terms of service before the next step.";
        //click on jewelry product
        clickElement(By.xpath("//ul[@class='top-menu']/li[6]/a"));
        //select product and click add to cart
       clickElement(By.xpath("//div[@data-productid='41']/div[2]/div[3]/div/input[1]"));
       //click top up menu shopping cart
        clickElement(By.xpath("//div[@id='bar-notification']/p/a"));
        //click Checkout button
        clickElement(By.id("checkout"));
        String actualConditionMeassage=driver.findElement(By.xpath("//div[@id=\"terms-of-service-warning-box\"]/p")).getText();
        Assert.assertEquals(expectedConditionMessage,actualConditionMeassage,"User Need to Accept Term and condtion test case fail");
        //close term and condition top up
        clickElement(By.xpath("//button[@class='ui-button ui-corner-all ui-widget ui-button-icon-only ui-dialog-titlebar-close']"));
        //click term and condition
        clickElement(By.id("termsofservice"));
    }
    @Test
    public void registeredUserShouldBeAbleToBuyAnySingleProductSuccessfully()
    {
        String expectedBuyProductSuccessfullyMessage="Your order has been successfully processed!";
        //click on login button
        clickElement(By.xpath("//a[@href='/login']"));
        //enter email id
        enterText(By.id("Email"),"test123456test@gmail.com");
        //enter password
        enterText(By.id("Password"),"123456");
        //click login button
        clickElement(By.xpath("//input[@class='button-1 login-button']"));
        clickElement(By.xpath("//ul[@class='top-menu']/li[6]/a"));
        //select product and click add to cart
        clickElement(By.xpath("//div[@data-productid='41']/div[2]/div[3]/div/input[1]"));
        //click top up menu shopping cart
        clickElement(By.xpath("//div[@id='bar-notification']/p/a"));
        //click term and condition
        clickElement(By.id("termsofservice"));
        //click Checkout button
        clickElement(By.id("checkout"));

        //select country
            Select country = new Select(driver.findElement(By.id("BillingNewAddress_CountryId")));
            country.selectByValue("1");
            //select state
            Select state = new Select(driver.findElement(By.id("BillingNewAddress_StateProvinceId")));
            state.selectByValue("41");
            //enter city
            enterText(By.id("BillingNewAddress_City"), "Testing");
            //Enter address 1
            enterText(By.id("BillingNewAddress_Address1"), "Testing Street");
            //enter zip code
            enterText(By.id("BillingNewAddress_ZipPostalCode"), "Te00st");
            //enter phone no
            enterText(By.id("BillingNewAddress_PhoneNumber"), "123456789");
            //click continue button
            clickElement(By.xpath("//div[@id='billing-buttons-container']/input"));
            //click continue
        clickElement(By.xpath("//div[@id='shipping-method-buttons-container']/input"));
        //select credit card button
       clickElement(By.id("paymentmethod_1"));
       //click continue button
        clickElement(By.xpath(("//div[@id='payment-method-buttons-container']/input")));
        //enter card Holdername
        enterText(By.id("CardholderName"),"Tester");
        //enter card number
        enterText(By.id("CardNumber"),"4111 1111 1111 1111");
        //Select expire month   //select[@id='ExpireMonth']
        Select expmonth= new Select(driver.findElement(By.id("ExpireMonth")));
        expmonth.selectByValue("2");
        //select expire year
        Select expyear= new Select(driver.findElement(By.id("ExpireYear")));
        expyear.selectByValue("2020");
        //entre card code
        enterText(By.id("CardCode"),"737");
        //click continue
        clickElement(By.xpath("//div[@id='payment-info-buttons-container']/input"));
        //confirm Message
        clickElement(By.xpath("//div[@id='confirm-order-buttons-container']/input"));
        String actualBuyProductSuccessfullyMessage=driver.findElement(By.xpath("//div[@class='section order-completed']/div")).getText();
        //click continue
        clickElement(By.xpath("//div[@class='section order-completed']/div[3]/input"));
        //click on logout button
        clickElement(By.linkText("Log out"));
        Assert.assertEquals(expectedBuyProductSuccessfullyMessage,actualBuyProductSuccessfullyMessage,"Product buy Successfully test case fail");

    }
    @Test
    public  void userShouldAbleToSortByPriceHighToLow()
    {
        //click on books
        clickElement(By.xpath("//ul[@class='top-menu']/li[5]/a"));
        //click on sort by menu
        clickElement(By.xpath("//div[@class='product-sorting']/select"));
        //select high to low option
        Select hightolow=new Select(driver.findElement(By.id("products-orderby")));
        hightolow.selectByVisibleText("Price: High to Low");
        //compare price
        String high=driver.findElement(By.xpath("//div[@data-productid='38']/div/div/div[@class='prices']/span[2]")).getText();
    }

    public void clickElement(By by){
        driver.findElement(by).click();
    }
    public void enterText(By by,String text){
        driver.findElement(by).sendKeys(text);
    }



}
