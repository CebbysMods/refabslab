package com.cebbys.slabref.mixins.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Settings;

@Mixin(AbstractBlock.class)
public interface BlockSettingsAccessor {
	@Accessor("settings")
	public Settings getBlockSettings();
	
	@Accessor("settings")
	public void setBlockSettings(Settings settings);
}
