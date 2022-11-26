package pages.web.home;

import core.GlobalConstants;
import core.Keyword;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Map;

public class HomePage extends Keyword {
    private static final Logger log = Logger.getLogger(HomePage.class);

    private final String strCategoryMenu = "//ul[contains(@class,'categorymenu')]//li[contains(string(),'%s')]";
    private final String strSubCategories = ".//div[@class='subcategories']//a";
    private final String strSubCategory = ".//div[@class='subcategories']//a[contains(text(),'%s')]";

    private WebElement selectedMenu = null;

    public void openChromeBrowser() {
        webDriver.get("http://www.google.com");
    }

    public void navigateToDemoSite() {
        webDriver.get(GlobalConstants.LINK_DEMO_PAGE);
    }

    public void verifyOpenedHomePage() {
        waitForPageLoaded();
        String url = webDriver.getCurrentUrl();
        Assert.assertEquals(GlobalConstants.LINK_DEMO_PAGE, url);
    }

    public void hoverMouseToMenu(String menuName) {
        try {
            menuName = StringUtils.capitalize(menuName.toLowerCase());
            waitForElementClickable(String.format(strCategoryMenu, menuName));
            WebElement categoryMenu = getWebElementByXpath(strCategoryMenu, menuName);
            selectedMenu = categoryMenu;
            Actions actions = new Actions(webDriver);
            actions.moveToElement(categoryMenu).perform();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public void verifyCategoriesInMenu(DataTable data) {
        List<Map<String, String>> listCategories = data.asMaps(String.class, String.class);
        try {
            if (org.apache.commons.lang.StringUtils.isNotBlank(listCategories.get(0).get("CATEGORIES"))) {
                List<WebElement> subCategories = getChildWebElementsByXpath(selectedMenu, strSubCategories, 20, "");
                for (int i = 0; i < subCategories.size(); i++) {
                    Assert.assertEquals(listCategories.get(i).get("CATEGORIES"), subCategories.get(i).getText().trim());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void clickOnCategory(String category) {
        try {
            category = StringUtils.capitalize(category.toLowerCase());
            clickWebElement(getChildWebElementByXpath(selectedMenu, strSubCategory, category));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
