/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.v7;

import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.dumbdogdiner.worldguardextraflags.worldguard.WorldGuardUtils;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import org.bukkit.Bukkit;

public class WorldEditFlagHandler extends AbstractDelegateExtent {
    protected final World weWorld;

    protected final org.bukkit.World world;
    protected final org.bukkit.entity.Player player;

    public WorldEditFlagHandler(World world, Extent extent, Player player) {
        super(extent);

        this.weWorld = world;

        if (world instanceof BukkitWorld) {
            this.world = ((BukkitWorld) world).getWorld();
        } else {
            this.world = Bukkit.getWorld(world.getName());
        }

        this.player = Bukkit.getPlayer(player.getUniqueId());
    }

    @Override
    public boolean setBlock(BlockVector3 location, BlockStateHolder block) throws WorldEditException {
        ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform().getRegionContainer().get(this.weWorld)
                .getApplicableRegions(location);

        State state = WorldGuardUtils.queryState(this.player, this.world, regions.getRegions(), Flags.WORLDEDIT);
        if (state != State.DENY) {
            return super.setBlock(location, block);
        }

        return false;
    }
}
