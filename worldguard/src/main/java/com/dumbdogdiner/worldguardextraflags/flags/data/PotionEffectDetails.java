/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.flags.data;

import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PotionEffectDetails {
    @Getter
    private final long endTime;
    @Getter
    private final int amplifier;
    @Getter
    private final boolean ambient;
    @Getter
    private final boolean particles;

    public long getTimeLeft() {
        return (this.endTime - System.nanoTime());
    }

    public int getTimeLeftInTicks() {
        return (int) (this.getTimeLeft() / TimeUnit.MILLISECONDS.toNanos(50L));
    }
}
