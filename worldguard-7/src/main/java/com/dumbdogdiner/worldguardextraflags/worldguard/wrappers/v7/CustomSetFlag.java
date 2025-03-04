/*
 * Copyright (c) 2020-2021 Skye Elliot. All rights reserved.
 * Licensed under the MIT License, see LICENSE for more information...
 */
package com.dumbdogdiner.worldguardextraflags.worldguard.wrappers.v7;

import com.google.common.collect.Sets;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.FlagContext;
import com.sk89q.worldguard.protection.flags.InvalidFlagFormat;
import com.sk89q.worldguard.protection.flags.SetFlag;
import java.util.Set;

public class CustomSetFlag<T> extends SetFlag<T> {
    public CustomSetFlag(String name, Flag<T> subFlag) {
        super(name, subFlag);
    }

    @Override
    public Set<T> parseInput(FlagContext context) throws InvalidFlagFormat {
        String input = context.getUserInput();
        if (input.isEmpty()) {
            return Sets.newHashSet();
        } else {
            Set<T> items = Sets.newHashSet();

            for (String str : input.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1)) {
                if (str.startsWith("\"") && str.endsWith("\"")) {
                    str = str.substring(1, str.length() - 1);
                }

                FlagContext copy = context.copyWith(null, str, null);
                items.add(this.getType().parseInput(copy));
            }

            return items;
        }
    }
}
