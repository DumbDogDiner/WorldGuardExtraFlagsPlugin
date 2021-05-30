/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.handlers;

import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.dumbdogdiner.worldguardextraflags.flags.data.PotionEffectDetails;
import com.dumbdogdiner.worldguardextraflags.utils.SupportedFeatures;
import com.dumbdogdiner.worldguardextraflags.worldguard.WorldGuardUtils;
import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.HandlerWrapper;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlockedEffectsFlagHandler extends HandlerWrapper {
    public static Factory FACTORY(Plugin plugin) {
        return new Factory(plugin);
    }

    public static class Factory extends HandlerWrapper.Factory<BlockedEffectsFlagHandler> {
        public Factory(Plugin plugin) {
            super(plugin);
        }

        @Override
        public BlockedEffectsFlagHandler create(Session session) {
            return new BlockedEffectsFlagHandler(this.getPlugin(), session);
        }
    }

    private final HashMap<PotionEffectType, PotionEffectDetails> removedEffects;

    protected BlockedEffectsFlagHandler(Plugin plugin, Session session) {
        super(plugin, session);

        this.removedEffects = new HashMap<>();
    }

    @Override
    public void initialize(Player player, Location current, ApplicableRegionSet set) {
        this.check(player, set);
    }

    @Override
    public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet,
            Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        this.check(player, toSet);

        return true;
    }

    @Override
    public void tick(Player player, ApplicableRegionSet set) {
        this.check(player, set);
    }

    private void check(Player player, ApplicableRegionSet set) {
        Set<PotionEffectType> potionEffects =
                WorldGuardUtils.queryValue(player, player.getWorld(), set.getRegions(), Flags.BLOCKED_EFFECTS);
        if (potionEffects != null && potionEffects.size() > 0) {
            for (PotionEffectType effectType : potionEffects) {
                PotionEffect effect = null;
                for (PotionEffect activeEffect : player.getActivePotionEffects()) {
                    if (activeEffect.getType().equals(effectType)) {
                        effect = activeEffect;
                        break;
                    }
                }

                if (effect != null) {
                    this.removedEffects.put(effect.getType(),
                            new PotionEffectDetails(
                                    System.nanoTime()
                                            + (long) (effect.getDuration() / 20D * TimeUnit.SECONDS.toNanos(1L)),
                                    effect.getAmplifier(), effect.isAmbient(),
                                    SupportedFeatures.isPotionEffectParticles() ? effect.hasParticles() : true));

                    player.removePotionEffect(effectType);
                }
            }
        }

        Iterator<Entry<PotionEffectType, PotionEffectDetails>> potionEffects_ =
                this.removedEffects.entrySet().iterator();
        while (potionEffects_.hasNext()) {
            Entry<PotionEffectType, PotionEffectDetails> potionEffect = potionEffects_.next();

            if (potionEffects == null || !potionEffects.contains(potionEffect.getKey())) {
                PotionEffectDetails removedEffect = potionEffect.getValue();
                if (removedEffect != null) {
                    int timeLeft = removedEffect.getTimeLeftInTicks();
                    if (timeLeft > 0) {
                        if (SupportedFeatures.isPotionEffectParticles()) {
                            player.addPotionEffect(
                                    new PotionEffect(potionEffect.getKey(), timeLeft, removedEffect.getAmplifier(),
                                            removedEffect.isAmbient(), removedEffect.isParticles()),
                                    true);
                        } else {
                            player.addPotionEffect(new PotionEffect(potionEffect.getKey(), timeLeft,
                                    removedEffect.getAmplifier(), removedEffect.isAmbient()), true);
                        }
                    }
                }

                potionEffects_.remove();
            }
        }
    }
}
