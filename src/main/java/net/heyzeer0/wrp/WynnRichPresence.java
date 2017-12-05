package net.heyzeer0.wrp;

import net.heyzeer0.wrp.utils.Reference;
import net.heyzeer0.wrp.utils.Updater;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */

@Mod(name = "WynncraftRichPresence", modid = "wynnrp", clientSideOnly = true, acceptedMinecraftVersions = "[1.10.2,1.11.2]", version = Reference.MOD_VERSION)
public class WynnRichPresence {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Main.logger = event.getModLog();
        Main.startRichPresence();
        Updater.checkForUpdates();
    }

}
