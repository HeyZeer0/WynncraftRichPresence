package net.heyzeer0.wrp;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import net.heyzeer0.wrp.events.ChatEvents;
import net.heyzeer0.wrp.events.ServerEvents;
import net.heyzeer0.wrp.utils.Utils;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class Main {

    public static boolean ready = false;
    public static IPCClient client;

    public static Logger logger;

    public static String actualServer = "none";
    public static boolean onServer = false;
    public static String location = "Waiting";

    public static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public static ScheduledFuture updateTimer;

    public static void startRichPresence() {
        try{
            client = new IPCClient(387266678607577088L);
            client.connect(DiscordBuild.ANY);

            ready = true;

            MinecraftForge.EVENT_BUS.register(new ServerEvents());
            MinecraftForge.EVENT_BUS.register(new ChatEvents());

        }catch (Exception ignored) {}
    }

    public static void stopRichPresence() {
        if(onServer) {
            client.sendRichPresence(null);

            if(updateTimer != null && !updateTimer.isCancelled()) {
                updateTimer.cancel(true);
            }
        }
    }

    public static void updateRichPresence(String state, String details, OffsetDateTime date) {
        client.sendRichPresence(new RichPresence(state, details, date, null, "wynn", null, null, null, null, 1, 1, null, null, null, false));
    }

    public static void startUpdateRegionName() {
         updateTimer = executor.scheduleAtFixedRate(() -> {
            try{
                Utils.getBossBarNames().forEach(sc -> {
                    if(!sc.contains("-") && !sc.contains("double") && !sc.contains("dungeon")) {
                        String loc = Utils.stripColor(sc).replaceAll("\\[(.*?)]", "");
                        if(!location.equalsIgnoreCase(loc)) {
                            location = loc;
                            Main.updateRichPresence("World " + Main.actualServer.replace("WC", ""), "At " + Main.location, null);
                        }
                    }
                });
            }catch (Exception ignored) { }

        }, 0, 1, TimeUnit.SECONDS);
    }

}
