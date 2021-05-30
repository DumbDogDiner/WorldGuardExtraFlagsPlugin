/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers;

import java.util.List;
import org.bukkit.World;

public abstract class AbstractRegionContainerWrapper {
    public abstract AbstractRegionQueryWrapper createQuery();

    public abstract AbstractRegionManagerWrapper get(World world);

    public abstract List<AbstractRegionManagerWrapper> getLoaded();
}
