package com.lukemango.cytnoteblockregion.music;

import com.lukemango.cytnoteblockregion.CYTNoteblockRegion;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

public class MusicRegister {

    private final MusicManager musicManager;
    private final CYTNoteblockRegion plugin;

    public MusicRegister(MusicManager musicManager) {
        this.musicManager = musicManager;
        this.plugin = musicManager.getPlugin();
    }

    public void loadSongs() {
        plugin.getLogger().log(INFO, "Loading songs...");
        File musicFolder = new File(plugin.getDataFolder(), "music");
        if (!(musicFolder.exists())) {
            musicFolder.mkdir();
        }

        if (musicFolder.listFiles() == null) {
            plugin.getLogger().log(SEVERE, "No music files found!");
            return;
        }

        for (File file : musicFolder.listFiles()) {
            if (!file.getName().endsWith(".nbs")) {
                continue;
            }

            Song song = NBSDecoder.parse(file);
            musicManager.addSong(file.getName().replace(".nbs", ""), song);
        }

        plugin.getLogger().log(INFO, "Loaded " + musicManager.getSongs().size() + " songs!");
    }

    public void loadRegions() {
        final FileConfiguration config = plugin.getConfig();
        if (config.getConfigurationSection("regions") == null) return;

        final Set<String> worldSet = config.getConfigurationSection("regions").getKeys(false);
        plugin.getLogger().log(INFO, "Loading regions...");

        for (String worldName : worldSet) {
            loadWorldRegions(worldName);
        }
        plugin.getLogger().log(INFO, "Loaded " + musicManager.getRegionSongs().size() + " regions!");
    }

    public void loadWorldRegions(String worldName) {
        World bukkitWorld = Bukkit.getWorld(worldName);
        if (bukkitWorld == null) {
            plugin.getLogger().warning("World " + worldName + " is not loaded yet. Skipping region setup until loaded.");
            return;
        }

        final FileConfiguration config = plugin.getConfig();
        if (!config.contains("regions." + worldName)) return;

        final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        Set<String> regionSet = config.getConfigurationSection("regions." + worldName).getKeys(false);

        for (String region : regionSet) {
            com.sk89q.worldedit.world.World wgWorld = BukkitAdapter.adapt(bukkitWorld);
            RegionManager regionList = container.get(wgWorld);

            if (regionList == null) {
                plugin.getLogger().warning("WorldGuard region manager for world " + worldName + " is null!");
                continue;
            }

            ProtectedRegion rg = regionList.getRegion(region);
            if (rg == null) {
                plugin.getLogger().warning("Region " + region + " does not exist in world " + worldName + "!");
                continue;
            }

            List<String> regionSongs = config.getStringList("regions." + worldName + "." + region + ".songs");
            List<Song> songs = new ArrayList<>();

            for (String song : regionSongs) {
                if (musicManager.getSongs().containsKey(song)) {
                    songs.add(musicManager.getSongs().get(song));
                } else {
                    plugin.getLogger().warning("Song " + song + " does not exist!");
                }
            }

            if (songs.isEmpty()) continue;

            RadioSongPlayer player = musicManager.getRegionSongs().computeIfAbsent(rg, k -> new RadioSongPlayer(songs.get(0)));
            player.setAutoDestroy(false);
            player.setPlaying(true);
            player.setLoop(config.getBoolean("regions." + worldName + "." + region + ".loop"));
            player.setRandom(config.getBoolean("regions." + worldName + "." + region + ".shuffle"));
            player.setVolume((byte) config.getInt("regions." + worldName + "." + region + ".volume"));
            player.setTick((short) config.getInt("regions." + worldName + "." + region + ".tick"));
        }
    }
}
