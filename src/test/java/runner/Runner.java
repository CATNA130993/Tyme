package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.restassured.RestAssured;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.*;
import org.junit.runner.RunWith;
//import utillities.DateTimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static core.Common.*;


@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        glue = {"steps"},
        plugin = { "pretty", "json:target/cucumber-reports/cucumber.json","json:reports/cucumber.json", "html:target/cucumber-reports"}
)

public class Runner {
    private static String strOutputDirectory = null;
    private static File outputDirectory = null;
    private static String sExecuteTime = null;
    public static int iFail = 0;
    public static int iPass = 0;
    public static List<String> failScenarios = new ArrayList<>();
    public static List<String> passScenarios = new ArrayList<>();

    private static final Logger log = Logger.getLogger(Runner.class);

    @BeforeClass
    public static void setUp() throws Exception {
        sExecuteTime = getTime();
        strOutputDirectory = "reports/" + sExecuteTime;
        outputDirectory = new File(strOutputDirectory);

        String log4jConfPath = "src/test/resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        RestAssured.useRelaxedHTTPSValidation();
    }

    @AfterClass
    public static void teardown() throws Exception {
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("reports/cucumber.json");
        Configuration config = new Configuration(outputDirectory, "Automation-Framework");
        config.addClassifications("Environment", "Staging Environment");
        config.addClassifications("Owner", "Cat Nguyen");

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, config);
        reportBuilder.generateReports();

        sendEmailReport("",iPass, iFail, passScenarios, failScenarios);
    }
}