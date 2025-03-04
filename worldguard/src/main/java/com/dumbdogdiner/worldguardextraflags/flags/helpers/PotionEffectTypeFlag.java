/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.flags.helpers;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.FlagContext;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectTypeFlag extends Flag<PotionEffectType> {
    public PotionEffectTypeFlag(String name) {
        super(name);
    }

    @Override
    public Object marshal(PotionEffectType o) {
        return o.getName();
    }

    @Override
    public PotionEffectType parseInput(FlagContext context) throws InvalidFlagFormat {
        PotionEffectType potionEffect = PotionEffectType.getByName(context.getUserInput().trim());
        if (potionEffect != null) {
            return potionEffect;
        } else {
            throw new InvalidFlagFormat(
                    "Unable to find the potion effect type! Please refer to https://hub.spigotmc.org/javadocs/spigot/org/bukkit/potion/PotionEffectType.html");
        }
    }

    @Override
    public PotionEffectType unmarshal(Object o) {
        return PotionEffectType.getByName(o.toString());
    }
}
