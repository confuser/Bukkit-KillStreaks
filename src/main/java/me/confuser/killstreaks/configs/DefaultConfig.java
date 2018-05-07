package me.confuser.killstreaks.configs;

import lombok.Getter;
import me.confuser.bukkitutil.configs.Config;
import me.confuser.killstreaks.KillStreaks;

public class DefaultConfig extends Config<KillStreaks> {

  @Getter
  private LevelsConfig levelsConfig;
  @Getter
  private StreaksConfig streaksConfig;

  public DefaultConfig() {
    super("config.yml");
  }

  @Override
  public void afterLoad() {
    levelsConfig = new LevelsConfig(conf.getConfigurationSection("levels"));
    streaksConfig = new StreaksConfig(conf.getConfigurationSection("streaks"));
  }

  @Override
  public void onSave() {
  }

}
