package com.example.gammabar.mixin;

import com.example.gammabar.GammaBarClient;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoSettingsScreenMixin extends OptionsSubScreen {
    protected VideoSettingsScreenMixin(Screen parent, Options options, Component title) {
        super(parent, options, title);
    }

    @Inject(method = "addOptions", at = @At("TAIL"))
    private void gammabar$addSlider(CallbackInfo ci) {
        int min = (int) Math.round(GammaBarClient.MIN * 100);
        int max = (int) Math.round(GammaBarClient.MAX * 100);
        OptionInstance<Integer> gammaBar = new OptionInstance<>(
                "options.gammabar.gamma",
                OptionInstance.noTooltip(),
                (caption, value) -> Component.translatable(
                        "options.generic_value", caption, Component.literal(value + "%")),
                new OptionInstance.IntRange(min, max),
                (int) Math.round(GammaBarClient.getGamma() * 100),
                value -> GammaBarClient.setGamma(value / 100.0));
        this.list.addSmall(gammaBar);
    }
}
