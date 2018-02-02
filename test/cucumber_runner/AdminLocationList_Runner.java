package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/AdminLocationListPage.feature",
		glue="cucumber_stepDefinition.adminLocationList",
		plugin= {"pretty", "html:target/cucumber"}
		)
public class AdminLocationList_Runner {

}
