package com.lukemango.cytnoteblockregion;

import com.lukemango.cytnoteblockregion.commands.ReloadCommand;
import com.lukemango.cytnoteblockregion.music.MusicManager;
import com.lukemango.cytnoteblockregion.utils.WorldGuardUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class CYTNoteblockRegion extends JavaPlugin {

    private MusicManager musicManager;
    private WorldGuardUtil worldGuardUtil;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        worldGuardUtil = new WorldGuardUtil(this);
        musicManager = new MusicManager(this);

        // Register WorldLoad listener
        getServer().getPluginManager().registerEvents(
            new com.lukemango.cytnoteblockregion.listeners.WorldListener(this), this
        );

        getCommand("cytnoteblockregion").setExecutor(new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (worldGuardUtil != null) {
            worldGuardUtil.cancelTask();
            worldGuardUtil.stopPlaying();
        }
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        if (musicManager != null) {
            musicManager.reloadSongs();
        }
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public WorldGuardUtil getWorldGuardUtil() {
        return worldGuardUtil;
    }
}
