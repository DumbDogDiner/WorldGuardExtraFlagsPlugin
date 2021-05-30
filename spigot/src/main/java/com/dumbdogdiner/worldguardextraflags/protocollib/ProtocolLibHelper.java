/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.protocollib;

import com.comphenix.protocol.ProtocolLibrary;
import com.dumbdogdiner.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

public class ProtocolLibHelper {
    @Getter
    private final WorldGuardExtraFlagsPlugin plugin;
    @Getter
    private final Plugin protocolLibPlugin;

    public ProtocolLibHelper(WorldGuardExtraFlagsPlugin plugin, Plugin protocolLibPlugin) {
        this.plugin = plugin;
        this.protocolLibPlugin = protocolLibPlugin;
    }

    public void onEnable() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new RemoveEffectPacketListener());
    }
}
