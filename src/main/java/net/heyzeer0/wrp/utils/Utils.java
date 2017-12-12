package net.heyzeer0.wrp.utils;

import net.heyzeer0.wrp.Main;
import net.heyzeer0.wrp.profiles.LocationProfile;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class Utils {

    public static ArrayList<LocationProfile> locations = new ArrayList<>();

    public static void updateRegions() {
        new Thread(() -> {
            try{
                URLConnection st = new URL("https://api.wynncraft.com/public_api.php?action=territoryList").openConnection();
                st.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

                JSONObject main = new JSONObject(IOUtils.toString(st.getInputStream())).getJSONObject("territories");

                for(String key : main.keySet()) {
                    if(main.getJSONObject(key).has("location")) {
                        JSONObject loc = main.getJSONObject(key).getJSONObject("location");
                        locations.add(new LocationProfile(key, loc.getInt("startX"), loc.getInt("startY"), loc.getInt("endX"), loc.getInt("endY")));
                    }
                }

                locations.add(new LocationProfile("Rodoroc", 1009, -5231, 1263, -5057));

            }catch (Exception ex) {
                Main.logger.warn("Error captured while trying to connect to Wynncraft location api", ex);}

        }).start();
    }

}
