/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers;

import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.BlockedEffectsFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.CommandOnEntryFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.CommandOnExitFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.ConsoleCommandOnEntryFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.ConsoleCommandOnExitFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.FlyFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.FlySpeedFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.GiveEffectsFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.GlideFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.GodmodeFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.PlaySoundsFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.TeleportOnEntryFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.TeleportOnExitFlagHandler;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.WalkSpeedFlagHandler;
import com.sk89q.worldedit.extent.AbstractDelegateExtent;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.SetFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface WorldGuardCommunicator {
    default public void onLoad(Plugin plugin) throws Exception {
        FlagRegistry flagRegistry = this.getFlagRegistry();
        flagRegistry.register(Flags.TELEPORT_ON_ENTRY);
        flagRegistry.register(Flags.TELEPORT_ON_EXIT);
        flagRegistry.register(Flags.COMMAND_ON_ENTRY);
        flagRegistry.register(Flags.COMMAND_ON_EXIT);
        flagRegistry.register(Flags.CONSOLE_COMMAND_ON_ENTRY);
        flagRegistry.register(Flags.CONSOLE_COMMAND_ON_EXIT);
        flagRegistry.register(Flags.WALK_SPEED);
        flagRegistry.register(Flags.KEEP_INVENTORY);
        flagRegistry.register(Flags.KEEP_EXP);
        flagRegistry.register(Flags.CHAT_PREFIX);
        flagRegistry.register(Flags.CHAT_SUFFIX);
        flagRegistry.register(Flags.BLOCKED_EFFECTS);
        flagRegistry.register(Flags.GODMODE);
        flagRegistry.register(Flags.RESPAWN_LOCATION);
        flagRegistry.register(Flags.WORLDEDIT);
        flagRegistry.register(Flags.GIVE_EFFECTS);
        flagRegistry.register(Flags.FLY);
        flagRegistry.register(Flags.FLY_SPEED);
        flagRegistry.register(Flags.PLAY_SOUNDS);
        flagRegistry.register(Flags.MYTHICMOB_EGGS);
        flagRegistry.register(Flags.FROSTWALKER);
        flagRegistry.register(Flags.NETHER_PORTALS);
        flagRegistry.register(Flags.GLIDE);
        flagRegistry.register(Flags.CHUNK_UNLOAD);
        flagRegistry.register(Flags.ITEM_DURABILITY);
        flagRegistry.register(Flags.JOIN_LOCATION);

        if (this.isLegacy()) {
            flagRegistry.register(Flags.ALLOW_BLOCK_PLACE);
            flagRegistry.register(Flags.DENY_BLOCK_PLACE);
            flagRegistry.register(Flags.ALLOW_BLOCK_BREAK);
            flagRegistry.register(Flags.DENY_BLOCK_BREAK);
        }
    }

    default public void onEnable(Plugin plugin) throws Exception {
        AbstractSessionManagerWrapper sessionManager = this.getSessionManager();
        sessionManager.registerHandler(TeleportOnEntryFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(TeleportOnExitFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(CommandOnEntryFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(CommandOnExitFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(ConsoleCommandOnEntryFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(ConsoleCommandOnExitFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(WalkSpeedFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(BlockedEffectsFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(GodmodeFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(GiveEffectsFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(FlyFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(FlySpeedFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(PlaySoundsFlagHandler.FACTORY(plugin));
        sessionManager.registerHandler(GlideFlagHandler.FACTORY(plugin));
    }

    public boolean isLegacy();

    public FlagRegistry getFlagRegistry();

    public AbstractSessionManagerWrapper getSessionManager();

    public AbstractRegionContainerWrapper getRegionContainer();

    public LocalPlayer wrapPlayer(Player player);

    public <T> SetFlag<T> getCustomSetFlag(String name, Flag<T> flag);

    public AbstractDelegateExtent getWorldEditFlag(World world, Extent extent,
            com.sk89q.worldedit.entity.Player player);

    public void doUnloadChunkFlagCheck(org.bukkit.World world);

    public boolean doUnloadChunkFlagCheck(org.bukkit.World world, org.bukkit.Chunk chunk);
}
