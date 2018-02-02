package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/SingleItemPage.feature",
		glue="cucumber_stepDefinition.singleItem",
		plugin= {"pretty", "html:target/cucumber"}
		)
public class SingleItemPage_Runner {

}
