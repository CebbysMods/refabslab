package lv.cebbys.mcmods.refabslab.mixins.accessors;

import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.Settings.class)
public interface SettingsAccessor {

	@Accessor("resistance")
	public float getResistance();

	@Accessor("hardness")
	public float getHardness();

}
