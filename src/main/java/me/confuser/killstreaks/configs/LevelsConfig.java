package me.confuser.killstreaks.configs;

import me.confuser.killstreaks.KillStreaks;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelsConfig {

  private HashMap<Integer, KillStreak> levels = new HashMap<>();

  public LevelsConfig(ConfigurationSection conf) {

    for (String kill : conf.getKeys(false)) {
      int killInt;
      try {
        killInt = Integer.parseInt(kill);
      } catch (NumberFormatException e) {
        KillStreaks.getPlugin().getLogger().warning("Ignored " + kill + " level, invalid number");
        continue;
      }

      ConfigurationSection levelConf = conf.getConfigurationSection(kill);

      boolean enabled = levelConf.getBoolean("enabled", false);
      String announcement = levelConf.getString("announcement", "");

      if (!announcement.isEmpty()) {
        announcement = ChatColor.translateAlternateColorCodes('&', announcement);
      }

      List<String> commands = levelConf.getStringList("commands");

      levels.put(killInt, new KillStreak(enabled, announcement, commands));
    }
  }

  public List<KillStreak> getLevels(int kills) {
    ArrayList<KillStreak> levels = new ArrayList<KillStreak>();

    for (int level : this.levels.keySet()) {
      if (kills % level == 0) {
        levels.add(this.levels.get(level));
      }
    }

    return levels;
  }

}
