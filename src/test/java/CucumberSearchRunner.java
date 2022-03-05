import auto.client.Browser;
import io.cucumber.testng.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import listener.TestListener;

import java.util.logging.Level;


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
//                "testng",
                // mvn test && mvn test -Dcucumber.features="@searchRerun.txt"
                "rerun:searchRerun.txt"
        }
)
public class CucumberSearchRunner extends AbstractTestNGCucumberTests {
        public static Logger log = LoggerFactory.getLogger(CucumberSearchRunner.class);

        @Override
        @DataProvider(parallel = true)
//        @DataProvider
        public Object[][] scenarios() {
                return super.scenarios();
        }

        @BeforeClass
        public void setLog(){
                log.info("set log level to OFF");
                java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
                java.util.logging.Logger.getLogger("io.netty").setLevel(Level.OFF);
        }

}