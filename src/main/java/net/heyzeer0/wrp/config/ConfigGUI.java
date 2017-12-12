package net.heyzeer0.wrp.config;

import net.heyzeer0.wrp.utils.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Created by HeyZeer0 on 12/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class ConfigGUI extends GuiConfig {

    public ConfigGUI(GuiScreen parentScreen) {
        super(parentScreen, new ConfigElement(ConfigManager.getConfig().getCategory("main")).getChildElements(), Reference.MOD_ID, false, false, "WynncraftRichPresence Configurations");
    }

}
