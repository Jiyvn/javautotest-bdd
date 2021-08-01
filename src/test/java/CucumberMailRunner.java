import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import utils.TestListener;

//import io.cucumber.testng.TestNGCucumberRunner;


@Listeners(TestListener.class)
@CucumberOptions(
        features = "src/test/resources/features/login163mail.feature",
//        features = "src/test/resources/features",
        glue = "steps", monochrome = true,
        plugin = {
//                "pretty",
//                "json:target/cucumber-report/CucumberRunner.json",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
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