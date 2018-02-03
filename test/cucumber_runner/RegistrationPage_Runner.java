package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/RegistrationPage.feature",
		glue="cucumber_stepDefinition.registration",
		plugin= {"pretty", "html:target/CucumberReports/RegistrationPage","junit:target/CucumberReports/RegistrationPage/junit.xml"}
		)
public class RegistrationPage_Runner {

}
