package com.example.gammabar.mixin;

import com.example.gammabar.GammaBarClient;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.Lightmap;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Overrides the brightness the lightmap shader receives. Hooking here (instead of
 * writing into options.gamma) skips vanilla's clamping, so negative values work.
 */
@Mixin(Lightmap.class)
public abstract class LightmapMixin {
    @ModifyExpressionValue(
            method = "render",
            at = @At(value = "FIELD", opcode = Opcodes.GETFIELD,
                    target = "Lnet/minecraft/client/renderer/state/LightmapRenderState;brightness:F"))
    private float gammabar$brightness(float original) {
        return GammaBarClient.hasOverride() ? (float) GammaBarClient.getGamma() : original;
    }
}
