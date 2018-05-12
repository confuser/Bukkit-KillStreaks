package me.confuser.killstreaks.storage;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerStorage {

  private HashMap<UUID, KillStreakPlayer> players;

  public PlayerStorage() {
    players = new HashMap<>();
  }

  public KillStreakPlayer get(Player player) {
    return players.get(player.getUniqueId());
  }

  public void add(Player player) {
    players.put(player.getUniqueId(), new KillStreakPlayer());
  }

  public void remove(Player player) {
    players.remove(player.getUniqueId());
  }

  public void reset(Player player) {
    reset(player.getUniqueId());
  }

  public void reset(UUID uuid) {
    players.get(uuid).reset();
  }
}
