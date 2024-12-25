
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.time.LocalTime;
import java.util.List;

public class SeleniumAutomation {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        WebDriver driver = new ChromeDriver();

        try {
            // Part 1: Find element by ID
            driver.get("https://example.com"); // Replace with your URL
            WebElement elementById = driver.findElement(By.id("element-id"));
            System.out.println("Found element: " + elementById.getText());

            // Part 2: Automate searching and filtering
            LocalTime currentTime = LocalTime.now();
            if (currentTime.isAfter(LocalTime.of(15, 0)) && currentTime.isBefore(LocalTime.of(18, 0))) {
                driver.get("https://example.com/products"); // Replace with your URL

                // Search for a product
                WebElement searchBox = driver.findElement(By.id("search-box"));
                searchBox.sendKeys("Product");
                searchBox.submit();

                // Apply brand filter
                List<WebElement> brands = driver.findElements(By.cssSelector(".brand-filter"));
                for (WebElement brand : brands) {
                    if (brand.getText().startsWith("C")) {
                        brand.click();
                        break;
                    }
                }

                // Apply price range filter (greater than 2000)
                WebElement minPrice = driver.findElement(By.id("min-price"));
                minPrice.sendKeys("2000");

                WebElement maxPrice = driver.findElement(By.id("max-price"));
                maxPrice.sendKeys("5000");
                maxPrice.submit();

                // Apply customer ratings filter (above 4)
                Select ratingFilter = new Select(driver.findElement(By.id("rating-filter")));
                ratingFilter.selectByValue("4");
            }

            // Part 3: Add products to cart and verify total
            if (currentTime.isAfter(LocalTime.of(18, 0)) && currentTime.isBefore(LocalTime.of(19, 0))) {
                driver.get("https://example.com/products");

                // Add multiple products to cart
                List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".add-to-cart"));
                for (WebElement button : addToCartButtons) {
                    button.click();
                }

                // Verify total price
                driver.get("https://example.com/cart"); // Replace with your cart URL
                WebElement totalPriceElement = driver.findElement(By.id("total-price"));
                int totalPrice = Integer.parseInt(totalPriceElement.getText().replace("\u20B9", ""));
                if (totalPrice > 2000) {
                    System.out.println("Total price is valid: " + totalPrice);
                } else {
                    System.out.println("Total price is invalid: " + totalPrice);
                }

                // Verify username
                String username = "testuser10";
                if (username.matches("^[a-zA-Z0-9]{10}$")) {
                    System.out.println("Username is valid.");
                } else {
                    System.out.println("Username is invalid.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
