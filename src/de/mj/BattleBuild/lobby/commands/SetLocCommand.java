/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.commands;

import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.utils.ServerManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocCommand implements CommandExecutor {

    private final Lobby lobby;

    public SetLocCommand(Lobby lobby) {
        this.lobby = lobby;
        lobby.setCommand(this, "set");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission("lobby.setlocs")) {
                if (strings.length == 1) {
                    setLocation(strings[0], player);
                }
            }
        }
        return false;
    }

    public void setLocation(String path, Player player) {
        Location loc = player.getLocation();
        ServerManager serverManager = lobby.getServerManager();
        serverManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".world", loc.getWorld().getName());
        serverManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".world", loc.getWorld().getName());
        serverManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".x", loc.getX());
        serverManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".y", loc.getY());
        serverManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".z", loc.getZ());
        serverManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".yaw", loc.getYaw());
        serverManager.getSetLocations().getYamlConfiguration().set("bb." + path + ".pitch", loc.getPitch());
        try {
            serverManager.getSetLocations().getYamlConfiguration().save(serverManager.getSetLocations().getFile());
            player.sendMessage(serverManager.getData().getPrefix() + "§aLocation wurde erfolgreich gespeichert!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
