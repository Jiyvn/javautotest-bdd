package utils;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;

public class Assertion extends Assert {

    public static void assertTrue(boolean condition, Exception exception) throws Exception {
        try{
            assertTrue(condition);
        }catch (AssertionError e){
            StackTraceElement[] ars = new ArrayList<>(
                    Arrays.asList(e.getStackTrace())).toArray(new StackTraceElement[0]);
            exception.setStackTrace(ars);
            throw exception;
//            Class<?>[]  arr = {String.class, Throwable.class};
//            throw ex.getClass().getDeclaredConstructor(arr).newInstance(ex.getMessage(), e);
        }
    }
    public static void assertTrue(boolean condition, String message, Class<Exception> cls) throws Exception {
        try{
            assertTrue(condition);
        }catch (AssertionError e){
//            Exception exception = cls.getDeclaredConstructor(new Class[]{String.class}).newInstance(message);
//            StackTraceElement[] ars = new ArrayList<>(
//                    Arrays.asList(e.getStackTrace())).toArray(new StackTraceElement[0]);
//            exception.setStackTrace(ars);
//            throw exception;
//            Class<?>[]  arr = new Class[]{String.class, Throwable.class};
//            Class<?>[]  arr = {String.class, Throwable.class};
            throw cls.getDeclaredConstructor(
                    new Class[]{String.class, Throwable.class}
            ).newInstance(message, e);
        }
    }

}
