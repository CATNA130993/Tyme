package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pages.web.category.tshirts.TShirtsPage;
import pages.web.home.HomePage;

public class CorePage {
    public static WebDriver webDriver = null;

    // Web application
    protected static HomePage homePage;
    protected static TShirtsPage tShirtsPage;

    protected static void initWebPage() {
        homePage = new HomePage();
        tShirtsPage = new TShirtsPage();
    }

    protected static void initWebElement() {
        PageFactory.initElements(webDriver, homePage);
        PageFactory.initElements(webDriver, tShirtsPage);
    }
}