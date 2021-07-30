package common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {
    private static String os = System.getProperty("os.name");
    private static boolean seperateReport = false;
    private static boolean cleanReport = false;

    public static void dumpReport() throws IOException {
        String ReportDir = seperateReport
                ? "allure-report/" + new SimpleDateFormat("yyyyMMdd-HHmm-ss.SSS").format(new Date())
                : "allure-report";
        String allureCmd = String.format("allure generate %s -o %s %s",
                "allure-results", ReportDir, cleanReport ? "--clean" : "");
        String[] cmd = os.startsWith("Windows")
                ? new String[]{"cmd", "-/c", allureCmd}
                : new String[]{"/bin/sh", "-c", allureCmd};
        Runtime.getRuntime().exec(cmd);
    }

    public static void Seperate(){
        seperateReport = true;
    }

    public static void Clean(){
        cleanReport = true;
    }

    public static void dumpReport(boolean clean, boolean seperate) throws IOException {
        seperateReport = seperate;
        cleanReport = clean;
        dumpReport();
    }

}
