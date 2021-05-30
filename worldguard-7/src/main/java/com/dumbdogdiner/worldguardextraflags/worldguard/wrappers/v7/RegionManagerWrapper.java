/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.v7;

import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.AbstractRegionManagerWrapper;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Location;

public class RegionManagerWrapper extends AbstractRegionManagerWrapper {
    public RegionManagerWrapper(RegionManager regionManager) {
        super(regionManager);
    }

    @Override
    public ApplicableRegionSet getApplicableRegions(Location location) {
        return this.regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(location));
    }
}
