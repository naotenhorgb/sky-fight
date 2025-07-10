package com.naotenhorgb.skyFight.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class YamlConverter {

    public Location stringToLocation(String string) {
        List<String> parts = Arrays.asList(string.split(":"));
        if (parts.size() < 6) {
            Bukkit.getLogger().warning("[SkyFight] Invalid location string (too short): " + string);
            return null;
        }

        World world = Bukkit.getWorld(parts.get(0));
        if (world == null) {
            Bukkit.getLogger().warning("[SkyFight] World not found: " + parts.get(0));
            return null;
        }

        try {
            double x = Double.parseDouble(parts.get(1));
            double y = Double.parseDouble(parts.get(2));
            double z = Double.parseDouble(parts.get(3));
            float yaw = Float.parseFloat(parts.get(4));
            float pitch = Float.parseFloat(parts.get(5));
            return new Location(world, x, y, z, yaw, pitch);
        } catch (NumberFormatException e) {
            Bukkit.getLogger().warning("[SkyFight] Invalid number format in location string: " + string);
            return null;
        }
    }



    public Cuboid stringsToCuboid(List<String> list) {
        if (list == null || list.size() < 2) return null;
        Location loc1 = stringToLocation(list.get(0));
        Location loc2 = stringToLocation(list.get(1));
        return new Cuboid(loc1, loc2);
    }


    public String locationToString(Location location){
        return location.getWorld().getName() + ":"
                + location.getX() + ":"
                + location.getY() + ":"
                + location.getZ() + ":"
                + location.getYaw() + ":"
                + location.getPitch();
    }

    public List<String> cuboidToString(Cuboid cuboid) {
        List<String> parts = new ArrayList<>();
        parts.add(locationToString(cuboid.getPoint1()));
        parts.add(locationToString(cuboid.getPoint2()));
        return parts;
    }


}
