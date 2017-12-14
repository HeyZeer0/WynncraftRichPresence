package net.heyzeer0.wrp.events;

import net.heyzeer0.wrp.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class ServerEvents {

    @SubscribeEvent
    @SideOnly(value = Side.CLIENT)
    public void onServerJoin(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        if(!Main.getRichPresence().isReady()) {
            return;
        }

        if(e.isLocal()) {
            return;
        }

        ServerData server = Minecraft.getMinecraft().getCurrentServerData();
        if(server == null || server.serverIP == null) {
            return;
        }
        if(!server.serverIP.equalsIgnoreCase("play.wynncraft.com")) {
            return;
        }

        Main.getRichPresence().updateRichPresence("At Lobby", null, null);
        Main.getData().setOnServer(true);
    }

    @SubscribeEvent
    @SideOnly(value = Side.CLIENT)
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        if(Main.getData().onServer()) {
            Main.getRichPresence().stopRichPresence();
            Main.getData().setOnServer(false);

            if(ChatEvents.updateTimer != null && !ChatEvents.updateTimer.isCancelled()) {
                ChatEvents.updateTimer.cancel(true);
            }
        }
    }

}
