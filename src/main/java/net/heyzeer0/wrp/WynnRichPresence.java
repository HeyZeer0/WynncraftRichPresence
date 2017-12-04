package net.heyzeer0.wrp;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */

@Mod(name = "WynncraftRichPresence", modid = "wynnrp", clientSideOnly = true, acceptedMinecraftVersions = "[1.10.2,1.11.2]")
public class WynnRichPresence {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Main.logger = event.getModLog();
        Main.config = new Configuration(event.getSuggestedConfigurationFile());
        Main.startRichPresence();
    }



}
