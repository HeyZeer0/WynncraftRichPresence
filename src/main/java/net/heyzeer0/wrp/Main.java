package net.heyzeer0.wrp;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import net.heyzeer0.wrp.events.ChatEvents;
import net.heyzeer0.wrp.events.ServerEvents;
import net.heyzeer0.wrp.profiles.LocationProfile;
import net.heyzeer0.wrp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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
    public static int locId = -1;

    public static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public static ScheduledFuture updateTimer;

    public static void startRichPresence() {
        try{
            client = new IPCClient(387266678607577088L);
            client.connect(DiscordBuild.ANY);

            ready = true;

            MinecraftForge.EVENT_BUS.register(new ServerEvents());
            MinecraftForge.EVENT_BUS.register(new ChatEvents());

            Utils.updateRegions();

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

             if(locId == -1) {
                 EntityPlayerSP pl = Minecraft.getMinecraft().player;
                 for(int i = 0; i <= Utils.locations.size(); i++) {
                     LocationProfile pf = Utils.locations.get(i);
                     if(pf.insideArea((int)pl.posX, (int)pl.posZ)) {
                         location = pf.getName();
                         locId = i;

                         Main.updateRichPresence("World " + Main.actualServer.replace("WC", ""), "At " + Main.location, null);
                         break;
                     }
                 }
             }else{
                 EntityPlayerSP pl = Minecraft.getMinecraft().player;
                 if(!Utils.locations.get(locId).insideArea((int)pl.posX, (int)pl.posZ)) {
                     for(int i = 0; i <= Utils.locations.size(); i++) {
                         LocationProfile pf = Utils.locations.get(i);
                         if(pf.insideArea((int)pl.posX, (int)pl.posZ)) {
                             location = pf.getName();
                             locId = i;

                             Main.updateRichPresence("World " + Main.actualServer.replace("WC", ""), "At " + Main.location, null);
                             break;
                         }
                     }
                 }
             }

        }, 0, 3, TimeUnit.SECONDS);
    }

}
