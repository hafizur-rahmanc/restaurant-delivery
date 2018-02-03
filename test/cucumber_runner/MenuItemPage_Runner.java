package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/MenuItemPage.feature",
		glue="cucumber_stepDefinition.menuItem",
		plugin= {"pretty", "html:target/CucumberReports/MenuItemPage","junit:target/CucumberReports/MenuItemPage/junit.xml"}
		)
public class MenuItemPage_Runner {

}
