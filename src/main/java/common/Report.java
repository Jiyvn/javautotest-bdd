package common;

import java.io.IOException;

public class Report {
    private static String os = System.getProperty("os.name");

    public static void dumpReport() throws IOException {
        dumpReport(true);
    }

    public static void dumpReport(boolean clean) throws IOException {
        String[] cmd;
        if (os.startsWith("Windows")) {
            cmd = new String[]{"cmd", "-/c", String.format("allure generate %s -o %s %s",
                    "result/allure-results", "result/allure-report", clean ? "--clean" : "")};
        }else{
            cmd = new String[]{"/bin/sh", "-c", String.format("allure generate %s -o %s %s",
                    "result/allure-results", "result/allure-report", clean ? "--clean" : "")};
        }
        Runtime.getRuntime().exec(cmd);
    }
}
