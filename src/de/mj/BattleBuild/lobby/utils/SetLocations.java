/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.utils;

import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SetLocations {

    private static File file = new File("plugins/BBLobby/", "locations.yml");
    private static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    private final Lobby lobby;

    public SetLocations(Lobby lobby) {
        this.lobby = lobby;
    }

    private static Location initLocs(String path) {
        double x = yamlConfiguration.getDouble("bb." + path + ".x");
        double y = yamlConfiguration.getDouble("bb." + path + ".y");
        double z = yamlConfiguration.getDouble("bb." + path + ".z");
        float yaw = yamlConfiguration.getLong("bb." + path + ".yaw");
        float pitch = yamlConfiguration.getLong("bb." + path + ".pitch");
        Location loc = new Location(Bukkit.getWorld(yamlConfiguration.getString("bb.spawn.world")), x, y, z, yaw, pitch);
        return loc;
    }

    public void saveLocs() {
        lobby.getLocationsUtil().setBedwars(initLocs("bedwars"));
        lobby.getLocationsUtil().setCitybuild(initLocs("citybuild"));
        lobby.getLocationsUtil().setGungame(initLocs("gungame"));
        lobby.getLocationsUtil().setSkywars(initLocs("skywars"));
        lobby.getLocationsUtil().setSpawn(initLocs("spawn"));
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
