package net.heyzeer0.wrp.guis.overlay;

import net.heyzeer0.wrp.Main;
import net.heyzeer0.wrp.guis.WRPGui;
import net.heyzeer0.wrp.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by HeyZeer0 on 11/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class UpdateGUI extends WRPGui {

    int size = 50;
    long timeout = System.currentTimeMillis();
    boolean loaded = false;

    public UpdateGUI(Minecraft mc) {
        super(mc);
    }

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post e) {
        if(e.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        if(!Main.getUpdater().hasUpdate()) {
            return;
        }

        //if you are reading this, this gui was retired from wynn expansion
        //thanks SCYTHE for your amazing job.

        drawRect(0, 0 - size, 203, 43 - size, -2500134);
        drawRect(0, 0 - size, 200, 40 - size, -10066329);
        drawString("§a§lWynncraft §6§lRich Presence", 5, 3 - size, -1);
        drawString("Update §av" + Main.getUpdater().getLatestUpdate() + "§f is available!", 8, 17 - size, -1);
        drawString("§7Currently using: v" + Reference.MOD_VERSION, 8, 27 - size, -1);
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
