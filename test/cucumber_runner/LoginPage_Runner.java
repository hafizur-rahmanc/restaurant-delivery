package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/LoginPage.feature",
		glue="cucumber_stepDefinition",
		plugin= {"pretty", "html:target/CucumberReports/LoginPage","junit:target/CucumberReports/LoginPage/junit.xml"}
		)
public class LoginPage_Runner {

}
