package com.ytb.checkpoint;

import com.ytb.checkpoint.command.SetCheckPoint;
import com.ytb.checkpoint.event.EventListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        File file = new File(getDataFolder().toString());
        if(file.mkdir()) {
            getLogger().info("Created data file.");
        }
        new EventListener(this);
        this.getCommand("setcp").setExecutor(new SetCheckPoint(this));
    }
}
