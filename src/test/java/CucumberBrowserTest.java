

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.BeforeAll;

import java.io.File;
import java.io.IOException;

import static io.cucumber.junit.platform.engine.Constants.*;
import static java.lang.invoke.MethodHandles.lookup;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,summary")
//@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty,summary, listener.ReportPortalListener")
public class CucumberBrowserTest {

        private static final Logger log = LoggerFactory.getLogger(lookup().lookupClass());

        @BeforeAll
        public void setUpAll() throws IOException {
                // global settings: env, baseurl, etc
                log.info("Runner ==> before all");
                System.out.println("Runner ==> before all");
                // String envPropsFile = Constant.TEST_RSRC_DIR + System.getProperty("test.env").toLowerCase() + ".properties";
                // Constant.PROPS = PropertyLoader.loadPropertyFromFile(new File(envPropsFile));


        }

}