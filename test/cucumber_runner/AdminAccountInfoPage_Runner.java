package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/AdminAccountInfoPage.feature",
		glue="cucumber_stepDefinition.adminAccountInfo",
		plugin= {"pretty", "html:target/CucumberReports/AdminAccountInfoPage","junit:target/CucumberReports/AdminAccountInfoPage/junit.xml"}
		)
public class AdminAccountInfoPage_Runner {

}
