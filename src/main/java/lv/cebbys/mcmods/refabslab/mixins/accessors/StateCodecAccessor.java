package lv.cebbys.mcmods.refabslab.mixins.accessors;

import com.mojang.serialization.MapCodec;
import net.minecraft.state.State;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(State.class)
public interface StateCodecAccessor<T> {
    @Accessor
    MapCodec<T> getCodec();
}
