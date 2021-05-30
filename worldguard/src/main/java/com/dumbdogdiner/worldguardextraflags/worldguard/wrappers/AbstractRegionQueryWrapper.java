/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import org.bukkit.Location;

public abstract class AbstractRegionQueryWrapper {
    public abstract ApplicableRegionSet getApplicableRegions(Location location);
}
