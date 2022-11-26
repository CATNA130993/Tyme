package pages.web.category.tshirts;

import core.Keyword;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;

public class TShirtsPage extends Keyword {
    private static final Logger log = Logger.getLogger(TShirtsPage.class);

    private String strUnitPrice = "//div[contains(@class,'grid')]//div[@class='oneprice']";
    private String strAddToCart = "//div[contains(@class,'grid')]//a[@data-id='%s']";


    @FindBy(id = "product_details")
    private WebElement productDetailElement;
    @FindBy(xpath = "//span[@class='maintext']")
    private WebElement headingElement;
    @FindBy(id = "sort")
    private WebElement sortElement;

    public void verifyTShirtsPage(String category) {
        waitForPageLoaded();
        try {
            Assert.assertEquals(category.toUpperCase(), headingElement.getText());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void sortByOnTShirtPage(String sortBy) {
        try {
            selectOptionToElement(sortElement, sortBy);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void verifyThatAllItemsWereSorted(DataTable data) {
        List<Map<String, String>> listSorts = data.asMaps(String.class, String.class);
        try {
            if (listSorts.get(0).get("SortType") == "Price" && StringUtils.isNotBlank(listSorts.get(0).get("SortName"))) {
                List<WebElement> sortElements = getWebElementsByXpath(strUnitPrice, 1, "");
                float currentPrice = 0;
                float nextPrice = 0;
                log.info(sortElements.size());
                for (int i = 0; i < sortElements.size() - 1; i++) {
                    for (int j = 0; j < sortElements.size() - 1; j++) {
                        currentPrice = Float.parseFloat(sortElements.get(j).getText().substring(1));
                        nextPrice = Float.parseFloat(sortElements.get(j + 1).getText().substring(1));
                        if (listSorts.get(0).get("SortName") == "Low > High") {
                            Assert.assertFalse(currentPrice > nextPrice);
                        } else if (listSorts.get(0).get("SortName") == "High > Low") {
                            Assert.assertFalse(currentPrice < nextPrice);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void addToCardAnItemOnTShirts(DataTable data) {
        List<Map<String, String>> listProduct = data.asMaps(String.class, String.class);
        try {
            if (StringUtils.isNotBlank(listProduct.get(0).get("ProductID"))) {
                clickWebElement(getWebElementByXpath(strAddToCart, listProduct.get(0).get("ProductID")));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void verifyTheItemDetailedInformation() {
        waitForPageLoaded();

        String currentUrl = webDriver.getCurrentUrl();
        String expectedUrl = "https://automationteststore.com/index.php?rt=product/product&product_id=121";
        Assert.assertEquals(expectedUrl, currentUrl);

        Assert.assertTrue(isElementDisplayed(productDetailElement));
    }
}
