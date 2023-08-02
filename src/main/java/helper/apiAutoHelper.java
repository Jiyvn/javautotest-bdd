package helper;

import io.cucumber.java.Scenario;
import utils.RequestUtil;

public final class apiAutoHelper {

    public static InheritableThreadLocal<RequestUtil> requestContext = new InheritableThreadLocal<RequestUtil>(){
        @Override
        protected synchronized RequestUtil initialValue() {
            return null;
        }
    };

    public static RequestUtil getRequestContext(){
        return requestContext.get();
    }

    public static RequestUtil setRequestContext(String baseUri){
        return requestContext.get().setBaseUri(baseUri);
    }

    public static void setRequestContext(RequestUtil reqContext){
        requestContext.set(reqContext);
    }

    public static void resetContext(){
        requestContext.remove();
    }


    public static final InheritableThreadLocal<Scenario> scenario = new InheritableThreadLocal<Scenario>() {
        @Override
        protected synchronized Scenario initialValue() {
            return null;
        }
    };

    public static Scenario getScenario() {
        return scenario.get();
    }

    public static void setScenario(Scenario sc) {
        scenario.set(sc);
    }

    public static void resetScenario(){
        scenario.remove();
    }


}
