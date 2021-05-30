/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.listeners;

import com.dumbdogdiner.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.dumbdogdiner.worldguardextraflags.flags.helpers.ForcedStateFlag;
import com.dumbdogdiner.worldguardextraflags.worldguard.WorldGuardUtils;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

@RequiredArgsConstructor
public class EntityListenerOnePointNine implements Listener {
    @Getter
    private final WorldGuardExtraFlagsPlugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onEntityToggleGlideEvent(EntityToggleGlideEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;

            ApplicableRegionSet regions = this.plugin.getWorldGuardCommunicator().getRegionContainer().createQuery()
                    .getApplicableRegions(player.getLocation());

            ForcedStateFlag.ForcedState state =
                    WorldGuardUtils.queryValue(player, player.getWorld(), regions.getRegions(), Flags.GLIDE);
            switch (state) {
                case ALLOW:
                    break;
                case DENY: {
                    if (!event.isGliding()) {
                        return;
                    }

                    event.setCancelled(true);

                    // Prevent the player from being allowed to glide by spamming space
                    player.teleport(player.getLocation());

                    break;
                }
                case FORCE: {
                    if (event.isGliding()) {
                        return;
                    }

                    event.setCancelled(true);

                    break;
                }
            }
        }
    }
}
