package auto.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public enum AppiumJS {
    MAINJS();

    private String mainjs;

    AppiumJS() {
        String os = System.getProperty("os.name");
        List<String> jscripts = new ArrayList<>();
        try {
            String[] npmRootcmd = new String[]{"cmd", "-/c", "npm root"};
            String[] npmRootGcmd = new String[]{"cmd", "-/c", "npm root -g"};
            String npmRoot = new BufferedReader(new InputStreamReader(
                    Runtime.getRuntime().exec(npmRootcmd).getInputStream()
            )).readLine();
            String npmRootG = new BufferedReader(new InputStreamReader(
                    Runtime.getRuntime().exec(npmRootGcmd).getInputStream()
            )).readLine();
            if (os.startsWith("Windows")){
                String WIN_PREFIX = System.getenv("Appdata");
                WIN_PREFIX = WIN_PREFIX.substring(0, WIN_PREFIX.length()-8)
                        .replace("\\\\", "/").replace("\\", "/");
                jscripts.add(WIN_PREFIX + "/Local/Programs/Appium/resources/app/node_modules/appium/build/lib/main.js");
                jscripts.add(WIN_PREFIX + "/Local/Programs/appium-desktop/resources/app/node_modules/appium/build/lib/main.js");
                jscripts.add("C:/\\\"Program Files (x86)\\\"/Appium/resources/app/node_modules/appium/build/lib/main.js");
                jscripts.add("C:/\\\"Program Files\\\"/Appium/resources/app/node_modules/appium/build/lib/main.js");
            }else if(os.startsWith("Mac")){
                jscripts.add("/Applications/Appium.app/Contents/Resources/app/node_modules/appium/build/lib/main.js");
            }
            jscripts.add(npmRootG +"/appium/build/lib/main.js");
            jscripts.add(npmRoot +"/appium/build/lib/main.js");
            for (String js: jscripts){
                if ((new File(js)).exists()){
                    this.mainjs = js;
                    break;
                }
            }

        }catch (IOException ignored){}
    }

    public String get() {
        return this.mainjs;
    }
}
