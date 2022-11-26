package core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Keyword extends CorePage {

    private static final Logger log = Logger.getLogger(Keyword.class);

    public static void clickWebElement(WebElement ele) {
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(ele));
        ele.click();
    }

    public static boolean isWebElementDisplayed(String strXpath, int iLoop) throws Exception {
        boolean bResult = false;
        for (int i = 0; i < iLoop; i++) {
            if (webDriver.findElements(By.xpath(strXpath)).size() != 0) {
                bResult = true;
                break;
            } else {
                Thread.sleep(GlobalConstants.WAIT_EXPLICIT);
            }
        }
        return bResult;
    }

    public static WebElement getWebElementByXpath(String strXpath, String... arg) throws Exception {
        WebElement ele = null;

        for (int i = 0; i < 30; i++) {
            if (webDriver.findElements(By.xpath(String.format(strXpath, arg))).size() == 1) {
                ele = webDriver.findElement(By.xpath(String.format(strXpath, arg)));
                break;
            } else {
                waitSomeSecond(3);
            }
        }
        return ele;
    }

    public static WebElement getChildWebElementByXpath(WebElement parentElement, String strXpath, String... arg) throws Exception {
        WebElement ele = null;

        for (int i = 0; i < 30; i++) {
            if (parentElement.findElements(By.xpath(String.format(strXpath, arg))).size() == 1) {
                ele = parentElement.findElement(By.xpath(String.format(strXpath, arg)));
                break;
            } else {
                waitSomeSecond(3);
            }
        }
        return ele;
    }

    public static List<WebElement> getChildWebElementsByXpath(WebElement parentElement, String strXpath, int iLoop, String... arg) throws Exception {
        List<WebElement> eles = null;

        for (int i = 0; i < iLoop; i++) {
            if (parentElement.findElements(By.xpath(String.format(strXpath, arg))).size() != 0) {
                eles = parentElement.findElements(By.xpath(String.format(strXpath, arg)));
                break;
            } else {
                waitSomeSecond(3);
            }
        }
        return eles;
    }

    public static List<WebElement> getWebElementsByXpath(String strXpath, int iLoop, String... arg) throws Exception {
        List<WebElement> eles = null;

        for (int i = 0; i < iLoop; i++) {
            if (webDriver.findElements(By.xpath(String.format(strXpath, arg))).size() != 0) {
                eles = webDriver.findElements(By.xpath(String.format(strXpath, arg)));
                break;
            } else {
                waitSomeSecond(3);
            }
        }
        return eles;
    }

    public static void waitSomeSecond(int second) throws Exception {
        Thread.sleep(second * 1000);
    }

    public WebElement waitForElementClickable(String locator) {
        WebDriverWait wait = new WebDriverWait(webDriver, GlobalConstants.WAIT_EXPLICIT);
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
    }

    public void waitForPageLoaded(long timeout) {
        ExpectedCondition<Boolean> expectation = driverWait -> {
            assert driverWait != null;
            return ((JavascriptExecutor) driverWait).executeScript("return document.readyState").toString().equalsIgnoreCase("complete");
        };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(webDriver, timeout);
            wait.until(expectation);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (WebDriverException e) {
            if (!e.getMessage().contains("target frame detached")) {
                throw new Error(e.getMessage());
            }
        }
    }

    public void waitForPageLoaded() {
        waitForPageLoaded(GlobalConstants.WAIT_EXPLICIT);
    }

    public void selectOptionToElement(WebElement element, String text) {
        Select elementDropdown = new Select(element);
        elementDropdown.selectByVisibleText(text);
    }

    public boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception ex) {
            return false;
        }

    }
}