package com.starcalculator.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class StarsCalculatorClient implements ClientModInitializer {

    private static KeyMapping OPEN_CALCULATOR;

    @Override
    public void onInitializeClient() {

        OPEN_CALCULATOR = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key.starscalculator.open",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_C,
                        KeyMapping.Category.MISC
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_CALCULATOR.consumeClick()) {
                client.gui.setScreen(new CalculatorScreen());
            }
        });
    }
}