/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.listeners;

import com.dumbdogdiner.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

@RequiredArgsConstructor
public class WorldListener implements Listener {
    @Getter
    private final WorldGuardExtraFlagsPlugin plugin;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldLoadEvent(WorldLoadEvent event) {
        World world = event.getWorld();

        this.plugin.getWorldGuardCommunicator().doUnloadChunkFlagCheck(world);
    }
}
