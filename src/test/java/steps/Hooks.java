package steps;

import core.CorePage;
import core.GlobalConstants;
import gherkin.ast.Step;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import runner.Runner;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Hooks extends CorePage {
    private static final Logger log = Logger.getLogger(Hooks.class);

    //==============SYSTEM PROPERTIES INPUT WHEN RUN CMD======================
    private static final String mode = System.getProperty("mode");
    private static final String environment = System.getProperty("environment");

    //====== DEFINE HOOK========================================

    @Before
    public void beforeScenario()throws Exception{
        log.info(" === This will run before the scenario === ");

        // Getting params in the properties file
        getParamsPropertiesFile(environment);

        // For web application
        if (mode.equalsIgnoreCase("web")) {
            System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe");
            FirefoxOptions opt = new FirefoxOptions();
            opt.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            opt.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            opt.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
            opt.addPreference("dom.webnotifications.enabled", false);
            webDriver = new FirefoxDriver(opt);
            webDriver.manage().window().maximize();
            webDriver.manage().deleteAllCookies();
            webDriver.manage().timeouts().implicitlyWait(GlobalConstants.WAIT_IMPLICIT, TimeUnit.SECONDS);

            // Define init pages
            initWebPage();
            initWebElement();
        }
    }

    @After
    public void afterScenario(Scenario scenario) {

        log.info(" === This will run after the scenario === ");

        if (mode.equalsIgnoreCase("web")) {
            {
                if (scenario.isFailed()) {
                    try {
                        final byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
                        scenario.embed(screenshot, "image/png");
                    } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                        log.error(somePlatformsDontSupportScreenshots.getMessage());
                    }
                }

                webDriver.quit();
            }
            if (scenario.isFailed()) {
                Runner.iFail++;
                Runner.failScenarios.add(scenario.getName());
            } else {
                Runner.iPass++;
                Runner.passScenarios.add(scenario.getName());
            }
        }
    }

    public static void getParamsPropertiesFile(String p_environment) throws Exception {
        Properties prop = new Properties();
        FileInputStream ip = null;
        if (p_environment.equalsIgnoreCase("staging")) {
            ip = new FileInputStream("src/test/resources/staging.properties");
        }
        prop.load(ip);

        // ==========GET SOME PROPERTIES
        GlobalConstants.LINK_DEMO_PAGE = prop.getProperty("demo_page");
        ip.close();
    }
}