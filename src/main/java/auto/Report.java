package auto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {
    private static String os = System.getProperty("os.name");
    private static boolean seperateReport = false;
    private static boolean cleanReport = false;
    private static String ReportDir;
    public static Logger log = LoggerFactory.getLogger(Report.class);

    public static void dumpReport() throws IOException {
        ReportDir = ReportDir != null
                ? ReportDir
                : seperateReport
                    ? "allure-report/" + new SimpleDateFormat("yyyyMMdd-HHmm-ss.SSS").format(new Date())
                    : "allure-report";
//        log.info("reportDir: "+ReportDir);
        String allureCmd = String.format("allure generate %s -o %s %s",
                "allure-results", ReportDir, cleanReport ? "--clean" : "");
//        log.info("allureCmd: "+allureCmd);
        String[] cmd = os.startsWith("Windows")
                ? new String[]{"cmd", "-/c", allureCmd}
                : new String[]{"/bin/sh", "-c", allureCmd};
//        log.info("cmd: "+ Arrays.toString(cmd));
        Runtime.getRuntime().exec(cmd);
    }

    public static void Seperate() {
        seperateReport = true;
    }

    public static void Clean() {
        cleanReport = true;
    }

    public static void setReport(String reportDir) {
        ReportDir = reportDir;
    }

    public static void dumpReport(String reportDir, boolean clean, boolean seperate) throws IOException {
        ReportDir = reportDir;
        seperateReport = seperate;
        cleanReport = clean;
        dumpReport();
    }

}
