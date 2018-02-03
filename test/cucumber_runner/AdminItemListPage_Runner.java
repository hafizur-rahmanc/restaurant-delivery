package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/AdminItemListPage.feature",
		glue="cucumber_stepDefinition.adminItemList",
		plugin= {"pretty", "html:target/CucumberReports/AccountListPage","junit:target/CucumberReports/AccountListPage/junit.xml"}
		)
public class AdminItemListPage_Runner {

}
