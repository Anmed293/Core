/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby.listener;

import cloud.timo.TimoCloud.api.TimoCloudUniversalAPI;
import cloud.timo.TimoCloud.api.objects.PlayerObject;
import cloud.timo.TimoCloud.api.objects.ServerGroupObject;
import cloud.timo.TimoCloud.api.objects.ServerObject;
import de.mj.BattleBuild.lobby.Lobby;
import de.mj.BattleBuild.lobby.utils.Particle;
import de.mj.BattleBuild.lobby.utils.ServerManager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class CompassListener implements Listener {

    private final Lobby lobby;
    private final TimoCloudUniversalAPI universalAPI;

    private ServerManager serverManager;

    public CompassListener(Lobby lobby) {
        this.lobby = lobby;
        lobby.setListener(this);
        this.universalAPI = lobby.getHookManager().getTimoCloudUniversalAPI();
        this.serverManager = lobby.getServerManager();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent interactEvent) {

        Player player = interactEvent.getPlayer();
        if (player.isOp()) {
            interactEvent.setCancelled(false);
            return;
        } else {
            interactEvent.setCancelled(true);
        }
        if (interactEvent.getAction() == Action.RIGHT_CLICK_AIR | interactEvent.getAction() == Action.LEFT_CLICK_AIR
                | interactEvent.getAction() == Action.LEFT_CLICK_BLOCK | interactEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (interactEvent.getMaterial() == Material.COMPASS) {

                Inventory inv = Bukkit.createInventory(null, 54, "§8\u00BB§7§lNavigator§8\u00AB");

                for (int i = 8; i >= 0; i--) {
                    if (SettingsListener.design.containsKey(player)) {
                        inv.setItem(i, serverManager.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE,
                                SettingsListener.design.get(player), 1, null, null));
                    } else {
                        inv.setItem(i,
                                serverManager.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
                    }
                }
                ArrayList<String> BWlore = new ArrayList<>();
                if (universalAPI.getServerGroup("BedWars4x4") != null) {
                    ServerGroupObject serverGroupObjectBW4x4 = universalAPI.getServerGroup("BedWars4x4");
                    BWlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectBW4x4) + "§7 Spieler");
                    BWlore.add("§7online.");
                } else
                    BWlore.add("§cDiese Server sind derzeit offline.");
                inv.setItem(11, serverManager.getItemCreator().CreateItemwithMaterial(Material.BED, 0, 1, "§3§lBedWars", BWlore));

                ArrayList<String> CBlore = new ArrayList<>();
                if (universalAPI.getServerGroup("CityBuild") != null) {
                    ServerGroupObject serverGroupObjectCityBuild = universalAPI.getServerGroup("CityBuild");
                    CBlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectCityBuild) + "§7 Spieler");
                    CBlore.add("§7online.");
                } else
                    CBlore.add("§cDiese Server sind derzeit offline.");
                inv.setItem(15,
                        serverManager.getItemCreator().CreateItemwithMaterial(Material.DIAMOND_BLOCK, 0, 1, "§6§lCityBuild", CBlore));

                ArrayList<String> sPvPlore = new ArrayList<>();
                if (universalAPI.getServerGroup("SkyPvP") != null) {
                    ServerGroupObject serverGroupObjectCityBuild = universalAPI.getServerGroup("SkyPvP");
                    sPvPlore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectCityBuild) + "§7 Spieler");
                    sPvPlore.add("§7online.");
                } else
                    sPvPlore.add("§cDiese Server sind derzeit offline.");
                inv.setItem(19, serverManager.getItemCreator().CreateItemwithMaterial(Material.GRASS, 0, 1, "§9§lSkyPvP", sPvPlore));

                ArrayList<String> lobbyLore = new ArrayList<>();
                ServerGroupObject serverGroupObjectLobby = universalAPI.getServerGroup("Lobby");
                lobbyLore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectLobby) + "§7 Spieler");
                lobbyLore.add("§7online.");
                inv.setItem(22, serverManager.getItemCreator().CreateItemwithMaterial(Material.NETHER_STAR, 0, 1, "§a§lSpawn", lobbyLore));

                ArrayList<String> SW8x1lore = new ArrayList<>();
                if (universalAPI.getServerGroup("SkyWars8x1") != null) {
                    ServerGroupObject serverGroupObjectSW8x1 = universalAPI.getServerGroup("SkyWars8x1");
                    SW8x1lore.add("§7Derzeit sind §a" + getOnlinePlayerCount(serverGroupObjectSW8x1) + "§7 Spieler");
                    SW8x1lore.add("§7online.");
                } else
                    SW8x1lore.add("§cDiese Server sind derzeit offline.");
                inv.setItem(25, serverManager.getItemCreator().CreateItemwithMaterial(Material.IRON_SWORD, 0, 1, "§f§lSkyWars", SW8x1lore));
                inv.setItem(28,
                        serverManager.getItemCreator().CreateItemwithMaterial(Material.TNT, 0, 1, "§4§lT§f§lN§4§lT§f§l-§4§lRun", null));
                inv.setItem(34, serverManager.getItemCreator().CreateItemwithMaterial(Material.SIGN, 0, 1, "§8§lComing Soon", null));

                for (int a = 53; a >= 45; a--) {
                    if (SettingsListener.design.containsKey(player)) {
                        inv.setItem(a, serverManager.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE,
                                SettingsListener.design.get(player), 1, null, null));
                    } else {
                        inv.setItem(a,
                                serverManager.getItemCreator().CreateItemwithMaterial(Material.STAINED_GLASS_PANE, 0, 1, null, null));
                    }
                }

                interactEvent.getPlayer().openInventory(inv);
            }
        }

    }

    public Integer getOnlinePlayerCount(ServerGroupObject serverGroupObject) {
        Integer playerCount = 0;
        for (ServerObject serverObject : serverGroupObject.getServers()) {
            for (PlayerObject playerObject : serverObject.getOnlinePlayers()) {
                playerCount++;
            }
        }
        return playerCount;
    }

    @EventHandler
    public void onClick(InventoryClickEvent clickEvent) {
        Player player = (Player) clickEvent.getWhoClicked();
        if (player.isOp()) {
            clickEvent.setCancelled(false);
            return;
        } else {
            clickEvent.setCancelled(true);
        }
        if (clickEvent.getInventory().getName().contains("§8\u00BB§7§lNavigator§8\u00AB")) {

            if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§a§lSpawn")) {

                scheduler(player, serverManager.getLocationsUtil().getSpawn().add(0, 1, 0));
                player.sendMessage(serverManager.getData().getPrefix() + "Du wurdest zum §6Server-Spawn §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§6§lCityBuild")) {

                scheduler(player, serverManager.getLocationsUtil().getCitybuild().add(0, 1, 0));
                player.sendMessage(serverManager.getData().getPrefix() + "Du wurdest zu §6Citybuild §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§3§lBedWars")) {

                scheduler(player, serverManager.getLocationsUtil().getBedwars().add(0, 1, 0));
                player.sendMessage(serverManager.getData().getPrefix() + "Du wurdest zu §6Bedwars §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§9§lSkyPvP")) {

                scheduler(player, serverManager.getLocationsUtil().getGungame().add(0, 1, 0));
                player.sendMessage(serverManager.getData().getPrefix() + "Du wurdest zu §6SkyPvP §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§f§lSkyWars")) {

                scheduler(player, serverManager.getLocationsUtil().getSkywars().add(0, 1, 0));
                player.sendMessage(serverManager.getData().getPrefix() + "Du wurdest zu §6SkyWars §7teleportiert.");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();
                player.closeInventory();


            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§4§lT§f§lN§4§lT§f§l-§4§lRun")) {

                player.sendMessage(serverManager.getData().getPrefix() + "Dieser Modus ist noch nicht verfügbar!");
                Particle particle = new Particle(EnumParticle.FLAME, player.getLocation().add(0, 2.25, 0), true, 0.25f, 0.25f, 0.25f, 0, 10000);
                particle.sendAll();

            } else if (clickEvent.getCurrentItem().getItemMeta().getDisplayName().contains("§8Coming Soon")) {

                player.sendMessage(serverManager.getData().getPrefix() + "Dieser Modus ist noch nicht verfügbar!");

            }
        }
    }

    private void scheduler(Player player, Location location) {
        new BukkitRunnable() {
            int timer = 1;

            public void run() {
                if (timer == 1)
                    player.setVelocity(new Vector(0, 1, 0));
                if (timer == 0) {
                    player.teleport(location);
                    cancel();
                }
                timer--;
            }
        }.runTaskTimer(lobby, 0L, 20L);
    }
}
