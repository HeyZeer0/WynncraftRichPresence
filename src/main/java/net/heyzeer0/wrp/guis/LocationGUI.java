package net.heyzeer0.wrp.guis;

import net.heyzeer0.wrp.Main;
import net.heyzeer0.wrp.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by HeyZeer0 on 12/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class LocationGUI extends Gui {

    private Minecraft mc;

    public LocationGUI(Minecraft mc) {
        super();
        this.mc = mc;
    }

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        if(!ConfigManager.useOverlay) {
            return;
        }
        if(event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        if(Main.onServer && !Main.location.equalsIgnoreCase("Waiting")) {
            drawString(mc.fontRendererObj, "§a§l" + Main.location, 10, 10, -1);
        }
    }

}
