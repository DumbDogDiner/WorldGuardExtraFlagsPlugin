/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.v7;

import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.AbstractRegionQueryWrapper;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

@RequiredArgsConstructor
public class RegionQueryWrapper extends AbstractRegionQueryWrapper {
    private final RegionQuery regionQuery;

    @Override
    public ApplicableRegionSet getApplicableRegions(Location location) {
        return this.regionQuery.getApplicableRegions(BukkitAdapter.adapt(location));
    }
}
