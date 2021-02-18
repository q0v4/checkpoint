package com.ytb.checkpoint.command;

import com.ytb.checkpoint.cp.Checkpoint;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetCheckPoint implements CommandExecutor {
    private Plugin plugin;

    public SetCheckPoint(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("setcp")) {
            Checkpoint cp = new Checkpoint(plugin);
            cp.setCheckPoint((Player) sender);
            sender.sendMessage("cp set!");
        }
        return true;
    }
}
