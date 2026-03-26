import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;

public class FlipkartTest {

    public static void main(String[] args) throws Exception {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 1️⃣ Open Flipkart
        driver.get("https://www.flipkart.com");

        // 2️⃣ Close login popup
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='✕']"))).click();
        } catch (Exception e) {
            System.out.println("Login popup not present");
        }

        // 3️⃣ Search Bluetooth Speakers
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        search.sendKeys("Bluetooth Speakers");
        search.sendKeys(Keys.ENTER);

        // 4️⃣ Open first product
        WebElement firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(@href,'/p/')])[1]")));
        firstProduct.click();

        // 5️⃣ Switch to new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        Thread.sleep(4000);

        // 6️⃣ Click Add to Cart
        try {
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(.,'Add to cart') or contains(.,'ADD TO CART') or contains(.,'Buy Now')]")));
            addBtn.click();
            System.out.println("Clicked Add to Cart / Buy Now");
        } catch (Exception e) {
            System.out.println("Product unavailable — could not be added to cart.");
        }

        Thread.sleep(4000);

        // 7️⃣ Take Screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("cart_result.png"));
        System.out.println("Screenshot saved");

        // 8️⃣ CLOSE BROWSER (important for assignment)
        driver.quit();
        System.out.println("Browser closed successfully");
    }
}