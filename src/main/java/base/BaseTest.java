package base;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExtentReporterNG;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
public class BaseTest {
    public static ExtentReports extent;
    public static ExtentSparkReporter sparkReporter;
//    public static WebDriver driver;
    private static ThreadLocal<ExtentTest> logger = new ThreadLocal<>();
//    public ExtentTest logger;
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static WebDriver getDriver()
    {
    	return driver.get();
    }
    public static ExtentTest getLogger() {
        return logger.get();
    }
    @BeforeSuite
    public void setupExtent()
    {
    	extent = ExtentReporterNG.getReportObjects();
    }
    @BeforeMethod
    @Parameters({"browser", "runOn"})
    public void setup(String browser, Method testMethod, String runOn) throws Exception {
    	ExtentTest test = extent.createTest(testMethod.getName());
    	logger.set(test);
    	WebDriver localDriver = setupDriver(browser, runOn);
        driver.set(localDriver);
        
        getDriver().manage().window().maximize();
        getDriver().get(utils.Constants.url);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }
    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
        	getLogger().fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
        	getLogger().skip(result.getThrowable());
        } else {
        	getLogger().pass("Test passed");
        }
        getDriver().quit();
        driver.remove();
        logger.remove();
    }
    @AfterSuite
    public void tearDown()
    {
    	extent.flush();
    }
    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
   	{
       	// Take screenshot as BASE64 (works on Grid + Local)
   		String base64 = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BASE64);
   		
   		// Create folder
   		String folderPath = System.getProperty("user.dir")+"//screenshots//";
   		new File(folderPath).mkdirs();
   		
   		// Build file path
   		String filePath = folderPath + testCaseName + "_" + System.currentTimeMillis() + ".png";
   		
   		// Convert base64 → image file
   		byte[] data = java.util.Base64.getDecoder().decode(base64);
   		FileOutputStream fos = new FileOutputStream(filePath);
   		fos.write(data);
   		fos.close();
   		
   		return filePath;	
   	}
    public WebDriver setupDriver(String browser , String runOn) throws Exception {
        System.out.println("runOn: " + runOn);
        WebDriver driver = null;
        
        URL gridUrl = new URI("http://192.168.0.103:4444").toURL(); 
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions chrome = new ChromeOptions();
            HashMap<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("profile.default_content_setting_values.geolocation", 2);
            prefs.put("download.default_directory", "C:\\downloads");
            chrome.setExperimentalOption("prefs", prefs);
            chrome.addArguments("--start-maximized");
            chrome.setAcceptInsecureCerts(true);
            
            if (runOn.equalsIgnoreCase("grid")) {
                driver = new RemoteWebDriver(gridUrl, chrome);
            } else 
            {
                driver = new ChromeDriver(chrome);
            }
        }

        else if(browser.equalsIgnoreCase("firefox"))
        {
        	FirefoxOptions ff = new FirefoxOptions();
        	ff.addPreference("dom.webnotifications.enabled", false);
            ff.addPreference("geo.enabled", false);
            ff.addPreference("browser.download.dir", "C:\\downloads");
            ff.addPreference("browser.download.useDownloadDir", true);
            ff.addArguments("--start-maximized");
            ff.addPreference("browser.download.folderList", 2);
            ff.setAcceptInsecureCerts(true);

            if (runOn.equalsIgnoreCase("grid")) {
                driver = new RemoteWebDriver(gridUrl, ff);
            } else {
                driver = new FirefoxDriver(ff);
            }
        }
        else if (browser.equalsIgnoreCase("edge")) {

            EdgeOptions edge = new EdgeOptions();
            HashMap<String, Object> edgePrefs = new HashMap<>();
            edgePrefs.put("profile.default_content_setting_values.notifications", 2);

            edge.setExperimentalOption("prefs", edgePrefs);
            edge.addArguments("--start-maximized");
            edge.setAcceptInsecureCerts(true);
 
            if (runOn.equalsIgnoreCase("grid")) {
                driver = new RemoteWebDriver(gridUrl, edge);
            } else {
                driver = new EdgeDriver(edge);
            }
        }
        System.out.println("Launching browser: " + browser);
        return driver;
    }
	
}
  
