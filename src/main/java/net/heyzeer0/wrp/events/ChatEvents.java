package net.heyzeer0.wrp.events;

import net.heyzeer0.wrp.Main;
import net.heyzeer0.wrp.config.ConfigValues;
import net.heyzeer0.wrp.guis.overlay.LocationGUI;
import net.heyzeer0.wrp.profiles.LocationProfile;
import net.heyzeer0.wrp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class ChatEvents {

    public static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    public static ScheduledFuture updateTimer;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @SideOnly(value = Side.CLIENT)
    public void onChatReceive(ClientChatReceivedEvent e) {
        if(e.getMessage().getFormattedText().toLowerCase().contains("loading the wynnpack...")) {

            new Thread(() -> {
                try{
                    InputStream st = new URL("https://api.wynncraft.com/public_api.php?action=playerStats&command=" + Minecraft.getMinecraft().player.getName()).openStream();
                    Main.getData().setActualServer(new JSONObject(IOUtils.toString(st)).getString("current_server"));

                    Main.getRichPresence().updateRichPresence("World " + Main.getData().getActualServer().replace("WC", ""), "At " + Main.getData().getLocation(), null);
                    Main.getData().setOnServer(true);

                    startUpdateRegionName();

                }catch (Exception ex) { Main.logger.warn("Cannot update status", ex); }
            }).start();

            return;
        }
        if(e.getMessage().getFormattedText().toLowerCase().contains("you are now entering") && !e.getMessage().getFormattedText().contains("/")) {
            if(ConfigValues.enteringNotifier) {
                String loc = e.getMessage().getFormattedText();
                LocationGUI.location = Utils.stripColor(loc.replace("[You are now entering ", "").replace("]", ""));
                e.setCanceled(true);
            }
            return;
        }
        if(e.getMessage().getFormattedText().toLowerCase().contains("you are now leaving") && !e.getMessage().getFormattedText().contains("/")) {
            if(ConfigValues.enteringNotifier) {
                LocationGUI.last_loc = "Waiting";
                LocationGUI.location = "Waiting";
                e.setCanceled(true);
            }
            return;
        }
    }

    public static void startUpdateRegionName() {
        updateTimer = executor.scheduleAtFixedRate(() -> {
            if(Main.getData().getLocId() == -1) {
                EntityPlayerSP pl = Minecraft.getMinecraft().player;
                for(int i = 0; i < Utils.locations.size(); i++) {
                    LocationProfile pf = Utils.locations.get(i);
                    if(pf.insideArea((int)pl.posX, (int)pl.posZ)) {
                        Main.getData().setLocation(pf.getName());
                        Main.getData().setLocId(i);

                        Main.getRichPresence().updateRichPresence("World " + Main.getData().getActualServer().replace("WC", ""), "At " + Main.getData().getLocation(), null);
                        break;
                    }
                }
            }else{
                EntityPlayerSP pl = Minecraft.getMinecraft().player;
                if(!Utils.locations.get(Main.getData().getLocId()).insideArea((int)pl.posX, (int)pl.posZ)) {
                    for(int i = 0; i < Utils.locations.size(); i++) {
                        LocationProfile pf = Utils.locations.get(i);
                        if(pf.insideArea((int)pl.posX, (int)pl.posZ)) {
                            Main.getData().setLocation(pf.getName());
                            Main.getData().setLocId(i);

                            Main.getRichPresence().updateRichPresence("World " + Main.getData().getActualServer().replace("WC", ""), "At " + Main.getData().getLocation(), null);
                            break;
                        }
                    }
                }
            }

        }, 0, 3, TimeUnit.SECONDS);
    }


}
