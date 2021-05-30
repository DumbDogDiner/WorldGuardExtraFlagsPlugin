/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.listeners;

import com.dumbdogdiner.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.event.extent.EditSessionEvent;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.util.eventbus.EventHandler;
import com.sk89q.worldedit.util.eventbus.Subscribe;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorldEditListener {
    private final WorldGuardExtraFlagsPlugin plugin;

    @Subscribe(priority = EventHandler.Priority.VERY_EARLY)
    public void onEditSessionEvent(EditSessionEvent event) {
        Actor actor = event.getActor();
        if (actor != null && actor.isPlayer()) {
            event.setExtent(this.plugin.getWorldGuardCommunicator().getWorldEditFlag(event.getWorld(),
                    event.getExtent(), (Player) actor));
        }
    }
}
