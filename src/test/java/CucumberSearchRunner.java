import io.cucumber.testng.*;

import org.testng.annotations.*;
import listener.TestListener;



@Listeners(TestListener.class)
@CucumberOptions(
        features = "src/test/resources/features/Search.feature",
//        features = "src/test/resources/features",
        glue = "steps/web", monochrome = true,
        plugin = {
//                "pretty",
//                "json:target/cucumber-report/CucumberRunner.json",
//                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "listener.cucumberListener",
                "progress",
                "summary",
//                "testng"
        }
)
public class CucumberSearchRunner extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
//        @DataProvider
        public Object[][] scenarios() {
                return super.scenarios();
        }

}