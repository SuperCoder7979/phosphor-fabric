package me.jellysquid.mods.phosphor.common.mixins.lighting;

import me.jellysquid.mods.phosphor.api.ILightingEngineProvider;
import me.jellysquid.mods.phosphor.common.world.LightingEngine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public abstract class MixinWorld implements ILightingEngineProvider {
    private LightingEngine lightingEngine;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        this.lightingEngine = new LightingEngine((World) (Object) this);
    }

    @Override
    public LightingEngine getLightingEngine() {
        return this.lightingEngine;
    }

    /**
     * Postpones lighting checks to the lighting engine and returns a success value.
     *
     * @author Angeline
     */
    @Overwrite
    public boolean checkLightFor(EnumSkyBlock type, BlockPos pos) {
        this.lightingEngine.scheduleLightUpdate(type, pos);

        return true;
    }
}