package net.heyzeer0.wrp.config;

import net.heyzeer0.wrp.Main;
import net.heyzeer0.wrp.utils.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


/**
 * Created by HeyZeer0 on 12/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class ConfigManager {

    private static Configuration config;

    public static boolean enteringNotifier = true;

    public static void registerConfig(FMLPreInitializationEvent e) {
        config = new Configuration(e.getSuggestedConfigurationFile());

        enteringNotifier = config.getBoolean("enteringNotifier", "main", true, "You are now entering overlay");

        config.save();

        MinecraftForge.EVENT_BUS.register(new ConfigManager());
    }

    public static Configuration getConfig() {
        return config;
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            config.save();
            enteringNotifier = config.getBoolean("enteringNotifier", "main", true, "You are now entering overlay");
        }
    }

}
