package me.confuser.killstreaks.configs;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

public class KillAbuseConfig {

  @Getter
  private boolean enabled;
  @Getter
  private int maxKills = 0;
  @Getter
  private long duration = 0;

  public KillAbuseConfig(ConfigurationSection conf) {
    enabled = conf.getBoolean("enabled", false);
    maxKills = conf.getInt("maxKills", 0);
    duration = conf.getInt("duration", 0) * 1000L;
  }
}
