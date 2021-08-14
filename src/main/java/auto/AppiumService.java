package auto;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import static common.Directory.LOG_DIR;
//import static common.Directory.Path;


public class AppiumService {
    public static Logger log = LoggerFactory.getLogger(AppiumService.class);
    private static String os = System.getProperty("os.name");
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 4723;
    private static final String HUB = "http://%s:%d/wd/hub";
    public String port = null;
    public String bp;
    private int timeout = 60;
    public AppiumDriverLocalService service;
    public Process p = null;
//    public  String cmd = "";
    public List<String> argumentList;
    public List<String> extraArguments;
    public String mainjs = null;
    public boolean relax = false;
    public boolean override = true;
    public String logFile = null;
    public String logLevel = "debug";
    public String udid = null;
    public String platform = null;

    public AppiumService(){
        this.argumentList = new ArrayList<>();
        this.extraArguments = new ArrayList<>();
    }

    public AppiumService(int port, String udid){
        this();
        this.setPort(port);
        this.setUdid(udid);
    }

    public AppiumService(int port, int bp, String udid, String platform){
        this(port, udid);
        this.setPlatform(platform);
        this.setBp(bp);
    }

    public AppiumService setPort(int port){
        this.port = String.valueOf(port);
        return this;
    }

    public AppiumService setPlatform(String platform){
        this.platform = platform.toLowerCase();
        return this;
    }

    public AppiumService setUdid(String udid){
        this.udid = udid;
        return this;
    }

    public AppiumService setBp(int bp){
        this.bp = String.valueOf(bp);
        return this;
    }
    public AppiumService setBp(int bp, String platform){
        this.platform = platform.toLowerCase();
        return this.setBp(bp);
    }

    public AppiumService setTimeOut(int timeout){
        this.timeout = timeout;
        return this;
    }

    public AppiumService setMainjs(String mainjs){
        this.mainjs = mainjs;
        return this;
    }

    public AppiumService sessionOverwrite(){
        this.override = true;
        return this;
    }

    public AppiumService relaxSecurity(){
        this.relax = true;
        return this;
    }

    public AppiumService setLogFile(String file){
        this.logFile = file;
        return this;
    }
    public AppiumService setLogLevel(String level){
        this.logLevel = level;
        return this;
    }

    public AppiumService addArgument(String argument){
        this.extraArguments.add(argument);
        return this;
    }
    public AppiumService addArgument(String argument, String value){
        this.extraArguments.add(argument);
        this.extraArguments.add(value);
        return this;
    }

    private void setArguments() throws FileNotFoundException{
        this.argumentList.add("node");
        if (this.mainjs == null) {
            this.mainjs = AppiumJS.MAINJS.get();
        }
        if (this.mainjs == null){
            throw new FileNotFoundException("Appium main.js not found");
        }
       log.debug("mainjs: "+this.mainjs);
        this.argumentList.add(this.mainjs);
        if (this.override) {
            this.argumentList.add("--session-override");
        }
        if (this.relax) {
            this.argumentList.add("--relaxed-security");
        }
        this.argumentList.add("--port");
        if (this.port == null){
            List<int []> ports = Talk.getFreePort(4723, 1, 2);
            this.port = String.valueOf(ports.stream().findFirst().get()[0]);
            this.bp = String.valueOf(ports.stream().findFirst().get()[1]);
        }
        this.argumentList.add(this.port);
        if (this.platform != null){
            String flag = this.platform.equals("android")
                    ? AndroidServerFlag.BOOTSTRAP_PORT_NUMBER.getArgument()
                    : "--webdriveragent-port";
            this.argumentList.add(flag);
            this.argumentList.add(String.valueOf(this.bp));
        }
        if (this.udid != null){
            this.argumentList.add("-U");
            this.argumentList.add(this.udid);
        }

        this.argumentList.add("-g");
        if (this.logFile == null){
            this.logFile = Directory.Path(
                    String.format("appium_%s.log", new Date().getTime()
//                            System.currentTimeMillis()
//                            new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS").format(new Date().getTime())
                    ), Directory.LOG_DIR);
        }
        this.argumentList.add(this.logFile);
        this.argumentList.add("--log-level");
        this.argumentList.add(this.logLevel);
    }

    public AppiumService Start() throws IOException {
        if (this.argumentList.size() < 1) {
            this.setArguments();
        }
        this.argumentList.addAll(this.extraArguments);
        log.debug(String.valueOf(this.argumentList));

//        ProcessBuilder builder = new ProcessBuilder();
//        builder.command(argumentList);
//        this.p = builder.redirectErrorStream(true).start();
//
        String[] argList = new String[argumentList.size()];
        this.p = Runtime.getRuntime().exec(argumentList.toArray(argList));
//        if (!waitForActive()){
//            throw new RuntimeException("Appium not started!");
//        }
        return this;
    }

    public Thread ThreadStart(){
        return new Thread(()-> {
            try {
                this.Start();
                while (this.p.isAlive()) {
                    Thread.sleep(2000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public Thread ThreadStop(){
        return new Thread(this::Stop);
    }

    public URL startAppium() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress(DEFAULT_HOST);
        builder.usingPort(Integer.parseInt(port));
        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        if (service == null || !service.isRunning()) {
            throw new RuntimeException("Appium not started!");
        }
        return service.getUrl();
    }

    public boolean isRunning() {
        try {
            return Talk.isActive(String.format(HUB, DEFAULT_HOST, Integer.parseInt(this.port)) +"/status");
        }catch (IOException e) {
            return false;
        }
    }

    public void waitForActive() {
        Date currentTime = new Date();
        while (currentTime.getTime()/1000.00+timeout < new Date().getTime()){
            log.info(
                    String.format("------- starting appium on port %s -------", this.port)
            );
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isRunning()){
                log.info("------- appium started -------");
                return;
            }

        }
        throw new RuntimeException("Appium not started!");
    }

    public String getPid(String port) throws IOException {
        String[] pidcmd;
        String pid = null;
        if (!os.startsWith("Windows")) {
            pidcmd = new String[]{
                    "/bin/sh",
                    "-c", String.format("lsof -i tcp:%s |grep LISTEN | awk '{print $2}'", port)
            };
            pid = new BufferedReader(new InputStreamReader(
                    Runtime.getRuntime().exec(pidcmd).getInputStream()
            )).readLine();
        }else{
            pidcmd = new String[]{"cmd", "-/c", String.format("netstat -ano | findstr %s", port)};
            String line = new BufferedReader(new InputStreamReader(
                    Runtime.getRuntime().exec(pidcmd).getInputStream()
            )).readLine();
            if (line!=null) {
                pid = line.split(" ")[line.split(" ").length - 1];
            }
        }
        return pid;
    }

    public void Stop(){
        if (this.p != null){
            try {
//                this.stopProcess();
                this.p.destroy();
                this.p.waitFor(5, TimeUnit.SECONDS);
                if (this.p.isAlive()){
                    this.p.destroyForcibly();
                }
                log.info(String.format("appium on port %s stopped", this.port));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopProcess(){
        try{
            String pid = getPid(String.valueOf(this.port));
            if (pid == null){
                log.debug(
                        String.format("No process running on port %s", this.port)
                );
            }
            String cmd = os.startsWith("Windows")
                    ? "cmd /c taskkill /f /pid " + pid
                    : "kill" + pid;
            Runtime.getRuntime().exec(cmd);
            log.info(String.format("appium %s killed on %s", pid, this.port));
        }catch (IOException ignored){
        }
    }
}