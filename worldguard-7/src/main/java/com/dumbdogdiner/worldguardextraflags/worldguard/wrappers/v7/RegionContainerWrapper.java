/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.v7;

import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.AbstractRegionContainerWrapper;
import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.AbstractRegionManagerWrapper;
import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.AbstractRegionQueryWrapper;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.World;

public class RegionContainerWrapper extends AbstractRegionContainerWrapper {
    @Override
    public AbstractRegionQueryWrapper createQuery() {
        return new RegionQueryWrapper(WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery());
    }

    @Override
    public AbstractRegionManagerWrapper get(World world) {
        return new RegionManagerWrapper(
                WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world)));
    }

    @Override
    public List<AbstractRegionManagerWrapper> getLoaded() {
        return WorldGuard.getInstance().getPlatform().getRegionContainer().getLoaded().stream()
                .map((m) -> new RegionManagerWrapper(m)).collect(Collectors.toList());
    }
}
