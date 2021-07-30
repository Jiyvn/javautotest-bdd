import io.cucumber.testng.*;

import org.testng.annotations.*;
import utils.TestListener;



@Listeners(TestListener.class)
@CucumberOptions(
        features = "src/test/resources/features/login163mail.feature",
//        features = "src/test/resources/features",
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
public class CucumberSearchRunner extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }

}