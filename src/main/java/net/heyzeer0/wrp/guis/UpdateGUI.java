package net.heyzeer0.wrp.guis;

import net.heyzeer0.wrp.utils.Reference;
import net.heyzeer0.wrp.utils.Updater;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeyZeer0 on 11/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class UpdateGUI extends Gui {

    private Minecraft mc;
    int size = 50;
    long timeout = System.currentTimeMillis();
    boolean loaded = false;

    public UpdateGUI(Minecraft mc) {
        super();
        this.mc = mc;
    }

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post e) {
        if(e.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        if(!Updater.hasUpdate) {
            return;
        }

        //if you are reading this, this gui was retired from wynn expansion
        //thanks SCYTHE for your amazing job.

        drawRect(0, 0 - size, 203, 43 - size, -2500134);
        drawRect(0, 0 - size, 200, 40 - size, -10066329);
        drawString(mc.fontRendererObj, "§a§lWynncraft §6§lRich Presence", 5, 3 - size, -1);
        drawString(mc.fontRendererObj, "Update §av" + Updater.latestUpdate + "§f is available!", 8, 17 - size, -1);
        drawString(mc.fontRendererObj, "§7Currently using: v" + Reference.MOD_VERSION, 8, 27 - size, -1);
        if(size > 0 && !loaded) {
            size-=1;
            timeout = System.currentTimeMillis();
        }else{
            if(System.currentTimeMillis() - timeout >= 5000) {
                loaded = true;
            }
            if(loaded) {
                if(size < 50) {
                    size+=1;
                    timeout = System.currentTimeMillis();
                }
            }
        }
    }

}
