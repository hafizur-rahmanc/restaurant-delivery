package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/PaymentPage.feature",
		glue="cucumber_stepDefinition.payment",
		plugin= {"pretty", "html:target/CucumberReports/PaymentPage","junit:target/CucumberReports/PaymentPage/junit.xml"}
		)
public class PaymentPage_Runner {

}
