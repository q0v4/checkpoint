package com.ytb.checkpoint;

import com.ytb.checkpoint.command.SetCheckPoint;
import com.ytb.checkpoint.event.EventListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // データファイルの生成
        File file = new File(getDataFolder().toString());
        if(file.mkdir()) {
            getLogger().info("Created data file.");
        }

        // イベントリスナークラスの初期化
        new EventListener(this);
        //コマンドの登録
        this.getCommand("setcp").setExecutor(new SetCheckPoint(this));
    }
}
