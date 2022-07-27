# Refabricated Slabs Mod Version Changes

---

## Version 0.0.0 - 0.5.0 - Forgotten Updates

> *Additions:*
> * Double slab block.

## Version 0.5.0 - Correct Tooling Update

> *Additions:*
> * Different tools are efficient on different material slabs. Pickaxe is efficient on stone / metal slabs. Axe is efficient on wood material slabs. Breaking mixed slab (wood / stone) with axe drops only wooden slab.

> *Fixes:*
> * Double slabs does not drop slabs
> * A lot... do not remember

> *Changes:*
> * Removed artifice dependency. Now uses Celib 0.5.0 resource generator.
> * Removed multiple mixin classes making mod more cross mod compatible.

## Version 0.5.1 - Bugfixes

> *Fixes:*
> * When standing on slab and placing another one makes the existing slab to be replaced.
> * When in creative game mode, when placing a slab on top of slab consumes slab from inventory.

## Version 0.6.0 - The Wall Update

> *Additions:*
> * Wall block (Vertical slab).
> * Minecraft vanilla walls.
> * Blockus mod walls.
> * Wall block item model generation based on existing models.
> * Block model generation based on existing models.

## Version 0.6.1 - The Forgotten Wall Update

> *Changes:*
> * Removed wall slabs. Make the mod simple and working.
> * Replaced block entity data with cardinal chunk api.

> *Fixes:*
> * Slabs being replaced by any block on right click.

## Version 0.6.2 - 0.6.4 - The Missed Documentation Update

> *Fixes:*
> * Slabs being invisible, because BlockView conversion failure.
> * Missing textures. Because of missing double slab model.

## Version 0.7.0 - Many Shades Of Renderers Update

> *Additions:*
> * Support for Sodium + Indium.
> * Support for Iris and shaders.

## Version 0.7.1 - 0.7.4 - Ouh Those Fixes

> *Changes:*
> * Updated Celib mod to version 1.1.0.

> *Fixes:*
> * Changed Cardinal API version to make the Refabslab mod compatible with other mods.
> * Fixed issue where game would crash if sand block landed on lower slab block.