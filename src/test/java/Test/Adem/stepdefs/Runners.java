package Test.Adem.stepdefs;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = {"src/test/java/Test/Adem/features/"},
        glue = {"Test/Adem/stepdefs"},
        tags = "@LoginPage",
        dryRun = false,
        plugin = { "pretty",
                "json:test-output/cucumber-reports/cucumber.json",
                "html:test-output/cucumber-reports/cucumberreport.html",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class Runners extends AbstractTestNGCucumberTests {

}
