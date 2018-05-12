package me.confuser.killstreaks.storage;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class KillStreakPlayer {

  @Getter
  private MutableInt kills;
  @Getter
  @Setter
  private boolean streaksAwarded;
  private HashMap<UUID, VictimPlayer> victims;

  public KillStreakPlayer() {
    kills = new MutableInt();
    streaksAwarded = false;
    victims = new HashMap<>();
  }

  public VictimPlayer getVictim(Player player) {
    VictimPlayer victim = victims.get(player.getUniqueId());

    if (victim != null) return victim;

    victim = new VictimPlayer();
    victims.put(player.getUniqueId(), victim);

    return victim;
  }

  public void reset() {
    kills.setValue(0);
    streaksAwarded = false;
  }
}