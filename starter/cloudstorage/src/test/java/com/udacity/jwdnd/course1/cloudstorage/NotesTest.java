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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTest {
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
        createNote("createNote", "notepass");
    }

    private void createNote(String name, String password) throws InterruptedException {
        signupAndLogin(name, password);


        driver.get("http://localhost:" + port + "/home");
        WebDriverWait wait = new WebDriverWait(driver, 3);
        WebElement homeMarker = wait.until(webDriver -> webDriver.findElement(By.id("nav-files-tab")));
        assertNotNull(homeMarker);


        home.enterNote("Title", "Text");
        Thread.sleep(3000);


        assertEquals("Title", home.getFirstNoteTitle());
        assertEquals("Text", home.getFirstNoteText());
    }

    private void signupAndLogin(String name, String password) {

        signup.signUp("Janani", "Bharathi", name, password);


        driver.get("http://localhost:" + port + "/login");
        login.enterUsernameAndPassword(name, password);
    }

    @Test
    public void testEdit() throws InterruptedException {
        createNote("editNote", "edit");
        home.editNote("Title1", "Text1");
        Thread.sleep(3000);


        assertEquals("Title1", home.getFirstNoteTitle());
        assertEquals("Text1", home.getFirstNoteText());
    }

    @Test
    public void testDelete() throws InterruptedException {
        createNote("deleteNote", "delete");
        home.deleteNote();
        Thread.sleep(3000);


        try {
            home.getFirstNoteTitle();
            fail("Note not deleted");
        } catch (NoSuchElementException e) {
            assertTrue(true);
        }
    }
}
