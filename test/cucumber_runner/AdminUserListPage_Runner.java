package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/AdminUserListPage.feature",
		glue="cucumber_stepDefinition.adminUserList",
		plugin= {"pretty", "html:target/CucumberReports/AccountUserListPage","junit:target/CucumberReports/AccountUserListPage/junit.xml"}
		)
public class AdminUserListPage_Runner {

}
