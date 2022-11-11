package com.udacity.jwdnd.course1.cloudstorage.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Home {

    @FindBy(id = "logout-button")
    private WebElement logoutBtn;

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "close-credential-edit-button")
    private WebElement closeCredentialEditButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlText;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameText;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordText;

    @FindBy(id = "credential-save-button")
    private WebElement credentialSaveButton;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note-button")
    private WebElement saveNoteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "edit-credential-button")
    private WebElement editCredentialButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    private WebDriver webDriver;

    public Home(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void logout() {
        logoutBtn.click();
    }

    public void goToNotesTab() {
        webDriver.manage().window().maximize();
        notesTab.click();
    }

    public void goToCredentialsTab() throws InterruptedException {
        webDriver.manage().window().maximize();
        credentialsTab.click();
    }

    public void enterNote(String title, String text) throws InterruptedException {
        goToNotesTab();
        Thread.sleep(2000);
        addNoteButton.click();
        Thread.sleep(3000);
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(text);
        saveNoteButton.click();
        Thread.sleep(2000);

    }

    public void enterCredentials(List<TestCredential> credentialsToTest) throws InterruptedException {
        for (TestCredential credential : credentialsToTest) {
            enterCredential(credential);
        }
    }

    public void enterCredential(TestCredential credentialTest) throws InterruptedException {
        goToCredentialsTab();
        Thread.sleep(2000);
        addCredentialButton.click();
        Thread.sleep(1000);
        credentialUrlText.sendKeys(credentialTest.url);
        credentialUsernameText.sendKeys(credentialTest.username);
        credentialPasswordText.sendKeys(credentialTest.password);
        credentialSaveButton.click();
        Thread.sleep(2000);
    }

    public void editCredentials(List<TestCredential> credentials) throws InterruptedException {
        for (TestCredential credential : credentials) {
            editCredential(credential);
        }
    }

    public void editCredential(TestCredential credential) throws InterruptedException {
        goToCredentialsTab();
        Thread.sleep(2000);
        WebElement buttonsTd = webDriver.findElement(By.id(credential.getId()));
        WebElement editButton = buttonsTd.findElement(By.id("edit-credential-button"));
        editButton.click();
        Thread.sleep(3000);
        credentialUrlText.clear();
        credentialUrlText.sendKeys(credential.url);
        credentialUsernameText.clear();
        credentialUsernameText.sendKeys(credential.username);
        credentialPasswordText.clear();
        credentialPasswordText.sendKeys(credential.password);
        credentialSaveButton.click();
        Thread.sleep(2000);
    }

    public void editNote(String title, String text) throws InterruptedException {
        goToNotesTab();
        Thread.sleep(2000);
        editNoteButton.click();
        Thread.sleep(3000);
        noteTitle.clear();
        noteTitle.sendKeys(title);
        noteDescription.clear();
        noteDescription.sendKeys(text);
        saveNoteButton.click();
        Thread.sleep(2000);
    }

    public void deleteNote() throws InterruptedException {
        goToNotesTab();
        Thread.sleep(2000);
        deleteNoteButton.click();
        Thread.sleep(2000);
    }

    public List<TestCredential> getPageCredentials() throws InterruptedException {
        goToCredentialsTab();
        Thread.sleep(2000);
        WebElement table = webDriver.findElement(By.id("credentialTable"));
        List<String> ids = new ArrayList<>();
        List<WebElement> urls = table.findElements(By.id("show-credential-url"));
        urls.forEach(url -> ids.add(url.getAttribute("tag")));
        List<WebElement> usernames = table.findElements(By.id("show-credential-username"));
        List<WebElement> passwords = table.findElements(By.id("show-credential-password"));

        List<TestCredential> credentialTests = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            credentialTests.add(new TestCredential(ids.get(i), urls.get(i).getText(), usernames.get(i).getText(), passwords.get(i).getText()));
        }
        return credentialTests;
    }

    public String getFirstNoteTitle() {
        return webDriver.findElement(By.id("show-note-title")).getText();
    }

    public String getFirstNoteText() {
        return webDriver.findElement(By.id("show-note-description")).getText();
    }

    public String getShowedPasswordForCredentialId(String id) throws InterruptedException {
        goToCredentialsTab();
        Thread.sleep(2000);
        WebElement buttonsTd = webDriver.findElement(By.id(id));
        WebElement editButton = buttonsTd.findElement(By.id("edit-credential-button"));
        editButton.click();
        Thread.sleep(2000);
        String password = credentialPasswordText.getAttribute("value");
        closeCredentialEditButton.click();
        Thread.sleep(2000);
        return password;
    }

    public void deleteCredentials(List<TestCredential> credentials) throws InterruptedException {
        for (TestCredential credential : credentials) {
            deleteCredential(credential);
        }
    }

    public void deleteCredential(TestCredential credential) throws InterruptedException {
        String id = credential.getId();
        goToCredentialsTab();
        Thread.sleep(2000);
        WebElement buttonsTd = webDriver.findElement(By.id(id));
        WebElement deleteButton = buttonsTd.findElement(By.id("delete-credential-button"));
        deleteButton.click();
        Thread.sleep(2000);
    }

    public static class TestCredential {
        private String url;
        private String id;
        private String password;
        private String username;

        public TestCredential(String id, String url, String username, String password) {
            this.id = id;
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }

        public String getId() {
            return id;
        }
    }
}
