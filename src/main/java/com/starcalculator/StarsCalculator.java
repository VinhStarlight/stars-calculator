package com.starcalculator;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarsCalculator implements ModInitializer {
    public static final String MOD_ID = "stars-calculator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("calculator works lol");
    }
}