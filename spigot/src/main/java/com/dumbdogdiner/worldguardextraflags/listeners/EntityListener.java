/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.listeners;

import com.dumbdogdiner.worldguardextraflags.AbstractWorldGuardExtraFlagsPlugin;
import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

@RequiredArgsConstructor
public class EntityListener implements Listener {
    @Getter
    private final AbstractWorldGuardExtraFlagsPlugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onPortalCreateEvent(PortalCreateEvent event) {
        for (BlockState block : event.getBlocks()) {
            ApplicableRegionSet regions = this.plugin.getWorldGuardCommunicator().getRegionContainer().createQuery()
                    .getApplicableRegions(block.getLocation());
            if (regions.queryValue(null, Flags.NETHER_PORTALS) == State.DENY) {
                event.setCancelled(true);
                break;
            }
        }
    }
}
