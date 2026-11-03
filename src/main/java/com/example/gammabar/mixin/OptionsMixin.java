package com.example.gammabar.mixin;

import com.example.gammabar.GammaBarClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Vanilla's options file only accepts 0-1, so clamp while saving, then restore. */
@Mixin(Options.class)
public abstract class OptionsMixin {
    @Inject(method = "save", at = @At("HEAD"))
    private void gammabar$clampForSave(CallbackInfo ci) {
        ((OptionInstanceAccessor) (Object) ((Options) (Object) this).gamma())
                .gammabar$setValue(Math.max(0.0, Math.min(1.0, GammaBarClient.getGamma())));
    }

    @Inject(method = "save", at = @At("TAIL"))
    private void gammabar$restoreAfterSave(CallbackInfo ci) {
        GammaBarClient.apply(Minecraft.getInstance());
    }
}
