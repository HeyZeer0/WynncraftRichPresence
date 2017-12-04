package net.heyzeer0.wrp.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.world.BossInfoLerping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by HeyZeer0 on 04/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class Utils {

    public static Field bossField;

    public static final char COLOR_CHAR = '\u00A7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");

    public static List<String> getBossBarNames() throws Exception {
        if(Minecraft.getMinecraft() == null || Minecraft.getMinecraft().ingameGUI == null || Minecraft.getMinecraft().ingameGUI.getBossOverlay() == null) {
            return null;
        }

        GuiBossOverlay bossOverlay = Minecraft.getMinecraft().ingameGUI.getBossOverlay();
        if(bossField == null) {
            bossField = GuiBossOverlay.class.getDeclaredField("field_184060_g");

            if(bossField == null) {
                return null;
            }

            bossField.setAccessible(true);
        }

        List<String> names = new ArrayList<>();

        if(names == null) { return null; }

        Map<UUID, BossInfoLerping> boss = (Map<UUID, BossInfoLerping>) bossField.get(bossOverlay);
        if(boss == null) { return null; }
        for (BossInfoLerping bIL : boss.values()) { names.add(bIL.getName().getFormattedText()); }

        return names;
    }

    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }

        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }


}
