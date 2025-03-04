/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;

/**
 * Helper class to decide what features are supported by the server
 */
@SuppressWarnings("deprecation")
public class SupportedFeatures {
    @Getter
    private static boolean frostwalkerSupported;
    @Getter
    private static boolean stopSoundSupported;
    @Getter
    private static boolean potionEffectEventSupported;
    @Getter
    private static boolean potionEffectParticles;
    @Getter
    private static boolean newMaterial;

    static {
        try {
            SupportedFeatures.frostwalkerSupported = Material.FROSTED_ICE != null;
        } catch (Throwable ignored) {
        }

        try {
            SupportedFeatures.stopSoundSupported = Player.class.getDeclaredMethod("stopSound", Sound.class) != null;
        } catch (Throwable ignored) {
        }

        try {
            SupportedFeatures.potionEffectEventSupported = EntityPotionEffectEvent.class != null;
        } catch (Throwable ignored) {
        }

        try {
            SupportedFeatures.potionEffectParticles = PotionEffect.class.getDeclaredMethod("hasParticles") != null;
        } catch (Throwable ignored) {

        }

        try {
            SupportedFeatures.newMaterial = Material.LEGACY_AIR != null;
        } catch (Throwable ignored) {

        }
    }
}
