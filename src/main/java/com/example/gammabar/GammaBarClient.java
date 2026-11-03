package com.example.gammabar;

import com.example.gammabar.mixin.OptionInstanceAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
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
    private static double gamma = 1.0;

    @Override
    public void onInitializeClient() {
        load();
        ClientLifecycleEvents.CLIENT_STARTED.register(GammaBarClient::apply);
    }

    public static double getGamma() { return gamma; }

    public static void setGamma(double value) {
        gamma = Math.max(MIN, Math.min(MAX, value));
        apply(Minecraft.getInstance());
        save();
    }

    /** Pushes our value into vanilla's gamma option so the lightmap uses it. */
    public static void apply(Minecraft mc) {
        if (mc == null || mc.options == null) return;
        ((OptionInstanceAccessor) (Object) mc.options.gamma()).gammabar$setValue(gamma);
    }

    private static void load() {
        if (!Files.exists(FILE)) return;
        try (Reader r = Files.newBufferedReader(FILE)) {
            Properties p = new Properties();
            p.load(r);
            gamma = Math.max(MIN, Math.min(MAX, Double.parseDouble(p.getProperty("gamma", "1.0"))));
        } catch (IOException | NumberFormatException ignored) {}
    }

    private static void save() {
        try (Writer w = Files.newBufferedWriter(FILE)) {
            Properties p = new Properties();
            p.setProperty("gamma", Double.toString(gamma));
            p.store(w, "Gamma Bar");
        } catch (IOException ignored) {}
    }
}
