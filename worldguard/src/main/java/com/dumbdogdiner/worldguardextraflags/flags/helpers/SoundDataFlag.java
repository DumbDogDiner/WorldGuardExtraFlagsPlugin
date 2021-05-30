/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.flags.helpers;

import com.dumbdogdiner.worldguardextraflags.flags.data.SoundData;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.FlagContext;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;

public class SoundDataFlag extends Flag<SoundData> {
    public SoundDataFlag(String name) {
        super(name);
    }

    @Override
    public Object marshal(SoundData o) {
        return o.getSound().toString() + " " + o.getInterval();
    }

    @Override
    public SoundData parseInput(FlagContext context) throws InvalidFlagFormat {
        String[] splitd = context.getUserInput().trim().split(" ");
        if (splitd.length == 2) {
            return new SoundData(splitd[0], Integer.parseInt(splitd[1]));
        } else {
            throw new InvalidFlagFormat("Please use format: <sound name> <interval in ticks>");
        }
    }

    @Override
    public SoundData unmarshal(Object o) {
        String[] splitd = o.toString().split(" ");
        return new SoundData(splitd[0], Integer.parseInt(splitd[1]));
    }
}
