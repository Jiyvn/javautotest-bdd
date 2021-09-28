import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import listener.TestListener;

//import io.cucumber.testng.TestNGCucumberRunner;
//import io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm;

@Listeners(TestListener.class)
@CucumberOptions(
        features = "src/test/resources/features/login163mail.feature",
//        features = "src/test/resources/features",
        glue = "steps", monochrome = true,
        plugin = {
//                "pretty",
//                "json:target/cucumber-report/CucumberRunner.json",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                "listener.cucumberListener",
                "progress",
                "summary",
//                "testng"
        }
)
public class CucumberMailRunner extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
//        @DataProvider
        public Object[][] scenarios() {
                return super.scenarios();
        }

}