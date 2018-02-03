package cucumber_runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="test/cucumber_feature/AdminOrderListPage.feature",
		glue="cucumber_stepDefinition.adminOrderList",
		plugin= {"pretty", "html:target/CucumberReports/AccountOrderListPage","junit:target/CucumberReports/AccountOrderListPage/junit.xml"}
		)
public class AdminOrderListPage_Runner {

}
