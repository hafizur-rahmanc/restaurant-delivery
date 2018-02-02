package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/ReviewOrderPage.feature",
		glue="cucumber_stepDefinition.reviewOrder",
		plugin= {"pretty", "html:target/cucumber"}
		)
public class ReviewOrderPage_Runner {

}
