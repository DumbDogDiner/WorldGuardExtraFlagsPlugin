/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.flags.helpers;

import com.sk89q.worldguard.protection.flags.FlagContext;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import org.bukkit.Material;

public class BlockMaterialFlag extends MaterialFlag {
    public BlockMaterialFlag(String name) {
        super(name);
    }

    @Override
    public Material parseInput(FlagContext context) throws InvalidFlagFormat {
        Material material = super.parseInput(context);
        if (!material.isBlock()) {
            throw new InvalidFlagFormat("This material isn't seen as 'placable block', use alternative id");
        }

        return material;
    }
}
