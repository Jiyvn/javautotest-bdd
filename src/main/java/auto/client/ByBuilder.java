package auto.client;

import io.appium.java_client.pagefactory.bys.builder.AppiumByBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;

import java.lang.reflect.Field;

public class ByBuilder {

    class findByBuilder extends AbstractFindByBuilder{
        @Override
        public By buildIt(Object annotation, Field field) {
            return null;
        }
    }

    class appiumByBuilder extends AppiumByBuilder{
        protected appiumByBuilder(String platform, String automation) {
            super(platform, automation);
        }

        @Override
        public By buildBy() {
            return null;
        }

        @Override
        public boolean isLookupCached() {
            return false;
        }

        @Override
        protected By buildDefaultBy() {
            return null;
        }

        @Override
        protected By buildMobileNativeBy() {
            return null;
        }

        @Override
        protected void assertValidAnnotations() {

        }
    }

}
