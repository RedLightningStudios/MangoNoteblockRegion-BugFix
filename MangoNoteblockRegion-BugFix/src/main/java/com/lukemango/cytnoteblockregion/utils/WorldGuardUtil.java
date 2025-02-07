package com.lukemango.cytnoteblockregion.utils;

import com.lukemango.cytnoteblockregion.CYTNoteblockRegion;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public class WorldGuardUtil {
    CYTNoteblockRegion plugin;
    private final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    private BukkitTask taskID;

    public WorldGuardUtil(CYTNoteblockRegion plugin) {
        this.plugin = plugin;
    }

    public void cancelTask() {
        if (taskID != null) {
            taskID.cancel();
        }
    }

    public void stopPlaying() {
        for (RadioSongPlayer songPlayer : plugin.getMusicManager().getRegionSongs().values()) {
            songPlayer.setPlaying(false);
        }
    }

    public void startCheckingPlayers() {
        taskID = new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    com.sk89q.worldedit.util.Location wgLocation = BukkitAdapter.adapt(player.getLocation());
                    RegionQuery query = container.createQuery();
                    ApplicableRegionSet set = query.getApplicableRegions(wgLocation);

                    RegionSongPlayer highestPrioritySong = null;

                    // Stop all songs for players not in any regions
                    if (set.getRegions().isEmpty()) {
                        for (RadioSongPlayer songPlayer : plugin.getMusicManager().getRegionSongs().values()) {
                            songPlayer.removePlayer(player);
                        }
                    } else {
                        // Determine the highest priority song
                        for (ProtectedRegion rg : set) {
                            if (plugin.getMusicManager().getRegionSongs().containsKey(rg)) {
                                RadioSongPlayer songPlayer = plugin.getMusicManager().getRegionSongs().get(rg);
                                if (highestPrioritySong == null || rg.getPriority() > highestPrioritySong.getRegion().getPriority()) {
                                    highestPrioritySong = new RegionSongPlayer(rg, songPlayer);
                                }
                            }
                        }

                        // Play the highest priority song for the player
                        if (highestPrioritySong != null) {
                            highestPrioritySong.getSongPlayer().addPlayer(player);
                        }

                        // Remove the player from all other songs
                        for (RadioSongPlayer songPlayer : plugin.getMusicManager().getRegionSongs().values()) {
                            if (highestPrioritySong == null || !songPlayer.equals(highestPrioritySong.getSongPlayer())) {
                                songPlayer.removePlayer(player);
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 10);
    }
}
