package com.starcalculator.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.starcalculator.client.math.ExpressionParser;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class CalculatorScreen extends Screen {

    private static final Identifier WHITE =
            Identifier.fromNamespaceAndPath(
                    "stars-calculator",
                    "textures/gui/white.png"
            );

    private String expression = "";
    private String result = "";
    private int stackMode = 64;
    private String stackResult = "";
    private int expressionScroll = 0;
    private final ExpressionParser parser = new ExpressionParser();

    public CalculatorScreen() {
        super(Component.literal("Star's Calculator"));
    }

    public void addCharacter(char c) {
        expression += c;
    }

    @Override
    public void extractRenderState(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float tickProgress
    ) {
        super.extractRenderState(graphics, mouseX, mouseY, tickProgress);

        int guiWidth = 220;
        int guiHeight = 200;

        int left = (this.width - guiWidth) / 2;
        int top = (this.height - guiHeight) / 2;

        int bg = 0xFFE9D8EB;

        graphics.fill(
                left,
                top,
                left + 220,
                top + 200,
                bg
        );

        graphics.fill(
                left,
                top,
                left + 220,
                top + 24,
                0xFFC5ACD9
        );

        graphics.fill(
                left + 18,
                top + 42,
                left + 202,
                top + 74,
                0xFFFFFFFF
        );

        graphics.fill(
                left + 18,
                top + 108,
                left + 202,
                top + 140,
                0xFFEAF7EE
        );

        graphics.text(
                this.font,
                Component.literal("Star's Calculator"),
                left + 18,
                top + 13,
                0xFF444444,
                false
        );

        int expressionBoxWidth = 172;
        int textWidth = this.font.width(expression);

        expressionScroll = Math.max(
                0,
                textWidth - expressionBoxWidth
        );

        graphics.enableScissor(
                left + 24,
                top + 42,
                left + 196,
                top + 74
        );

        graphics.text(
                this.font,
                Component.literal(expression),
                left + 24 - expressionScroll,
                top + 51,
                0xFF444444,
                false
        );

        graphics.disableScissor();

        graphics.text(
                this.font,
                Component.literal("Result"),
                left + 24,
                top + 100,
                0xFF444444,
                false
        );

        graphics.text(
                this.font,
                Component.literal(result),
                left + 24,
                top + 115,
                0xFF444444,
                false
        );

        graphics.text(
                this.font,
                Component.literal(
                        stackMode == 0
                                ? "Stack Mode: Off"
                                : "Stack Mode: x" + stackMode
                ),
                left + 22,
                top + 154,
                0xFF444444,
                false
        );

        if (stackMode != 0) {
            graphics.text(
                    this.font,
                    Component.literal(stackResult),
                    left + 22,
                    top + 172,
                    0xFF444444,
                    false
            );
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        InputConstants.Key key = InputConstants.getKey(event);
        String keyName = key.toString();

        String prefix = "key.keyboard.";

        if (keyName.startsWith(prefix)) {
            String keyboardKey = keyName.substring(prefix.length());

            if (keyboardKey.equals("8") && (event.modifiers() & 1) != 0) {
                expression += "*";
                return true;
            }

            if (keyboardKey.length() == 1
                    && Character.isDigit(keyboardKey.charAt(0))) {
                expression += keyboardKey;
                return true;
            }

            switch (keyboardKey) {
                case "x" -> expression += "x";
                case "minus" -> expression += "-";
                case "slash" -> expression += "/";
                case "equal" -> {
                    if ((event.modifiers() & 1) != 0) {
                        expression += "+";
                    }
                }

                case "backspace" -> {
                    if (!expression.isEmpty()) {
                        expression = expression.substring(
                                0,
                                expression.length() - 1
                        );
                    }
                }

                case "enter", "kp_enter" -> calculate();

                case "tab" -> {
                    switch (stackMode) {
                        case 64 -> stackMode = 16;
                        case 16 -> stackMode = 0;
                        default -> stackMode = 64;
                    }

                    updateStackResult();
                }

                default -> {
                    return super.keyPressed(event);
                }
            }

            return true;
        }

        return super.keyPressed(event);
    }

    private void calculate() {
        try {
            String input = expression.replace("x", "*");
            double answer = parser.evaluate(input);

            if (answer == Math.floor(answer)) {
                result = String.valueOf((long) answer);
            } else {
                result = String.valueOf(answer);
            }

            updateStackResult();

        } catch (Exception e) {
            result = "Error";
        }
    }

    private void updateStackResult() {
        if (stackMode == 0) {
            stackResult = "";
            return;
        }

        try {
            double value = Double.parseDouble(result);

            int stacks = (int) (value / stackMode);
            double leftover = value - (stacks * stackMode);

            String leftoverText;

            if (leftover == Math.floor(leftover)) {
                leftoverText = String.valueOf((long) leftover);
            } else {
                leftoverText = String.valueOf(leftover);
            }

            stackResult = stacks
                    + (stacks == 1 ? " stack + " : " stacks + ")
                    + leftoverText
                    + " items";

        } catch (Exception e) {
            stackResult = "";
        }
    }
}