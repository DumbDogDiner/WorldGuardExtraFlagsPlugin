/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags;

import com.dumbdogdiner.worldguardextraflags.flags.Flags;
import com.dumbdogdiner.worldguardextraflags.listeners.BlockListener;
import com.dumbdogdiner.worldguardextraflags.listeners.BlockListenerWG;
import com.dumbdogdiner.worldguardextraflags.listeners.EntityListener;
import com.dumbdogdiner.worldguardextraflags.listeners.EntityListenerOnePointNine;
import com.dumbdogdiner.worldguardextraflags.listeners.EntityPotionEffectEventListener;
import com.dumbdogdiner.worldguardextraflags.listeners.PlayerListener;
import com.dumbdogdiner.worldguardextraflags.listeners.WorldEditListener;
import com.dumbdogdiner.worldguardextraflags.listeners.WorldListener;
import com.dumbdogdiner.worldguardextraflags.protocollib.ProtocolLibHelper;
import com.dumbdogdiner.worldguardextraflags.utils.SupportedFeatures;
import com.dumbdogdiner.worldguardextraflags.worldguard.WorldGuardUtils;
import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.WorldGuardCommunicator;
import com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.v7.WorldGuardSevenCommunicator;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flag;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;
import kr.entree.spigradle.annotations.PluginMain;
import lombok.Getter;
import org.bukkit.World;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.Plugin;

@PluginMain
public class WorldGuardExtraFlagsPlugin extends AbstractWorldGuardExtraFlagsPlugin {
    @Getter
    private static WorldGuardExtraFlagsPlugin plugin;

    @Getter
    private WorldGuardPlugin worldGuardPlugin;
    @Getter
    private WorldEditPlugin worldEditPlugin;

    @Getter
    private ProtocolLibHelper protocolLibHelper;

    public WorldGuardExtraFlagsPlugin() {
        WorldGuardExtraFlagsPlugin.plugin = this;
    }

    @Override
    public void onLoad() {
        this.worldEditPlugin = (WorldEditPlugin) this.getServer().getPluginManager().getPlugin("WorldEdit");
        this.worldGuardPlugin = (WorldGuardPlugin) this.getServer().getPluginManager().getPlugin("WorldGuard");

        this.worldGuardCommunicator = WorldGuardExtraFlagsPlugin.createWorldGuardCommunicator();
        if (this.worldGuardCommunicator == null) {
            throw new RuntimeException(
                    "Unsupported WorldGuard version: " + this.worldGuardPlugin.getDescription().getVersion());
        }

        WorldGuardUtils.setCommunicator(this.worldGuardCommunicator);

        try {
            this.worldGuardCommunicator.onLoad(this);
        } catch (Exception e) {
            this.getServer().getPluginManager().disablePlugin(this);

            throw new RuntimeException("Failed to load WorldGuard communicator", e);
        }

        try {
            Plugin protocolLibPlugin = this.getServer().getPluginManager().getPlugin("ProtocolLib");
            if (protocolLibPlugin != null) {
                this.protocolLibHelper = new ProtocolLibHelper(this, protocolLibPlugin);
            }
        } catch (Throwable ignore) {

        }
    }

    @Override
    public void onEnable() {
        if (this.worldGuardCommunicator == null) {
            this.getServer().getPluginManager().disablePlugin(this);

            return;
        }

        try {
            this.worldGuardCommunicator.onEnable(this);
        } catch (Exception e) {
            this.getServer().getPluginManager().disablePlugin(this);

            throw new RuntimeException("Failed to enable WorldGuard communicator", e);
        }

        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BlockListener(this), this);
        this.getServer().getPluginManager().registerEvents(new WorldListener(this), this);

        if (this.worldGuardCommunicator.isLegacy()) {
            this.getServer().getPluginManager().registerEvents(new BlockListenerWG(this), this);
        }

        try {
            if (EntityToggleGlideEvent.class != null) // LOL, Just making it look nice xD
            {
                this.getServer().getPluginManager().registerEvents(new EntityListenerOnePointNine(this), this);
            }
        } catch (NoClassDefFoundError ignored) {

        }

        try {
            ParameterizedType type =
                    (ParameterizedType) PortalCreateEvent.class.getDeclaredField("blocks").getGenericType();
            Class<?> clazz = (Class<?>) type.getActualTypeArguments()[0];
            this.getServer().getPluginManager().registerEvents(new EntityListener(this), this);
        } catch (Throwable ignored) {
            this.getServer().getPluginManager().registerEvents(new EntityListener(this), this);
        }

        this.worldEditPlugin.getWorldEdit().getEventBus().register(new WorldEditListener(this));

        if (this.protocolLibHelper != null) {
            this.protocolLibHelper.onEnable();
        } else if (SupportedFeatures.isPotionEffectEventSupported()) {
            this.getServer().getPluginManager().registerEvents(new EntityPotionEffectEventListener(this), this);
        }

        for (World world : this.getServer().getWorlds()) {
            this.getWorldGuardCommunicator().doUnloadChunkFlagCheck(world);
        }
    }

    private Set<Flag<?>> getPluginFlags() {
        Set<Flag<?>> flags = new HashSet<>();

        for (Field field : Flags.class.getFields()) {
            try {
                flags.add((Flag<?>) field.get(null));
            } catch (IllegalArgumentException | IllegalAccessException e) {
            }
        }

        return flags;
    }

    public static WorldGuardCommunicator createWorldGuardCommunicator() {
        try {
            Class.forName("com.sk89q.worldguard.WorldGuard"); // Only exists in WG 7

            return new WorldGuardSevenCommunicator();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
