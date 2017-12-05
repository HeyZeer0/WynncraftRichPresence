package net.heyzeer0.wrp.utils;

import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by HeyZeer0 on 05/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class Updater {

    public static boolean sended = false;
    public static boolean hasUpdate = false;
    public static String latestUpdate = Reference.MOD_VERSION;

    public static void checkForUpdates() {
        new Thread(() -> {
            try{
                URLConnection st = new URL("http://dl.heyzeer0.cf/WynnRP/version").openConnection();
                st.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                String msg = IOUtils.toString(st.getInputStream());
                if(!Reference.MOD_VERSION.equalsIgnoreCase(msg)) {
                    hasUpdate = true;
                    latestUpdate = msg;
                }
            }catch(Exception ignored) { ignored.printStackTrace(); }
        }).start();
    }

}
