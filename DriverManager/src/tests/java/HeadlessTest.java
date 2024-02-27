import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;


public class HeadlessTest {
    WebDriver driver;

    private WaitTools waitTools;

    private final Logger logger = LogManager.getLogger(HeadlessTest.class);

    @BeforeAll
    public static void driverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void driverStart() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        driver = new ChromeDriver(options);
        waitTools = new WaitTools(driver);
        driver.get("https://duckduckgo.com");
    }

    @AfterEach
    public void driverStop() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void headlessTest() {
        waitTools.waitForCondition(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='searchbox_input']")));
        driver.findElement(By.xpath("//*[@id='searchbox_input']"))
                .sendKeys("ОТУС" + Keys.ENTER);
        String expectedText = "Онлайн‑курсы для профессионалов, дистанционное обучение";
        WebElement firstResultText = driver.findElement(By.xpath("//span[text()='Онлайн‑курсы для профессионалов, " +
                "дистанционное обучение современным ...']"));
        String actualText = firstResultText.getText();
        int startNum = 0;
        int endNum = 55;
        /*оставить только часть текста от startNum до endNum*/
        actualText = actualText.substring(startNum, endNum);
        Assertions.assertEquals(expectedText, actualText);
        logger.info("Первый результат в поисковике соответствует ожидаемому");
    }
}
