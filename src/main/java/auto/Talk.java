package auto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Talk {
    public static Logger log = LoggerFactory.getLogger(Talk.class);
    private static String os = System.getProperty("os.name");

    public static boolean portInUse(int port) {
        String[] cmd = os.startsWith("Windows")
                ? new String[]{"cmd", "-/c", String.format("netstat -ano | findstr %d", port)}
                : new String[]{"/bin/sh", "-c", String.format("lsof -i tcp:%d |grep LISTEN", port)}; // filter out close_wait status
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream())
        )) {
            String line = br.readLine();
            return line!=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static List<int []> getFreePort(int startPort, int pairs, int amountPerPairs){
        List<int []> ports = new ArrayList<>();
        int i = 0;
        int amount = 0;
        while(amount < amountPerPairs*pairs){
            if(!portInUse(startPort)){
                amount += 1;
                if (i % amountPerPairs==0) {
                    i = 0;
                    ports.add(new int[amountPerPairs]);
                }
                ports.get(ports.size()-1)[i] = startPort;
                i += 1;
            }
            startPort += 1;
        }
        return ports;
    }

    public static boolean isActive(String url) throws IOException {
        URL server_url = new URL(url);
        URLConnection conn = server_url.openConnection();
//        conn.setUseCaches(false);
        conn.setConnectTimeout(5000);
        Map<String, List<String>> headers = conn.getHeaderFields();
        Set<String> keys = headers.keySet();
        for( String key : keys ){
            String val = conn.getHeaderField(key);
            if (val.contains("HTTP") && val.split(" ")[1].startsWith("2")){
                return true;
            }
        }
        return false;
    }

    public static boolean portIsFree(int port) {
        return portIsFree("127.0.0.1", port);
    }

    public static boolean portIsFree(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            socket.close();
            return false;
        } catch (IOException e) {
            return true;
        }

    }
}
