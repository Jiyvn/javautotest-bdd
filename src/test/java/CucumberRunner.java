import common.Browser;
import common.Report;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
//import io.cucumber.testng.TestNGCucumberRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.IOException;

@CucumberOptions(
//        features = "src/test/resources/features/search.feature",
        features = "src/test/resources/features",
        glue = "steps", monochrome = true,
        plugin = {
        "pretty",
        "json:target/cucumber-report/CucumberRunner.json"
        }
)
public class CucumberRunner extends AbstractTestNGCucumberTests {
//        private TestNGCucumberRunner testNGCucumberRunner;

//        @BeforeClass()
//        public void ini(){
//                DesiredCapabilities caps = DesiredCapabilities.firefox();
//                Browser page = new Browser(caps);
//                WebDriver driver = page.setBrowser("firefox").setOptions().Start();
//        }


//        @AfterSuite()
//        public void dumpReport() throws IOException {
//                Report.dumpReport();
//        }

}