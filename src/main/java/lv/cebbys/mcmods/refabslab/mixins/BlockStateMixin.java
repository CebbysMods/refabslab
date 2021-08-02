package lv.cebbys.mcmods.refabslab.mixins;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import lv.cebbys.mcmods.refabslab.mixins.accessors.BlockSettingsAccessor;
import lv.cebbys.mcmods.refabslab.mixins.accessors.SettingsAccessor;
import lv.cebbys.mcmods.refabslab.utilities.BlockStateEntityProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class BlockStateMixin extends State<Block, BlockState> implements BlockStateEntityProvider {

	@Shadow
	public abstract Block getBlock();
	@Shadow
	protected abstract BlockState asBlockState();

	@Inject(method = "getHardness", at = @At("HEAD"), cancellable = true)
	public void getHardness(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
		BlockEntity e0 = world.getBlockEntity(pos);
		if (e0 instanceof DoubleSlabEntity) {
			DoubleSlabEntity entity = (DoubleSlabEntity) e0;
			Block bt = Registry.BLOCK.get(entity.getTop());
			Block bb = Registry.BLOCK.get(entity.getBottom());
			Settings st = ((BlockSettingsAccessor) bt).getBlockSettings();
			Settings sb = ((BlockSettingsAccessor) bb).getBlockSettings();
			float hardness = Math.max(((SettingsAccessor) st).getHardness(), ((SettingsAccessor) sb).getHardness());
			cir.setReturnValue(hardness);
			cir.cancel();
		}
	}

	private BlockEntity entity;

	@Override
	public BlockEntity getBlockEntity() {
		return this.entity;
	}

	@Override
	public void setBlockEntity(BlockEntity entity) {
		this.entity = entity;
	}

	protected BlockStateMixin(Block owner, ImmutableMap<Property<?>, Comparable<?>> entries,
							  MapCodec<BlockState> codec) {
		super(owner, entries, codec);
	}

}
