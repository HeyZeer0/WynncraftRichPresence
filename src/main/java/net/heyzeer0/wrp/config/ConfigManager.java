package net.heyzeer0.wrp.config;

import net.heyzeer0.wrp.utils.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;


/**
 * Created by HeyZeer0 on 12/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class ConfigManager {

    private static Configuration config;

    public static void registerConfig(FMLPreInitializationEvent e) {
        config = new Configuration(e.getSuggestedConfigurationFile());

        updateConfig();

        MinecraftForge.EVENT_BUS.register(new ConfigManager());
    }

    public static void updateConfig() {
        try{
            for(Field f : ConfigValues.class.getFields()) {
                if(f.get(null).getClass() == boolean.class) {
                    f.set(null, config.get("main", f.getName(), f.getBoolean(null)));
                }else if(f.get(null).getClass() == String.class) {
                    f.set(null, config.get("main", f.getName(), String.valueOf(f.get(null))));
                }
            }
        }catch (Exception ex) { }

        if(config.hasChanged()) {
            config.save();
        }
    }

    public static Configuration getConfig() {
        return config;
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            updateConfig();
        }
    }

}
