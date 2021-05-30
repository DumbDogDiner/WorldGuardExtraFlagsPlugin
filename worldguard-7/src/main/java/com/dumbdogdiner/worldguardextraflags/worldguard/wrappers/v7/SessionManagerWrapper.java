/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.v7;

import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.AbstractSessionManagerWrapper;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.SessionManager;
import com.sk89q.worldguard.session.handler.Handler;
import com.sk89q.worldguard.session.handler.Handler.Factory;
import org.bukkit.entity.Player;

public class SessionManagerWrapper extends AbstractSessionManagerWrapper {
    public SessionManagerWrapper(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    public Session get(Player player) {
        return this.sessionManager.get(WorldGuardPlugin.inst().wrapPlayer(player));
    }

    @Override
    public Session getIfPresent(Player player) {
        return this.sessionManager.getIfPresent(WorldGuardPlugin.inst().wrapPlayer(player));
    }

    @Override
    public void registerHandler(Factory<? extends Handler> factory) {
        this.sessionManager.registerHandler(factory, null);
    }
}
