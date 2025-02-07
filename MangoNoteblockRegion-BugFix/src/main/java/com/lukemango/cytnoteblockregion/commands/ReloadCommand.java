package com.lukemango.cytnoteblockregion.commands;

import com.lukemango.cytnoteblockregion.CYTNoteblockRegion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private CYTNoteblockRegion plugin;

    public ReloadCommand(CYTNoteblockRegion plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !sender.hasPermission("cytnoteblockregion.reload")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }
        
        if (sender instanceof Player || sender instanceof ConsoleCommandSender) {
            plugin.reloadConfig();
            sender.sendMessage("CYTNoteblockRegion config reloaded!");
            return true;
        }
        return false;
    }
}
