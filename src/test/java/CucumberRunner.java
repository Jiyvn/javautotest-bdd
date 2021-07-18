import common.Report;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
//import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.*;

import java.io.IOException;


//@Test
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "steps", monochrome = true,
        plugin = {
//                "pretty",
//                "json:target/cucumber-report/CucumberRunner.json",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
//                "progress",
//                "summary",
                "testng"
        }
)
public class CucumberRunner extends AbstractTestNGCucumberTests {

//        private TestNGCucumberRunner testNGCucumberRunner;


        @AfterSuite()
        public void dumpReport() throws IOException {
                Report.dumpReport();
        }

}