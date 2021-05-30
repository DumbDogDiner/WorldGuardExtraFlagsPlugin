/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers;

import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.SessionManager;
import com.sk89q.worldguard.session.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public abstract class AbstractSessionManagerWrapper {
    protected final SessionManager sessionManager;

    public abstract Session get(Player player);

    public abstract Session getIfPresent(Player player);

    public abstract void registerHandler(Handler.Factory<? extends Handler> factory);
}
