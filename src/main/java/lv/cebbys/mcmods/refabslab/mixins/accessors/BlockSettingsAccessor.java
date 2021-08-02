package lv.cebbys.mcmods.refabslab.mixins.accessors;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Settings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.class)
public interface BlockSettingsAccessor {
	
	@Accessor("settings")
	public Settings getBlockSettings();

	@Accessor("settings")
	public void setBlockSettings(Settings settings);
	
}
