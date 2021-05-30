/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.listeners;

import com.dumbdogdiner.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.dumbdogdiner.worldguardextraflags.utils.SupportedFeatures;
import com.dumbdogdiner.worldguardextraflags.worldguard.WorldGuardUtils;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

@RequiredArgsConstructor
public class BlockListener implements Listener {
    @Getter
    private final WorldGuardExtraFlagsPlugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onEntityBlockFormEvent(EntityBlockFormEvent event) {
        if (SupportedFeatures.isFrostwalkerSupported()) {
            BlockState newState = event.getNewState();
            if (newState.getType() == Material.FROSTED_ICE) {
                ApplicableRegionSet regions = this.plugin.getWorldGuardCommunicator().getRegionContainer().createQuery()
                        .getApplicableRegions(newState.getLocation());

                Entity entity = event.getEntity();
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    if (WorldGuardUtils.queryValue(player, player.getWorld(), regions.getRegions(),
                            Flags.FROSTWALKER) == State.DENY) {
                        event.setCancelled(true);
                    }
                } else {
                    if (regions.queryValue(null, Flags.FROSTWALKER) == State.DENY) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
