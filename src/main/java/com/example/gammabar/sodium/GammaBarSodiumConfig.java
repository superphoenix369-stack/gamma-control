package com.example.gammabar.sodium;

import com.example.gammabar.GammaBarClient;
import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/**
 * Sodium replaces the vanilla Video Settings screen, so the vanilla-screen mixin never
 * runs when Sodium is installed. Sodium loads this class (via the "sodium:config_api_user"
 * entrypoint in fabric.mod.json) and shows the slider on its own "Gamma" page.
 * Without Sodium this class is never loaded.
 */
public class GammaBarSodiumConfig implements ConfigEntryPoint {
    @Override
    public void registerConfigLate(ConfigBuilder builder) {
        int min = (int) Math.round(GammaBarClient.MIN * 100);
        int max = (int) Math.round(GammaBarClient.MAX * 100);

        builder.registerOwnModOptions()
                .addPage(builder.createOptionPage()
                        .setName(Component.translatable("options.gammabar.page"))
                        .addOptionGroup(builder.createOptionGroup()
                                .addOption(builder.createIntegerOption(Identifier.parse("gammabar:gamma"))
                                        .setName(Component.translatable("options.gammabar.gamma"))
                                        .setTooltip(Component.translatable("options.gammabar.gamma.tooltip"))
                                        .setStorageHandler(GammaBarClient::save)
                                        .setRange(min, max, 5)
                                        .setValueFormatter(value -> Component.literal(value + "%"))
                                        .setDefaultValue(100)
                                        .setBinding(
                                                value -> GammaBarClient.setGammaNoSave(value / 100.0),
                                                () -> (int) Math.round(GammaBarClient.getGamma() * 100)))));
    }
}
