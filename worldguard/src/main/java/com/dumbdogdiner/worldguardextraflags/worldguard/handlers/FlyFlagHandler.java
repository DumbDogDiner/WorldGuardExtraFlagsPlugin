/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.handlers;

import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.dumbdogdiner.worldguardextraflags.worldguard.WorldGuardUtils;
import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.HandlerWrapper;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import java.util.Set;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FlyFlagHandler extends HandlerWrapper {
    public static final Factory FACTORY(Plugin plugin) {
        return new Factory(plugin);
    }

    public static class Factory extends HandlerWrapper.Factory<FlyFlagHandler> {
        public Factory(Plugin plugin) {
            super(plugin);
        }

        @Override
        public FlyFlagHandler create(Session session) {
            return new FlyFlagHandler(this.getPlugin(), session);
        }
    }

    @Getter
    private Boolean currentValue;
    private Boolean originalFly;

    protected FlyFlagHandler(Plugin plugin, Session session) {
        super(plugin, session);
    }

    @Override
    public void initialize(Player player, Location current, ApplicableRegionSet set) {
        State state = WorldGuardUtils.queryState(player, current.getWorld(), set.getRegions(), Flags.FLY);
        this.handleValue(player, state);
    }

    @Override
    public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet,
            Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        State state = WorldGuardUtils.queryState(player, to.getWorld(), toSet.getRegions(), Flags.FLY);
        this.handleValue(player, state);

        return true;
    }

    private void handleValue(Player player, State state) {
        if (state != null) {
            boolean value = (state == State.ALLOW ? true : false);

            if (player.getAllowFlight() != value) {
                if (this.originalFly == null) {
                    this.originalFly = player.getAllowFlight();
                }

                player.setAllowFlight(value);
            }
        } else {
            if (this.originalFly != null) {
                player.setAllowFlight(this.originalFly);

                this.originalFly = null;
            }
        }
    }
}
