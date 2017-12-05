package net.heyzeer0.wrp.events;

import net.heyzeer0.wrp.Main;
import net.heyzeer0.wrp.utils.Updater;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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
        if(!Main.ready) {
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

        Main.updateRichPresence("At Lobby", null, null);
        Main.onServer = true;
    }

    @SubscribeEvent
    @SideOnly(value = Side.CLIENT)
    public void entityJoin(EntityJoinWorldEvent e) {
        if(Updater.hasUpdate && !Updater.sended && e.getEntity() == Minecraft.getMinecraft().player) {
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(" "));
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§a§lWynncraft §6§lRich Presence"));
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§7A new version is §aavailable §e" + Updater.latestUpdate));
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString(" "));

            Updater.sended = true;
        }
    }

    @SubscribeEvent
    @SideOnly(value = Side.CLIENT)
    public void onServerLeave(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        Main.stopRichPresence();
        Main.onServer = false;
    }



}
