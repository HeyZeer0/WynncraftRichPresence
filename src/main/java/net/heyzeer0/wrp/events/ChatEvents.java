package net.heyzeer0.wrp.events;

import net.heyzeer0.wrp.Main;
import net.heyzeer0.wrp.config.ConfigManager;
import net.heyzeer0.wrp.guis.LocationGUI;
import net.heyzeer0.wrp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class ChatEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @SideOnly(value = Side.CLIENT)
    public void onChatReceive(ClientChatReceivedEvent e) {
        if(e.getMessage().getFormattedText().toLowerCase().contains("loading the wynnpack...")) {

            new Thread(() -> {
                try{
                    InputStream st = new URL("https://api.wynncraft.com/public_api.php?action=playerStats&command=" + Minecraft.getMinecraft().player.getName()).openStream();
                    Main.actualServer = new JSONObject(IOUtils.toString(st)).getString("current_server");

                    Main.updateRichPresence("World " + Main.actualServer.replace("WC", ""), "At " + Main.location, null);
                    Main.onServer = true;

                    Main.startUpdateRegionName();

                }catch (Exception ex) { Main.logger.warn("Cannot update status", ex); }
            }).start();

            return;
        }
        if(e.getMessage().getFormattedText().toLowerCase().contains("you are now entering") && !e.getMessage().getFormattedText().contains("/")) {
            if(ConfigManager.enteringNotifier) {
                String loc = e.getMessage().getFormattedText();
                LocationGUI.location = Utils.stripColor(loc.replace("[You are now entering ", "").replace("]", ""));
                e.setCanceled(true);
            }
            return;
        }
        if(e.getMessage().getFormattedText().toLowerCase().contains("you are now leaving") && !e.getMessage().getFormattedText().contains("/")) {
            if(ConfigManager.enteringNotifier) {
                LocationGUI.last_loc = "Waiting";
                LocationGUI.location = "Waiting";
                e.setCanceled(true);
            }
            return;
        }
    }


}
