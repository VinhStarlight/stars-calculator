package com.starcalculator.client;
import net.minecraft.resources.Identifier;
import com.starcalculator.client.math.ExpressionParser;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;

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
    private final ExpressionParser parser = new ExpressionParser();

    public CalculatorScreen() {
        super(Component.literal("Star's Calculator"));
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float tickProgress) {
        super.extractRenderState(graphics, mouseX, mouseY, tickProgress);

        int guiWidth = 220;
        int guiHeight = 200;

        int left = (this.width - guiWidth) / 2;
        int top = (this.height - guiHeight) / 2;

        int backgroundColor = 0xFFF7F3EF;      // Warm cream (calculator body)
        int displayBackground = 0xFFFFFFFF;    // White paper (expression display)
        int accentColor = 0xFFD8C8E8;      // Soft lavender
        int resultDisplay = 0xFFE8F7EF;  // Soft mint (result display)
        int resultColor = 0xFFDCE9DD;      // Sage accent (stack mode pill)
        int textColor = 0xFF444444;        // Charcoal text
        int shadowColor = 0x22000000;      // shadow color

        // Shadow
        graphics.fill(
                left - 4,
                top - 4,
                left + guiWidth + 4,
                top + guiHeight + 4,
                shadowColor
        );

        // body color
        graphics.fill(
                left,
                top,
                left + guiWidth,
                top + guiHeight,
                backgroundColor
        );

        // top cap? idk maybe it looks good
        graphics.fill(
                left,
                top,
                left + guiWidth,
                top + 24,
                accentColor
        );

        // Background
        graphics.fill(
                left + 16,
                top + 39,
                left + guiWidth - 16,
                top + 71,
                displayBackground
        );

        // Result background
        graphics.fill(
                left + 16,
                top + 105,
                left + guiWidth - 16,
                top + 137,
                resultDisplay
        );

        // Title
        graphics.text(
                this.font,
                Component.literal("Star's Calculator"),
                left + 18,
                top + 14,
                textColor,
                false
        );

        // Current expression
        graphics.text(
                this.font,
                Component.literal(expression),
                left + 24,
                top + 50,
                textColor,
                false
        );

        // Result text
        graphics.text(
                this.font,
                Component.literal("Result"),
                left + 18,
                top + 90,
                textColor,
                false
        );

        // Result
        graphics.text(
                this.font,
                Component.literal(result),
                left + 20,
                top + 118,
                textColor,
                false
        );

        // Stack
        graphics.text(
                this.font,
                Component.literal(
                        stackMode == 0 ? "Stack Mode: Off" : "Stack Mode: x" + stackMode
                ),
                left + 18,
                top + 152,
                textColor,
                false
        );
        if (stackMode != 0) {
            graphics.text(
                    this.font,
                    Component.literal(stackResult),
                    left + 18,
                    top + 164,
                    textColor,
                    false
            );
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {

        switch (event.key()) {

            // Backspace
            case 259 -> {
                if (!expression.isEmpty()) {
                    expression = expression.substring(0, expression.length() - 1);
                }
                return true;
            }

            // Enter
            case 257, 335 -> {
                calculate();
                return true;
            }
        }
        if (event.key() == 258) { // TAB
            switch (stackMode) {
                case 64 -> stackMode = 16;
                case 16 -> stackMode = 0;
                default -> stackMode = 64;
            }

            updateStackResult();
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean charTyped(net.minecraft.client.input.CharacterEvent event) {
        char c = event.codepointAsString().charAt(0);

        if ("0123456789+-x*/.".indexOf(c) != -1) {
            expression += c;
        }

        return true;
    }

    private void calculate() {
        try {

            // Accept both x and *
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