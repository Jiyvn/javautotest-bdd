package auto;

public class propertyLoader {

    static String home = System.getenv("HOME") == null ? System.getenv("HOMEPATH") : System.getenv("HOME");

    public static void setDriverExecutor(){
        System.setProperty("webdriver.chrome.driver", home + "/webdrivers/chromedriver");
        System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
        // firefox dev edition application
        System.setProperty("webdriver.firefox.bin", "/Applications/Firefox Developer Edition.app/Contents/MacOS/firefox");
        System.setProperty("webdriver.gecko.driver", home + "/webdrivers/geckodriver");
    }
}
