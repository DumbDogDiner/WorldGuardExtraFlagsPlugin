/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.handlers;

import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.HandlerWrapper;
import com.sk89q.worldguard.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FlySpeedFlagHandler extends AbstractSpeedFlagHandler {
    public static final Factory FACTORY(Plugin plugin) {
        return new Factory(plugin);
    }

    public static class Factory extends HandlerWrapper.Factory<FlySpeedFlagHandler> {
        public Factory(Plugin plugin) {
            super(plugin);
        }

        @Override
        public FlySpeedFlagHandler create(Session session) {
            return new FlySpeedFlagHandler(this.getPlugin(), session);
        }
    }

    protected FlySpeedFlagHandler(Plugin plugin, Session session) {
        super(plugin, session, Flags.FLY_SPEED);
    }

    @Override
    protected float getSpeed(Player player) {
        return player.getFlySpeed();
    }

    @Override
    protected void setSpeed(Player player, float speed) {
        player.setFlySpeed(speed);
    }
}
