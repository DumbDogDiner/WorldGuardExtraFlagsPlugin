/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.handlers;

import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.dumbdogdiner.worldguardextraflags.we.WorldEditUtils;
import com.dumbdogdiner.worldguardextraflags.worldguard.WorldGuardUtils;
import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.HandlerWrapper;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TeleportOnExitFlagHandler extends HandlerWrapper {
    public static final Factory FACTORY(Plugin plugin) {
        return new Factory(plugin);
    }

    public static class Factory extends HandlerWrapper.Factory<TeleportOnExitFlagHandler> {
        public Factory(Plugin plugin) {
            super(plugin);
        }

        @Override
        public TeleportOnExitFlagHandler create(Session session) {
            return new TeleportOnExitFlagHandler(this.getPlugin(), session);
        }
    }

    protected TeleportOnExitFlagHandler(Plugin plugin, Session session) {
        super(plugin, session);
    }

    @Override
    public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet,
            Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        Object location = WorldGuardUtils.queryValueUnchecked(player, to.getWorld(), exited, Flags.TELEPORT_ON_EXIT);
        if (location != null && WorldGuardUtils.hasNoTeleportLoop(this.getPlugin(), player, location)) {
            player.teleport(WorldEditUtils.toLocation(location));
        }

        return true;
    }
}
