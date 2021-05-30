/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.dumbdogdiner.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import com.dumbdogdiner.worldguardextraflags.worldguard.handlers.GiveEffectsFlagHandler;
import com.sk89q.worldguard.session.Session;
import org.bukkit.entity.Player;

public class RemoveEffectPacketListener extends PacketAdapter {
    public RemoveEffectPacketListener() {
        super(WorldGuardExtraFlagsPlugin.getPlugin(), PacketType.Play.Server.REMOVE_ENTITY_EFFECT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (!event.isCancelled()) {
            Player player = event.getPlayer();
            if (!player.isValid()) // Work around, getIfPresent is broken inside WG due to using LocalPlayer as key
                                   // instead of CacheKey
            {
                return;
            }

            try {
                Session session = WorldGuardExtraFlagsPlugin.getPlugin().getWorldGuardCommunicator().getSessionManager()
                        .get(player);

                GiveEffectsFlagHandler giveEffectsHandler = session.getHandler(GiveEffectsFlagHandler.class);
                if (giveEffectsHandler.isSupressRemovePotionPacket()) {
                    event.setCancelled(true);
                }
            } catch (IllegalStateException wgBug) {

            }
        }
    }
}
