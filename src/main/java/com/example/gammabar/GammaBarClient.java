package com.example.gammabar;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class GammaBarClient implements ClientModInitializer {
    public static final double MIN = -1.0; // -100% (pitch black)
    public static final double MAX = 15.0; // 1500%
    private static final Path FILE =
            FabricLoader.getInstance().getConfigDir().resolve("gammabar.properties");

    /** null until the player has used the Gamma slider; then vanilla's value is ignored. */
    private static Double override = null;

    @Override
    public void onInitializeClient() {
        load();
    }

    public static boolean hasOverride() { return override != null; }

    public static double getGamma() {
        if (override != null) return override;
        Minecraft mc = Minecraft.getInstance();
        return mc != null && mc.options != null ? mc.options.gamma().get() : 1.0;
    }

    public static void setGamma(double value) {
        override = Math.max(MIN, Math.min(MAX, value));
        save();
    }

    /** Updates the value without writing the file (the caller saves later via save()). */
    public static void setGammaNoSave(double value) {
        override = Math.max(MIN, Math.min(MAX, value));
    }

    private static void load() {
        if (!Files.exists(FILE)) return;
        try (Reader r = Files.newBufferedReader(FILE)) {
            Properties p = new Properties();
            p.load(r);
            String v = p.getProperty("gamma");
            if (v != null) override = Math.max(MIN, Math.min(MAX, Double.parseDouble(v)));
        } catch (IOException | NumberFormatException ignored) {}
    }

    public static void save() {
        if (override == null) return;
        try (Writer w = Files.newBufferedWriter(FILE)) {
            Properties p = new Properties();
            p.setProperty("gamma", Double.toString(override));
            p.store(w, "Gamma Bar");
        } catch (IOException ignored) {}
    }
}
