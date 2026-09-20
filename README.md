# Star's Calculator 🧮

**Star's Calculator** is a lightweight, keyboard-driven calculator mod for **Minecraft Java Edition**.

Built with **Fabric**, it provides a simple in-game calculator without filling the screen with calculator buttons. Just open it, type an expression, and press Enter.

## ✨ Features

* 🧮 Basic arithmetic

  * Addition
  * Subtraction
  * Multiplication
  * Division
  * Decimals
  * Parentheses
* ⌨️ Fully keyboard-driven input
* 📦 Minecraft item stack calculations
* 🔢 Automatic handling of whole numbers and decimals
* 🌸 Simple custom GUI
* ⚡ Lightweight expression parser
* 🎮 Designed to work entirely in-game

## 🎹 Controls

| Key         | Action                    |
| ----------- | ------------------------- |
| `C`         | Open calculator           |
| `0` – `9`   | Enter numbers             |
| `.`         | Decimal point             |
| `x`         | Multiplication            |
| `Shift + 8` | Multiplication (`*`)      |
| `+`         | Addition                  |
| `-`         | Subtraction               |
| `/`         | Division                  |
| `Backspace` | Delete the last character |
| `Enter`     | Calculate expression      |
| `Tab`       | Change stack mode         |

## 🧮 Expressions

The calculator supports standard arithmetic expressions with operator precedence.

Examples:

```text id="q4b7r2"
12 + 5
64 / 16
8 x 7
2 * 16
(10 + 5) / 3
```

Multiplication can be written using either `x` or `*`.

The expression parser evaluates multiplication and division before addition and subtraction, and supports parentheses.

## 📦 Stack Mode

Star's Calculator can also convert results into Minecraft item stacks.

Press **Tab** to cycle through the available modes:

```text id="k2x6fd"
Stack Mode: x64
Stack Mode: x16
Stack Mode: Off
```

For example:

```text id="n8w4py"
130

→ 2 stacks + 2 items
```

when using 64-item stacks.

This is useful for quickly figuring out how many Minecraft stacks are required for a given quantity.

## 🎮 Opening the Calculator

Press:

```text id="j1v8xq"
C
```

The calculator opens directly in-game and accepts keyboard input immediately.

No calculator buttons. No extra GUI clutter. Just type. ⌨️

## 🛠️ Requirements

* **Minecraft:** 26.3
* **Fabric Loader:** 0.19.5+
* **Fabric API:** 0.161.0+26.3
* **Java:** 25+

## 📥 Installation

1. Install **Fabric Loader** for Minecraft 26.3.
2. Install **Fabric API** for Minecraft 26.3.
3. Download the latest Star's Calculator JAR.
4. Place the JAR in your Minecraft `mods` folder.
5. Launch Minecraft with the Fabric profile.

## 🔧 Building From Source

Clone the repository and enter the project directory:

```bash id="u0y6kn"
git clone <repository-url>
cd stars-calculator
```

Run the Minecraft development client:

```bash id="r5s2ac"
./gradlew runClient
```

Build the mod:

```bash id="x8c1qp"
./gradlew build
```

The resulting JAR files can be found in:

```text id="f3m7za"
build/libs/
```

## 📁 Project Structure

```text id="v5x2jw"
stars-calculator/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── starcalculator/
│   │               └── StarsCalculator.java
│   │
│   └── client/
│       └── java/
│           └── com/
│               └── starcalculator/
│                   └── client/
│                       ├── CalculatorScreen.java
│                       ├── StarsCalculatorClient.java
│                       └── math/
│                           └── ExpressionParser.java
│
├── build.gradle
├── gradle.properties
├── gradlew
└── fabric.mod.json
```

## 🧠 How It Works

Star's Calculator is split into three main parts:

### `StarsCalculator`

The main Fabric mod initializer.

It handles basic mod initialization and logging.

### `StarsCalculatorClient`

Handles client-side functionality such as:

* Registering the calculator keybind
* Opening the calculator
* Processing keyboard input
* Handling calculator controls

### `ExpressionParser`

A small recursive-descent expression parser responsible for evaluating mathematical expressions.

It handles:

* Numbers
* `+`
* `-`
* `*`
* `/`
* Parentheses

The parser is independent from the Minecraft GUI, keeping the calculation logic separate from the interface.

## 🌱 Development Status

Star's Calculator is currently a small personal project and is actively being developed.

Current focus is keeping the calculator:

* Simple
* Lightweight
* Keyboard-first
* Easy to use
* Compatible with current Minecraft/Fabric versions

## 📜 License

This project is licensed under **CC0 1.0 Universal**.

You are free to use, modify, and redistribute the project without restriction.
