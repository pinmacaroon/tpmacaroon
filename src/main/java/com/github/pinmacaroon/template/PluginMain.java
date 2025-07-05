package com.github.pinmacaroon.template;

import com.github.pinmacaroon.template.command.TestCommand;
import com.github.zafarkhaja.semver.Version;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.time.Instant;
import java.util.Random;
import java.util.logging.Logger;

public final class PluginMain extends JavaPlugin {
    public static final Random RANDOM = new Random(Instant.now().getEpochSecond());
    public static final Version VERSION = new Version.Builder()
            .setMajorVersion(0)
            .setMinorVersion(0)
            .setPatchVersion(1)
            .build();
    public static Logger LOGGER;
    public static FileConfiguration CONFIG;
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private void createConfig() {
        try {
            if (!this.getDataFolder().exists()) {
                //noinspection ResultOfMethodCallIgnored
                this.getDataFolder().mkdirs();
            }
            File file = new File(this.getDataFolder(), "config.yml");
            if (!file.exists()) {
                this.getLogger().info("Config.yml not found, creating!");

                //this.getConfig().addDefault("config.key", "dafault");

                this.getConfig().options().copyDefaults(true);

                this.saveDefaultConfig();
            } else {
                this.getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        this.createConfig();
        LOGGER = this.getLogger();
        CONFIG = this.getConfig();

        this.getCommand("test").setExecutor(new TestCommand());

        LOGGER.info(CONFIG.getString("config.key"));
    }

    @Override
    public void onDisable() {

    }
}
