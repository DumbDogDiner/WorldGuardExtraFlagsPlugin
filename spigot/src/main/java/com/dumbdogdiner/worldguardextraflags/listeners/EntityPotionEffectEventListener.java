/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.listeners;

import com.dumbdogdiner.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.GiveEffectsFlagHandler;
import com.sk89q.worldguard.session.Session;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

@RequiredArgsConstructor
public class EntityPotionEffectEventListener implements Listener {
    @Getter
    private final WorldGuardExtraFlagsPlugin plugin;

    @EventHandler(ignoreCancelled = true)
    public void onEntityPotionEffectEvent(EntityPotionEffectEvent event) {
        if (event.getAction() != EntityPotionEffectEvent.Action.REMOVED) {
            return;
        }

        if (event.getCause() != EntityPotionEffectEvent.Cause.PLUGIN) {
            return;
        }

        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        if (!player.isValid()) // Work around, getIfPresent is broken inside WG due to using LocalPlayer as key instead
                               // of CacheKey
        {
            return;
        }

        try {
            Session session =
                    WorldGuardExtraFlagsPlugin.getPlugin().getWorldGuardCommunicator().getSessionManager().get(player);

            GiveEffectsFlagHandler giveEffectsHandler = session.getHandler(GiveEffectsFlagHandler.class);
            if (giveEffectsHandler.isSupressRemovePotionPacket()) {
                event.setCancelled(true);
            }
        } catch (IllegalStateException wgBug) {

        }
    }
}
