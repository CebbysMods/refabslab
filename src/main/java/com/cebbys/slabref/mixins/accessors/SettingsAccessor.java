package com.cebbys.slabref.mixins.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.AbstractBlock;

@Mixin(AbstractBlock.Settings.class)
public interface SettingsAccessor {

	@Accessor("resistance")
	public float getResistance();

	@Accessor("hardness")
	public float getHardness();

}
