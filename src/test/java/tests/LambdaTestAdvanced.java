package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import base.BaseTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LambdaTestAdvanced extends BaseTest {

	@FindBy(xpath = "//a[text()='Simple Form Demo']")
	public WebElement simpleFormDemo;

	@FindBy(xpath = "//input[@id='user-message']")
	public WebElement messageText;

	@FindBy(xpath = "//button[text()='Get Checked Value']")
	public WebElement checkedBtn;

	@FindBy(xpath = "//p[text()='Welcome to LambdaTest']")
	public WebElement yourMSGTxt;

	@FindBy(xpath = "//a[text()='Drag & Drop Sliders']")
	public WebElement dragAndDrop;

	@FindBy(xpath = "//a[text()='Input Form Submit']")
	public WebElement inputForm;

	@FindBy(xpath = "//button[text()='Submit']")
	public WebElement submitBtn;

	@FindBy(xpath = "//div[@id='slider3']/div/input")
	public WebElement slider;

	@FindBy(xpath = "//input[@id='name']")
	public WebElement name;

	@FindBy(xpath = "//input[@id='inputEmail4']")
	public WebElement email;

	@FindBy(xpath = "//input[@name='password']")
	public WebElement password;

	@FindBy(xpath = "//input[@name='company']")
	public WebElement company;

	@FindBy(xpath = "//input[@name='website']")
	public WebElement website;

	WebDriver driver;
	WebDriverWait wait;
	SoftAssert softAssert = new SoftAssert();

	@Parameters({ "browser", "version", "platform" })
	@BeforeMethod
	public void setUp(String browser, String version, String platform)
			throws MalformedURLException, InterruptedException {

		switch (browser) {

		case "Chrome":
			driver = new ChromeDriver();
			driver.get("https://www.lambdatest.com/selenium-playground");
			driver.manage().window().maximize();
			Thread.sleep(3000);
			break;

		case "MicrosoftEdge":
			System.setProperty("webdriver.edge.driver", "C:\\Users\\ACER\\Desktop\\webdrivers\\msedgedriver.exe");
			driver = new EdgeDriver();
			driver.get("https://www.lambdatest.com/selenium-playground");
			driver.manage().window().maximize();
			Thread.sleep(3000);
			break;

		case "Firefox":
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\ACER\\Desktop\\webdrivers\\geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.setAcceptInsecureCerts(true);
			driver = new FirefoxDriver(options);
			driver.get("https://www.lambdatest.com/selenium-playground");
			driver.manage().window().maximize();
			Thread.sleep(3000);
			break;

		default:
			System.out.println("Enter Valid Browser");

		}

		PageFactory.initElements(driver, this);
	}

	@Test(priority = 1)
	public void testSeleniumPlayGround() throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", simpleFormDemo);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", simpleFormDemo);

		softAssert.assertEquals(driver.getCurrentUrl().contains("simple-form-demo"),
				"simple-form-demo is not available");

		String welecomeMSG = "Welcome to LambdaTest";
		Thread.sleep(3000);
		messageText.sendKeys(welecomeMSG);
		checkedBtn.click();

		softAssert.assertEquals(yourMSGTxt.getText().equals(welecomeMSG), "Test is not mactching");
	}

	@Test(priority = 2)
	public void dragSliderTo95() throws InterruptedException {

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dragAndDrop);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", dragAndDrop);

		Thread.sleep(3000);
		WebElement rangeValue = driver.findElement(By.id("rangeSuccess"));

		slider.click();

		while (!rangeValue.getText().equals("95")) {
			slider.sendKeys(Keys.ARROW_RIGHT);
		}
		softAssert.assertEquals(rangeValue.getText(), "95", "Slider value is not 95!");

	}

	@Test(priority = 3)
	public void inputFormTest() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", inputForm);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", inputForm);
		Thread.sleep(2000);
		submitBtn.click();
		Thread.sleep(2000);
		String validationMsg = driver.findElement(By.name("name")).getAttribute("validationMessage");
		softAssert.assertTrue(validationMsg.contains("Please fill out this field."), "Validation message not shown!");
		name.sendKeys("testName");
		email.sendKeys("abc@xyz.com");
		password.sendKeys("Test123");
		company.sendKeys("Capgemini");
		website.sendKeys("https://abcd.com");
		WebElement countryDropdown = driver.findElement(By.name("country"));

		Select sel = new Select(countryDropdown);
		sel.selectByVisibleText("United States");

		driver.findElement(By.name("city")).sendKeys("New York");
		driver.findElement(By.name("address_line1")).sendKeys("123 Test Street");
		driver.findElement(By.name("address_line2")).sendKeys("Suite 456");
		driver.findElement(By.id("inputState")).sendKeys("NY");
		driver.findElement(By.name("zip")).sendKeys("10001");
		submitBtn.click();

		Thread.sleep(2000);
		WebElement successMSG = driver.findElement(
				By.xpath("//p[contains(text(),'Thanks for contacting us, we will get back to you shortly.')]"));
		softAssert.assertTrue(successMSG.isDisplayed(), "Success message not displayed!");
		softAssert.assertEquals(successMSG.getText(), "Thanks for contacting us, we will get back to you shortly.");

	}
	
	  @AfterMethod public void tearDown() { 
		  driver.quit();
	  
	  }
	 

}
