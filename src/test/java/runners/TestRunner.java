package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/resources/features", 
				 glue= {"stepDefinations"},
				 plugin= "json:target/jsonReports/mavenCucumberReport.json"  
//				 tags = "@DeletePlace"
				)
public class TestRunner extends AbstractTestNGCucumberTests{

}
