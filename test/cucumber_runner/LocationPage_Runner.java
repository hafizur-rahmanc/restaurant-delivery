package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/LocationPage.feature",
		glue="cucumber_stepDefinition.location",
		plugin= {"pretty", "html:target/CucumberReports/LocationPage","junit:target/CucumberReports/LocationPage/junit.xml"}
		)
public class LocationPage_Runner {

}
