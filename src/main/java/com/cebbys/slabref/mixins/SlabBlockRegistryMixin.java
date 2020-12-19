package com.cebbys.slabref.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import com.cebbys.slabref.registries.SlabRegistry;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Mixin(Registry.class)
public abstract class SlabBlockRegistryMixin {

	// Thanks to Discord `sailKite`
	@Inject(method = "Lnet/minecraft/util/registry/Registry;register(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;", at = @At("HEAD"))
	private static <V, T extends V> void register(Registry<V> registry, Identifier id, T entry,
			CallbackInfoReturnable<T> cir) {
		if(entry instanceof SlabBlock) {
			SlabRegistry.SLABS.add(id);
		}
	}
}
