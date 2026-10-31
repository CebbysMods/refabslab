# Refabricated Slabs

Mix and match slabs as you want: place any two different slabs into one block space.

Since 1.1 the mod is written against the [Modularity](https://github.com/CebbysMods/modularity)
API - the mod contains no loader or Minecraft specific code.

## Structure

| Module | Contents |
|---|---|
| `refabslab-common` | All mod logic (`RefabslabMod`), depends only on `modularity-api`. Feature tests run on `modularity-testkit`. |
| `refabslab-fabric-1.21.1` | `fabric.mod.json`, assets and data for Fabric 1.21.1. Bundles Modularity. |

## Features

* Combine any two different slabs; the double slab keeps both and renders both models.
* Mining speed and tool requirements follow the slabs; both slabs drop.
* Light level of the brightest slab; break/hit particles of the right half.
* Works with Sodium (0.6+, or older with Indium) and Iris.
* KleeSlabs support: break only the half you are looking at.
* Data-driven exclusions: add slabs to the block tag `refabslab:non_combinable`.

## Building

```sh
# 1. publish Modularity locally (from a modularity checkout)
./gradlew publishToMavenLocal
# 2. build refabslab
./gradlew build
# logic + tests only, without Minecraft mavens
./gradlew -Prefabslab.platforms=none :refabslab-common:test
```
