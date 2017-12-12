package net.heyzeer0.wrp.guis;

import net.heyzeer0.wrp.Main;
import net.heyzeer0.wrp.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

/**
 * Created by HeyZeer0 on 12/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class LocationGUI extends Gui {

    public static boolean newArea = true;

    private Minecraft mc;
    int size = 50;
    long timeout = System.currentTimeMillis();
    String last_loc = "Waiting";

    boolean showing = false;
    boolean animation = false;

    public LocationGUI(Minecraft mc) {
        super();
        this.mc = mc;
    }

    @SubscribeEvent(priority= EventPriority.NORMAL)
    public void onRenderExperienceBar(RenderGameOverlayEvent.Post event) {
        if(!ConfigManager.enteringNotifier /*|| !Main.onServer*/) {
            return;
        }
        if(event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }

        if(last_loc.equals(Main.location) && !showing) {
            return;
        }

        String loc = Main.location;
        last_loc = loc;

        showing = true;

        if(loc.length() >= 15) {
            loc = loc.substring(0, loc.length() - (loc.length() - 15));
            loc = loc + "...";
        }

        drawRect(0, 0 - size, 143, 43 - size, -2500134);
        drawRect(0, 0 - size, 140, 40 - size, -10066329);

        drawString(mc.fontRendererObj, "§a§lYou are now entering", 5, 5 - size, -1);
        GL11.glScalef(1.5f, 1.5f, 1.5f);
        drawString(mc.fontRendererObj, "§e" + loc, 7,13 - size, 13782543);
        GL11.glScalef(1, 1, 1);

        if(size > 0 && !animation) {
            size-=1;
            timeout = System.currentTimeMillis();
        }else{
            if(System.currentTimeMillis() - timeout >= 3000) {
                animation = true;
            }
            if(animation) {
                if(size < 50) {
                    size+=1;
                    timeout = System.currentTimeMillis();
                }
                if(size >= 50) {
                    showing = false;
                    animation = false;
                }
            }
        }

    }

}
