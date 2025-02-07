package com.lukemango.cytnoteblockregion.music;

import com.lukemango.cytnoteblockregion.CYTNoteblockRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private final CYTNoteblockRegion plugin;
    private final MusicRegister musicRegister;

    private final Map<String, Song> songs = new HashMap<>();
    private final Map<ProtectedRegion, RadioSongPlayer> regionSongs = new HashMap<>();

    public MusicManager(CYTNoteblockRegion plugin) {
        this.plugin = plugin;
        this.musicRegister = new MusicRegister(this);

        loadSongs();
        loadRegions();

        plugin.getWorldGuardUtil().startCheckingPlayers();
    }

    public CYTNoteblockRegion getPlugin() {
        return plugin;
    }

    public Map<String, Song> getSongs() {
        return songs;
    }

    public void addSong(String title, Song song) {
        songs.put(title, song);
    }

    public Map<ProtectedRegion, RadioSongPlayer> getRegionSongs() {
        return regionSongs;
    }

    public void reloadSongs() {
        songs.clear();
        regionSongs.clear();
        loadSongs();
        loadRegions();
    }

    private void loadSongs() {
        // Implementation to load songs
        musicRegister.loadSongs();
    }

    private void loadRegions() {
        // Implementation to load regions and associate them with songs
        musicRegister.loadRegions();
    }
}
