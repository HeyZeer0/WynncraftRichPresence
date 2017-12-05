package net.heyzeer0.wrp.events;

import net.heyzeer0.wrp.Main;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
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

    @SubscribeEvent
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
    }


}