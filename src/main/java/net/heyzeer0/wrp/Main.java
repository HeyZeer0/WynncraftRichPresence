package net.heyzeer0.wrp;

import com.jagrosh.discordipc.entities.DiscordBuild;
import net.heyzeer0.wrp.events.ChatEvents;
import net.heyzeer0.wrp.events.ServerEvents;
import net.heyzeer0.wrp.guis.overlay.LocationGUI;
import net.heyzeer0.wrp.guis.overlay.UpdateGUI;
import net.heyzeer0.wrp.profiles.DataProfile;
import net.heyzeer0.wrp.profiles.RichProfile;
import net.heyzeer0.wrp.profiles.UpdateProfile;
import net.heyzeer0.wrp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class Main {

    private static RichProfile richPresence;
    private static DataProfile modData = new DataProfile();
    private static UpdateProfile updater = new UpdateProfile();

    public static Logger logger;

    public static void startRichPresence() {
        try{
            richPresence = new RichProfile(387266678607577088L, DiscordBuild.ANY);

            //events
            MinecraftForge.EVENT_BUS.register(new ServerEvents());
            MinecraftForge.EVENT_BUS.register(new ChatEvents());

            //guis
            MinecraftForge.EVENT_BUS.register(new UpdateGUI(Minecraft.getMinecraft()));
            MinecraftForge.EVENT_BUS.register(new LocationGUI(Minecraft.getMinecraft()));

            Utils.updateRegions();
        }catch (Exception ignored) {}
    }

    public static RichProfile getRichPresence() {
        return richPresence;
    }

    public static DataProfile getData() {
        return modData;
    }

    public static UpdateProfile getUpdater() {
        return updater;
    }

}
