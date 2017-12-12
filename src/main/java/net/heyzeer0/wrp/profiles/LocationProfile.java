package net.heyzeer0.wrp.profiles;

import net.heyzeer0.wrp.Main;

/**
 * Created by HeyZeer0 on 11/12/2017.
 * Copyright © HeyZeer0 - 2016
 */
public class LocationProfile {

    String name;
    int startX;
    int startZ;
    int endX;
    int endZ;

    public LocationProfile(String name, int startX, int startZ, int endX, int endZ) {
        this.name = name; this.startX = startX; this.startZ = startZ; this.endX = endX; this.endZ = endZ;
    }

    public String getName() {
        return name;
    }

    public boolean insideArea(int x, int z) {
        if (startX <= x && endX >= x) {
            Main.logger.warn(startX + " < " + x + " && " + endX + " > " + z);
            if (startZ <= z && endZ >= z) {
                Main.logger.warn(startZ + " < " + x + " && " + endZ + " > " + z);
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return name + "|" + startX + "|" + startZ + "|" + endX + "|" + endZ;
    }

}
