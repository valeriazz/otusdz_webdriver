import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;

public class KioskTest {
    WebDriver driver;

    private WaitTools waitTools;

    private final Logger logger = LogManager.getLogger(KioskTest.class);

    @BeforeAll
    public static void driverSetup() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void driverStart() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-fullscreen");
        driver = new ChromeDriver(options);
        waitTools = new WaitTools(driver);
        driver.get("https://p.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash" +
                "-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818");
    }

    @AfterEach
    public void driverStop() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void kioskTest() {
        waitTools.waitForCondition(ExpectedConditions.elementToBeClickable(By.xpath("//img[@src='assets/images/p1.jpg']")));
        WebElement pic = driver.findElement(By.xpath("//img[@src='assets/images/p1.jpg']"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", pic);
        WebElement window = driver.findElement(By.xpath("//div[@class='pp_content_container']"));
        waitTools.waitForCondition(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='pp_content_container']")));
        Assertions.assertNotNull(window);
        logger.info("Картинка открылась в модальном окне");
    }
}
