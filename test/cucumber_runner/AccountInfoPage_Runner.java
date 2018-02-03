package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/AccountInfoPage.feature",
		glue="cucumber_stepDefinition.accountInfo",
		plugin= {"pretty", "html:target/CucumberReports/AccountInfoPage","junit:target/CucumberReports/AccountInfoPage/junit.xml"}
		)
public class AccountInfoPage_Runner {

}
