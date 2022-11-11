package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.selenium.Home;
import com.udacity.jwdnd.course1.cloudstorage.selenium.Login;
import com.udacity.jwdnd.course1.cloudstorage.selenium.Signup;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpTest {
    private static WebDriver driver;
    @LocalServerPort
    private Integer port;
    private Signup signup;
    private Login login;
    private Home home;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/signup");
        signup = new Signup(driver);
        login = new Login(driver);
        home = new Home(driver);
    }

    @Test
    public void testHomePageNotAccessible() {
        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = null;
        try {
            homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        } catch (TimeoutException e) {
            //e.printStackTrace();
        }
        assertNull(homeMarker);

        WebDriverWait wait2 = new WebDriverWait(driver, 3);
        WebElement marker2 = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        assertNotNull(marker2);
    }

    @Test
    public void testloginCheck() {
        driver.get("http://localhost:" + port + "/login");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        assertNotNull(homeMarker);
    }

    @Test
    public void testsignupCheck() {
        driver.get("http://localhost:" + port + "/signup");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        assertNotNull(homeMarker);
    }

    @Test
    public void testHomePageIsAccessibleAfterSignup() {

        signup.signUp("Janani", "Bharathi", "jpjanani", "password");


        driver.get("http://localhost:" + port + "/login");
        login.enterUsernameAndPassword("jpjanani", "password");


        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        assertNotNull(homeMarker);


        home.logout();


        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait2 = new WebDriverWait(driver, 3);
        WebElement homeMarker2 = null;
        try {
            homeMarker2 = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        } catch (TimeoutException e) {
            //  e.printStackTrace();
        }
        assertNull(homeMarker2);
    }


}
