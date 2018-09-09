/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.mysql;


import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.utils.PlayerLevel;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.UUID;

public class SettingsAPI {

    private final CoreSpigot coreSpigot;
    private AsyncMySQL amsql;

    public SettingsAPI(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        amsql = coreSpigot.getServerManager().getAsyncMySQL();
    }

    public void createPlayer(Player p) {
        UUID uuid = p.getUniqueId();
        AsyncMySQL.update("INSERT INTO LobbyConf (UUID, COLOR, WJUMP, PJUMP, SILENT, RIDE, DJUMP) SELECT '" + uuid + "', '1', '1', '1', '1', '1', '1' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM LobbyConf WHERE UUID = '" + uuid + "');");
    }

    public void createScorePlayer(Player p) {
        UUID uuid = p.getUniqueId();
        AsyncMySQL.update("INSERT INTO ScoreConf (UUID, FRIENDS, RANG, SERVER, CLAN, COINS, TIME, REALTIME, WEATHER) SELECT '" + uuid + "', '1', '1', '1', '1', '1', '1', '1', '1' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM ScoreConf WHERE UUID = '" + uuid + "');");
    }

    public void getColor(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        if (rs.next()) {
                            short c = rs.getShort("COLOR");
                            coreSpigot.getServerManager().getSettingsListener().getDesign().put(p, c);
                            coreSpigot.getServerManager().getSettingsListener().ItemColToString(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getSilent(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("SILENT"));
                        }
                        if ((b = rs.getInt("SILENT")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getSilentState().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getSilentState().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getWjump(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("WJUMP"));
                        }
                        if ((b = rs.getInt("WJUMP")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getWaterJump().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getWaterJump().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getJumPlate(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("PJUMP"));
                        }
                        if ((b = rs.getInt("PJUMP")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getJumpPads().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getJumpPads().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getDoubleJump(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("DJUMP"));
                        }
                        if ((b = rs.getInt("DJUMP")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getDoubleJump().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getDoubleJump().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getRide(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("RIDE"));
                        }
                        int b = rs.getInt("RIDE");
                        if (b == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getRideState().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getRideState().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getLevel(Player player) {
        UUID uuid = player.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    Integer.valueOf(resultSet.getInt("LEVEL"));
                }
                int b = resultSet.getInt(("LEVEL"));
                coreSpigot.getServerManager().getSettingsListener().getPlayerLevel().remove(player);
                if (b == 0)
                    coreSpigot.getServerManager().getSettingsListener().getPlayerLevel().put(player, PlayerLevel.LOBBY);
                else if (b == 1)
                    coreSpigot.getServerManager().getSettingsListener().getPlayerLevel().put(player, PlayerLevel.SCROLL);
                else if (b == 2)
                    coreSpigot.getServerManager().getSettingsListener().getPlayerLevel().put(player, PlayerLevel.YEAR);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void setColor(Player p, short i) {
        UUID uuid = p.getUniqueId();
        AsyncMySQL.update("UPDATE LobbyConf SET COLOR='" + i + "' WHERE UUID='" + uuid + "'");
    }

    public void setSilent(Player p, boolean silent) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = silent ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET SILENT='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setRide(Player p, boolean ride) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = ride ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET RIDE='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setWJUMP(Player p, boolean wjump) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = wjump ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET WJUMP='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setPJUMP(Player p, boolean pjump) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = pjump ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET PJUMP='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setDJUMP(Player p, boolean djump) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = djump ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET DJUMP='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setLEVEL(Player player, PlayerLevel playerLevel) {
        UUID uuid = player.getUniqueId();
        if (playerLevel.equals(PlayerLevel.LOBBY))
            AsyncMySQL.update("UPDATE LobbyConf SET LEVEL='" + 0 + "' WHERE UUID='" + uuid + "'");
        else if (playerLevel.equals(PlayerLevel.SCROLL))
            AsyncMySQL.update("UPDATE LobbyConf SET LEVEL='" + 1 + "' WHERE UUID='" + uuid + "'");
        else if (playerLevel.equals(PlayerLevel.YEAR))
            AsyncMySQL.update("UPDATE LobbyConf SET LEVEL='" + 2 + "' WHERE UUID='" + uuid + "'");
    }

    public void getFriends(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("FRIENDS"));
                        }
                        if ((b = rs.getInt("FRIENDS")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getScoreFriends().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getScoreFriends().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getRang(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("RANG"));
                        }
                        if ((b = rs.getInt("RANG")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getScoreRank().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getScoreRank().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getServer(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("SERVER"));
                        }
                        if ((b = rs.getInt("SERVER")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getScoreServer().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getScoreServer().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getClan(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("CLAN"));
                        }
                        if ((b = rs.getInt("CLAN")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getScoreClan().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getScoreClan().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getCoins(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("COINS"));
                        }
                        if ((b = rs.getInt("COINS")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getScoreCoins().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getScoreCoins().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getRealTime(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("REALTIME"));
                        }
                        if ((b = rs.getInt("REALTIME")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getRealTime().add(p);
                        } else if (b == 2) {
                            coreSpigot.getServerManager().getSettingsListener().getRealTime().remove(p);
                            coreSpigot.getServerManager().getSettingsListener().getDay().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getRealTime().remove(p);
                            coreSpigot.getServerManager().getSettingsListener().getDay().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getTime(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("TIME"));
                        }
                        if ((b = rs.getInt("TIME")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getTime().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getTime().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getWeather(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("WEATHER"));
                        }
                        if ((b = rs.getInt("WEATHER")) == 1) {
                            coreSpigot.getServerManager().getSettingsListener().getWeather().add(p);
                        } else {
                            coreSpigot.getServerManager().getSettingsListener().getWeather().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void setFriends(Player p, boolean friends) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = friends ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET FRIENDS='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setRang(Player p, boolean rang) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = rang ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET RANG='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setServer(Player p, boolean server) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = server ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET SERVER='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setClan(Player p, boolean clan) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = clan ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET CLAN='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setCoins(Player p, boolean coins) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = coins ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET COINS='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setRealTime(Player p, boolean realtime, boolean day) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = realtime ? 1 : (day ? 2 : 0);
        AsyncMySQL.update("UPDATE ScoreConf SET REALTIME='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setWeather(Player p, boolean weather) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = weather ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET WEATHER='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setTime(Player p, boolean time) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = time ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET TIME='" + b + "' WHERE UUID='" + uuid + "'");
    }
}
