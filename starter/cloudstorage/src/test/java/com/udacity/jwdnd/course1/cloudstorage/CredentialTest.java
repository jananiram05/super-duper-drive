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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {
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
    public void testCreate() throws InterruptedException {

        prepareSignUpWithHome("createTest");

        List<Home.TestCredential> credentialsToBeSaved = new ArrayList<>();
        credentialsToBeSaved.add(new Home.TestCredential("", "http://google.com", "user1", "pass1"));
        credentialsToBeSaved.add(new Home.TestCredential("", "http://google1.com", "user2", "pass2"));
        credentialsToBeSaved.add(new Home.TestCredential("", "http://yahoo.com", "user3", "pass3"));
        credentialsToBeSaved.add(new Home.TestCredential("", "http://linkedin.com", "user4", "pass4"));

        home.enterCredentials(credentialsToBeSaved);

        List<Home.TestCredential> pageCredentials = home.getPageCredentials();

        for (int i = 0; i < credentialsToBeSaved.size(); i++) {
            String id = pageCredentials.get(i).getId();
            Home.TestCredential savedCredential = credentialsToBeSaved.get(i);
            Home.TestCredential pageCredential = pageCredentials.get(i);
            assertEquals(savedCredential.getUrl(), pageCredential.getUrl());
            assertEquals(savedCredential.getUsername(), pageCredential.getUsername());


            assertNotEquals(savedCredential.getPassword(), pageCredential.getPassword());
        }
    }

    @Test
    public void testEdit() throws InterruptedException {

        prepareSignUpWithHome("EditTest");

        List<Home.TestCredential> credentialsToBeSaved = new ArrayList<>();
        credentialsToBeSaved.add(new Home.TestCredential("", "http://google.com", "user1", "pass1"));
        credentialsToBeSaved.add(new Home.TestCredential("", "http://google1.com", "user2", "pass2"));
        credentialsToBeSaved.add(new Home.TestCredential("", "http://yahoo.com", "user3", "pass3"));
        credentialsToBeSaved.add(new Home.TestCredential("", "http://linkedin.com", "user4", "pass4"));

        home.enterCredentials(credentialsToBeSaved);

        List<Home.TestCredential> pageCredentials = home.getPageCredentials();
        List<Home.TestCredential> credentialsEdit = new ArrayList<>();
        for (int i = 0; i < pageCredentials.size(); i++) {
            String showedPassword = home.getShowedPasswordForCredentialId(pageCredentials.get(i).getId());
            assertEquals(credentialsToBeSaved.get(i).getPassword(), showedPassword);
        }

        for (int i = 0; i < pageCredentials.size(); i++) {
            Home.TestCredential pageCredential = pageCredentials.get(i);
            credentialsEdit.add(new Home.TestCredential(pageCredential.getId(), "New url " + i, "new username " + i, "new password " + i));
        }

        home.editCredentials(credentialsEdit);

        List<Home.TestCredential> pageCredentialsAfterEdit = home.getPageCredentials();
        for (int i = 0; i < pageCredentialsAfterEdit.size(); i++) {
            Home.TestCredential pageCredential = pageCredentialsAfterEdit.get(i);
            String showedPassword = home.getShowedPasswordForCredentialId(pageCredential.getId());
            assertEquals("new password " + i, showedPassword);
            assertEquals("new username " + i, pageCredential.getUsername());
            assertEquals("New url " + i, pageCredential.getUrl());
        }
    }

    @Test
    public void testDelete() throws InterruptedException {

        prepareSignUpWithHome("deleteTest");

        List<Home.TestCredential> credentialsToBeSaved = new ArrayList<>();
        credentialsToBeSaved.add(new Home.TestCredential("", "http://google.com", "user1", "pass1"));
        credentialsToBeSaved.add(new Home.TestCredential("", "http://google1.com", "user2", "pass2"));
        credentialsToBeSaved.add(new Home.TestCredential("", "http://yahoo.com", "user3", "pass3"));
        credentialsToBeSaved.add(new Home.TestCredential("", "http://linkedin.com", "user4", "pass4"));

        home.enterCredentials(credentialsToBeSaved);

        List<Home.TestCredential> pageCredentials = home.getPageCredentials();
        assertEquals(credentialsToBeSaved.size(), pageCredentials.size());

        home.deleteCredentials(pageCredentials);

        List<Home.TestCredential> pageCredentialsAfterDelete = home.getPageCredentials();
        assertEquals(0, pageCredentialsAfterDelete.size());
    }

    private void prepareSignUpWithHome(String username) {
        signup.signUp("Test", "test", username, "123456");


        driver.get("http://localhost:" + port + "/login");
        login.enterUsernameAndPassword(username, "123456");


        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        assertNotNull(homeMarker);
    }
}
