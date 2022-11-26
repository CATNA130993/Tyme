package steps.web.demo;

import core.CorePage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apache.log4j.Logger;

public class DemoSteps extends CorePage {
    private static final Logger log = Logger.getLogger(DemoSteps.class);

    @Given("I can open Chrome")
    public void openChromeBrowser() {
        log.info(" ==== I can open Chrome ==== ");
        homePage.openChromeBrowser();
    }

    @And("I access this page: ([^\"]*)")
    public void navigateToDemoSite(String url) {
        log.info(" ==== I access this page: " + url + " ==== ");
        homePage.navigateToDemoSite();
    }

    @Then("I can see the home page of the Automation test Store")
    public void verifyOpenedHomePage() {
        log.info(" ==== I can see the home page of the Automation test Store ==== ");
        homePage.verifyOpenedHomePage();
    }

    @And("I can hover my mouse to ([^\"]*) menu")
    public void hoverMouseToMenu(String menuName) {
        log.info(" ==== I can hover my mouse to " + menuName + " menu ==== ");
        homePage.hoverMouseToMenu(menuName);
    }

    @Then("I can see categories in APPAREL & ACCESSORIES menu")
    public void verifyCategoriesInMenu(DataTable data) {
        log.info(" ==== I can see categories in APPAREL & ACCESSORIES menu ==== ");
        homePage.verifyCategoriesInMenu(data);
    }

    @And("I can click on ([^\"]*) categories")
    public void clickOnCategory(String category) {
        log.info(" ==== I can click on " + category + " categories ==== ");
        homePage.clickOnCategory(category);
    }

    @Then("I can see ([^\"]*) page")
    public void verifyTShirtsPage(String category) {
        log.info(" ==== I can see " + category + " page ==== ");
        tShirtsPage.verifyTShirtsPage(category);
    }

    @And("I can select Sort by ([^\"]*) on T-Shirt page")
    public void sortByOnTShirtPage(String sortBy) {
        log.info(" ==== I can select Sort by " + sortBy + " on T-Shirt page ==== ");
        tShirtsPage.sortByOnTShirtPage(sortBy);
    }

    @And("I can verify that all items were sorted")
    public void verifyThatAllItemsWereSorted(DataTable data) {
        log.info(" ==== I can verify that all items were sorted ==== ");
        tShirtsPage.verifyThatAllItemsWereSorted(data);
    }

    @Then("I add to Card an Item on T-shirts")
    public void addToCardAnItemOnTShirts(DataTable data) {
        log.info(" ==== I add to Card an Item on T-shirts ==== ");
        tShirtsPage.addToCardAnItemOnTShirts(data);
    }

    @And("I can see the item detailed information")
    public void verifyTheItemDetailedInformation() {
        log.info(" ==== I can see the item detailed information ==== ");
        tShirtsPage.verifyTheItemDetailedInformation();
    }
}