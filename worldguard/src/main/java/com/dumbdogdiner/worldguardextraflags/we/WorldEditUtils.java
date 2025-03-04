/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.we;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WorldEditUtils {
    private static Method legacyToLocationMethod;

    static {
        try {
            Class<?> bukkitUtilClass = Class.forName("com.sk89q.worldedit.bukkit.BukkitUtil");
            Class<?> legacyLocationClass = Class.forName("com.sk89q.worldedit.Location");

            WorldEditUtils.legacyToLocationMethod = bukkitUtilClass.getMethod("toLocation", legacyLocationClass);
        } catch (Throwable ignore) {
        }
    }

    public static org.bukkit.Location toLocation(Object location) {
        if (WorldEditUtils.legacyToLocationMethod != null) {
            try {
                return (org.bukkit.Location) WorldEditUtils.legacyToLocationMethod.invoke(null, location);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException("Unsupported WorldEdit version");
            }
        } else {
            return BukkitAdapter.adapt((Location) location);
        }
    }
}
