# Boring More Vanilla Shields

A small NeoForge mod for Minecraft 1.21.1 that gives shields the same kind of material progression as armor while keeping vanilla shield behavior.

## Shields

| Shield | Durability | Material |
| --- | ---: | --- |
| Leather Shield | 196 | Leather |
| Iron Shield | 336 | Vanilla shield, renamed |
| Copper Shield | 420 | Copper Ingot |
| Golden Shield | 322 | Gold Ingot |
| Diamond Shield | 1,024 | Diamond |
| Netherite Shield | 2,042 | Diamond Shield upgraded with Netherite |

All variants:

- block attacks exactly like the vanilla shield;
- use the vanilla shield model and animations;
- support banner patterns by combining one banner and one undecorated shield in any crafting grid;
- keep their material-colored rim when decorated;
- support Unbreaking, Mending, and Curse of Vanishing through vanilla enchantment tags.

The Leather Shield can also be dyed with any vanilla dye recipe. Its rim—not its wooden face—changes color. The Netherite Shield is fire resistant like other Netherite equipment.

## Recipes

Leather, Copper, Golden, and Diamond Shields use the vanilla shield pattern. Replace the center-top Iron Ingot with the shield's respective material:

```text
P M P
P P P
  P
```

`P` is any plank and `M` is Leather, a Copper Ingot, a Gold Ingot, or a Diamond.

The Netherite Shield uses a Smithing Table with:

1. Netherite Upgrade Smithing Template
2. Diamond Shield
3. Netherite Ingot

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.266 or newer in the 21.1 line
- Java 21 (development only)

## Building

On Windows:

```powershell
.\gradlew.bat build
```

On Linux or macOS:

```bash
./gradlew build
```

The mod JAR is written to `build/libs/`.

## License

This project is available under the [MIT License](LICENSE).
