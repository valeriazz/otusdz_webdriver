import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tools.WaitTools;


public class FullAuthTest {
    WebDriver driver;
    private WaitTools waitTools;
    private final Logger logger = LogManager.getLogger(FullAuthTest.class);
    private String email = System.getProperty("valeria211@yandex.ru");
    private String password = System.getProperty("password");


    @BeforeAll
    public static void driverSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void driverStart() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        waitTools = new WaitTools(driver);
        driver.get("https://otus.ru");
    }

    @AfterEach
    public void driverStop() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void fullAuthTest() {
        String emailLocator = "//div[./input[@name='email']]";
        String passwordLocator = "//div[./input[@type='password']]";
        String inputEmailLocator = "//input[@name='email']";
        String inputPasswordLocator = "//input[@type='password']";
        waitTools.waitForCondition(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()=\"Войти\"]")));
        WebElement buttonEnter = driver.findElement(By.xpath("//button[text()=\"Войти\"]"));
        buttonEnter.click();
        driver.findElement(By.xpath(emailLocator)).click();
        driver.findElement(By.xpath(inputEmailLocator)).sendKeys("valeria211@yandex.ru");
        driver.findElement(By.xpath(passwordLocator)).click();
        driver.findElement(By.xpath(inputPasswordLocator)).sendKeys("123");
        driver.findElement(By.cssSelector("#__PORTAL__ button")).click();
        Assertions.assertTrue(waitTools.waitForCondition(ExpectedConditions.
                invisibilityOf(buttonEnter)));
        logger.info("Авторизация прошла успешно");
        logger.info(driver.manage().getCookies());
    }
}
