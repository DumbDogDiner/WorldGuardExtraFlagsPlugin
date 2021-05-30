/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.handlers;

import com.dumbdogdiner.worldguardextraflags.worldguard.WorldGuardUtils;
import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.HandlerWrapper;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DoubleFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class AbstractSpeedFlagHandler extends HandlerWrapper {

    private Float originalSpeed;
    private DoubleFlag flag;

    protected AbstractSpeedFlagHandler(Plugin plugin, Session session, DoubleFlag flag) {
        super(plugin, session);

        this.flag = flag;
    }

    protected abstract float getSpeed(Player player);

    protected abstract void setSpeed(Player player, float speed);

    @Override
    public void initialize(Player player, Location current, ApplicableRegionSet set) {
        Double speed = WorldGuardUtils.queryValue(player, current.getWorld(), set.getRegions(), this.flag);
        this.handleValue(player, speed);
    }

    @Override
    public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet,
            Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType) {
        Double speed = WorldGuardUtils.queryValue(player, to.getWorld(), toSet.getRegions(), this.flag);
        this.handleValue(player, speed);

        return true;
    }

    private void handleValue(Player player, Double speed) {
        if (speed != null) {
            if (speed > 1.0) {
                speed = 1.0;
            } else if (speed < -1.0) {
                speed = -1.0;
            }

            if (this.getSpeed(player) != speed.floatValue()) {
                if (this.originalSpeed == null) {
                    this.originalSpeed = this.getSpeed(player);
                }

                this.setSpeed(player, speed.floatValue());
            }
        } else {
            if (this.originalSpeed != null) {
                this.setSpeed(player, this.originalSpeed);

                this.originalSpeed = null;
            }
        }
    }
}
