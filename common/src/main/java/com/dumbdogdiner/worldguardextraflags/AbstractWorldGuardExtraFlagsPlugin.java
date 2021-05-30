/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags;

import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.WorldGuardCommunicator;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractWorldGuardExtraFlagsPlugin extends JavaPlugin {
    @Getter
    protected WorldGuardCommunicator worldGuardCommunicator;
}
