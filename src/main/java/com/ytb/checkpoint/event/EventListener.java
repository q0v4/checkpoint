package com.ytb.checkpoint.event;

import com.ytb.checkpoint.cp.Checkpoint;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.Statement;

import static org.bukkit.Bukkit.getServer;

public class EventListener implements Listener {
    private Plugin plugin;

    private ItemStack returner;
    private Connection con = null;
    private Statement stat = null;

    public EventListener(Plugin plugin) {
        getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;

        // cp returner用のItemStackを生成(Dye)
        this.returner = new ItemStack(Material.INK_SACK, 1, (short)1);
        // メタの取得
        ItemMeta returner_meta = returner.getItemMeta();
        // 変更
        returner_meta.setDisplayName("§o§b returner "); // 名前
        // 保存
        returner.setItemMeta(returner_meta);
        }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // 参加時にreturnerを持っていなかった場合付与
        if(!player.getInventory().contains(returner)) {
            player.getInventory().addItem(returner);
        }
    }

    @EventHandler
    public boolean onUseItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack hold_item = player.getInventory().getItemInMainHand();
        Checkpoint cp = new Checkpoint(plugin);
        Location location = cp.getCheckPoint(player);

        // 本処理
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(hold_item.isSimilar(returner)) {
                // nullチェック
                if(location == null) {
                    player.sendMessage("§cCPが設定されていません！");
                    return true;
                }

                player.teleport(location);
            }
        }

        return true;
    }
}
