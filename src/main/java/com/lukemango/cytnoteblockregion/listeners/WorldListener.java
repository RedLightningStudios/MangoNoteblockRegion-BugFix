package com.lukemango.cytnoteblockregion.listeners;

import com.lukemango.cytnoteblockregion.CYTNoteblockRegion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldListener implements Listener {

    private final CYTNoteblockRegion plugin;

    public WorldListener(CYTNoteblockRegion plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        String worldName = event.getWorld().getName();
        plugin.getMusicManager().getMusicRegister().loadWorldRegions(worldName);
    }
}