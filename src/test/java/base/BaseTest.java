package base;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;



public class BaseTest {
	
	public WebDriver driver;
	public ExtentReports extent;
	public ExtentTest test;
	public String sessionId;

	@Parameters({ "browser", "version", "platform" })
	@BeforeMethod
	public void setupDriver(String browser, String version, String platform) throws Exception {

		try {
			String username = "rahulbr";
			String accessKey = "LT_mBGxir3k01R2Bu2Um8u5DhZPVwfOQqLSzWKr5zL7KvVvpfW";
			MutableCapabilities capabilities = new MutableCapabilities();
			capabilities.setCapability("browserName", browser);
			capabilities.setCapability("browserVersion", version);

			Map<String, Object> ltOptions = new HashMap<>();
			ltOptions.put("username", username);
			ltOptions.put("accessKey", accessKey);
			ltOptions.put("platformName", platform);
			ltOptions.put("project", "LambdaTest-Certifications");
			ltOptions.put("build", "Parallel Test Build");
			ltOptions.put("name", "LambdaTest Parallel Test");

			ltOptions.put("w3c", true);
			ltOptions.put("video", true);
			ltOptions.put("network", true);

			capabilities.setCapability("LT:Options", ltOptions);

			String gridURL = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";
			WebDriver driver = new RemoteWebDriver(new URL(gridURL), capabilities);
			sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
			System.out.println("Session Id is: " + sessionId);

		} catch (Exception e) {
			System.out.println("❌ Diagnostic failed: " + e.getMessage());
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void tearDownDriver() {
		if (driver != null) {
			driver.quit();
		}
	}

	public void captureScreenshot(String testName) {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src,
					new File("C:\\Users\\ACER\\eclipse-workspace\\SeleniumLambda\\screenshots\\" + testName + ".png"));
			test.addScreenCaptureFromPath(
					"C:\\Users\\ACER\\eclipse-workspace\\SeleniumLambda\\screenshots\\" + testName + ".png");
		} catch (IOException e) {
			test.warning("Screenshot capture failed: " + e.getMessage());
		}
	}

}
