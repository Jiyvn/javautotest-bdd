import common.AppiumService;
import common.Browser;
import common.Report;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.Status;
import io.cucumber.testng.*;
//import io.cucumber.testng.TestNGCucumberRunner;
import io.qameta.allure.Epic;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import utils.TestListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Listeners(TestListener.class)
@CucumberOptions(
//        features = "src/test/resources/features/login163mail.feature",
        features = "src/test/resources/features",
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
public class CucumberRunner extends AbstractTestNGCucumberTests {

//        private TestNGCucumberRunner testNGCucumberRunner;
        public static Logger log = LoggerFactory.getLogger(CucumberRunner.class);


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