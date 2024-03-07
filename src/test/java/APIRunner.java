//import io.cucumber.testng.AbstractTestNGCucumberTests;
//import io.cucumber.testng.CucumberOptions;
//import listener.TestListener;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Listeners;
//
//@Listeners(TestListener.class)
//@CucumberOptions(
//        features = "src/test/resources/features/api.feature",
////        features = "src/test/resources/features",
//        glue = "steps/api", monochrome = true,
//        plugin = {
////                "pretty",
////                "json:target/cucumber-report/CucumberRunner.json",
////                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
//                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
//                "listener.cucumberListener",
//                "progress",
//                "summary",
////                "testng"
//        }
//)
//public class APIRunner extends AbstractTestNGCucumberTests {
//
//    @Override
//    @DataProvider(parallel = true)
////        @DataProvider
//    public Object[][] scenarios() {
//        return super.scenarios();
//    }
//
//}
