package me.confuser.killstreaks.storage;

import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerStorage {

  private HashMap<UUID, MutableInt> players;

  public PlayerStorage() {
    players = new HashMap<>();
  }

  public void add(Player player) {
    players.put(player.getUniqueId(), new MutableInt());
  }

  public void remove(Player player) {
    players.remove(player.getUniqueId());
  }

  public void addKill(Player player) {
    players.get(player.getUniqueId()).increment();
  }

  public int getKills(Player player) {
    return players.get(player.getUniqueId()).intValue();
  }

  public void reset(Player player) {
    reset(player.getUniqueId());
  }

  public void reset(UUID uuid) {
    players.get(uuid).setValue(0);
  }
}
