package Retail.BackOffice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Dashboard_Home {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		String chromedriverpath = "C:\\Users\\Revest\\eclipse-workspace\\chromedriver-win64\\chromedriver.exe";
        
		// Set the path to your ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", chromedriverpath);

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();
       
        // Create a new instance of the HtmlUnitDriver
        WebDriver htmldriver = new HtmlUnitDriver();
        
        // Navigate to the login page
        driver.get("https://dev.revestretail.com/dashboards/analytics/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        
        WebElement username = driver.findElement(By.xpath("//input[@name='userName']"));
        username.sendKeys("ksajja@revestretail.com");

        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        password.sendKeys("Revest@09");

        WebElement checkbox = driver.findElement(By.xpath("//span[text()='Remember Me']"));
        boolean isDisplayed = checkbox.isDisplayed();
        
        if (isDisplayed == true) {
        checkbox.click();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        
        WebElement signin = driver.findElement(By.xpath("//button[text()='Sign in']"));
        signin.click();
        
        // Set up WebDriverWait with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()='Analytics']/..//div[2]")));

        // Take a screenshot after login
        takeScreenshot(driver, "login_page");
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        
        //Navigate to new tab
        htmldriver.switchTo().newWindow(null);
        
        // Navigate to the email inbox using the WebDriver
        htmldriver.get("https://outlook.office365.com/mail/");

        // Log in to the email account using the provided credentials
        // Replace 'Username' and 'password' with your actual credentials
        htmldriver.findElement(By.id("Email")).sendKeys("ksajja@revestretail.com");
        htmldriver.findElement(By.id("next")).click();
        htmldriver.findElement(By.id("Passwd")).sendKeys("Password");
        htmldriver.findElement(By.id("signIn")).click();

        // Wait for the page to load completely
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Read the OTP from the email body
        List<WebElement> otpList = driver.findElements(By.xpath("//td[contains(text(), 'OTP:')]/following-sibling::td[1]"));

        if (!otpList.isEmpty()) {
            String otp = otpList.get(0).getText();
            System.out.println("The OTP is: " + otp);

            // Enter the OTP in the Outlook app
            // You need to handle this part manually as automating the Outlook app is not feasible
        } else {
            System.out.println("OTP not found in the email body.");
        }
        
        // Close the browser
        driver.quit();

    }
    private static void takeScreenshot(WebDriver driver, String fileName) {
        // Convert WebDriver object to TakesScreenshot
        File screenshot = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);

        // Define the destination path for the screenshot
        Path destination = Paths.get("path/to/screenshots/" + fileName + ".png");

        try {
            // Copy the screenshot to the specified path
            Files.copy(screenshot.toPath(), destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
}
