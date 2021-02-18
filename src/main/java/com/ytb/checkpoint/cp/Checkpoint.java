package com.ytb.checkpoint.cp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.*;

public class Checkpoint {
    private String path;

    public Checkpoint(Plugin plugin) {
        this.path = plugin.getDataFolder().getAbsoluteFile().toString();

        // テーブルの作成
        String sql = "CREATE TABLE IF NOT EXISTS cpdata(uuid text, parkour text primary key, x real, y real, z real, pitch real, yaw real)";
        try(
                Connection con = DriverManager.getConnection("jdbc:sqlite:" + path + "/checkpoint.db");
                PreparedStatement prep = con.prepareStatement(sql);
                ) {

            prep.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCheckPoint(Player player) {
        String sql = "INSERT OR REPLACE INTO cpdata VALUES(?, ?, ?, ?, ?, ?, ?)";

        try(
                Connection con = DriverManager.getConnection("jdbc:sqlite:" + path + "/checkpoint.db");
                PreparedStatement prep = con.prepareStatement(sql);
                ) {

            prep.setString(1, player.getUniqueId().toString());
            prep.setString(2, player.getWorld().getName());
            prep.setDouble(3, player.getLocation().getX());
            prep.setDouble(4, player.getLocation().getY());
            prep.setDouble(5, player.getLocation().getZ());
            prep.setFloat(6, player.getLocation().getYaw());
            prep.setFloat(7, player.getLocation().getPitch());

            prep.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Location getCheckPoint(Player player) {
        String sql = "SELECT * FROM cpdata WHERE uuid = ? and parkour = ?";
        double x, y, z;
        float pitch, yaw;

        try(
                Connection con = DriverManager.getConnection("jdbc:sqlite:" + path + "/checkpoint.db");
                PreparedStatement prep = con.prepareStatement(sql);
                ) {

            prep.setString(1, player.getUniqueId().toString());
            prep.setString(2, player.getWorld().getName());

            ResultSet result = prep.executeQuery();
            if(result != null) {
                x = result.getDouble("x");
                y = result.getDouble("y");
                z = result.getDouble("z");
                pitch = result.getFloat("pitch");
                yaw = result.getFloat("yaw");

                return new Location(player.getWorld(), x, y, z, pitch, yaw);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
