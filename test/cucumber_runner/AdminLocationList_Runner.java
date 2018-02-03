package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/AdminLocationListPage.feature",
		glue="cucumber_stepDefinition.adminLocationList",
		plugin= {"pretty", "html:target/CucumberReports/AccountLocationListPage","junit:target/CucumberReports/AccountLocationListPage/junit.xml"}
		)
public class AdminLocationList_Runner {

}
