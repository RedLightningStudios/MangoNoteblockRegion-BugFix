package com.lukemango.cytnoteblockregion.utils;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;

public class RegionSongPlayer {
    private final ProtectedRegion region;
    private final RadioSongPlayer songPlayer;

    public RegionSongPlayer(ProtectedRegion region, RadioSongPlayer songPlayer) {
        this.region = region;
        this.songPlayer = songPlayer;
    }

    public ProtectedRegion getRegion() {
        return region;
    }

    public RadioSongPlayer getSongPlayer() {
        return songPlayer;
    }
}
