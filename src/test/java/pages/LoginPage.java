package pages;

public class LoginPage {

    public void enterUsername(String username) {
        System.out.println("Entering username: " + username);
    }

    public void enterPassword(String password) {
        System.out.println("Entering password: " + password);
    }

    public void clickLogin() {
        System.out.println("Tapping login button");
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}