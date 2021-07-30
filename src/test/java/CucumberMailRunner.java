import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import utils.TestListener;

//import io.cucumber.testng.TestNGCucumberRunner;


@Listeners(TestListener.class)
@CucumberOptions(
        features = "src/test/resources/features/Search.feature",
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

//        private TestNGCucumberRunner testNGCucumberRunner;
        public static Logger log = LoggerFactory.getLogger(CucumberMailRunner.class);


//        /**
//         * Returns two dimensional array of {@link PickleWrapper}s with their
//         * associated {@link FeatureWrapper}s.
//         *
//         * @return a two dimensional array of scenarios features.
//         */
//        @DataProvider
//        public Object[][] scenarios() {
//                if (testNGCucumberRunner == null) {
//                        return new Object[0][0];
//                }
//                return testNGCucumberRunner.provideScenarios();
//        }

}