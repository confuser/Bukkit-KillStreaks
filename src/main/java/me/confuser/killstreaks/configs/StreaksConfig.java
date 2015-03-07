package me.confuser.killstreaks.configs;

import java.util.HashMap;
import java.util.List;

import me.confuser.killstreaks.KillStreaks;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class StreaksConfig {
	private HashMap<Integer, KillStreak> streaks = new HashMap<>();

	public StreaksConfig(ConfigurationSection conf) {

		for (String kill : conf.getKeys(false)) {
			int killInt;
			try {
				killInt = Integer.parseInt(kill);
			} catch (NumberFormatException e) {
				KillStreaks.getPlugin().getLogger().warning("Ignored " + kill + " streak, invalid number");
				continue;
			}

			ConfigurationSection levelConf = conf.getConfigurationSection(kill);

			boolean enabled = levelConf.getBoolean("enabled", false);
			String announcement = levelConf.getString("announcement", "");

			if (!announcement.isEmpty()) {
				announcement = ChatColor.translateAlternateColorCodes('&', announcement);
			}

			List<String> commands = levelConf.getStringList("commands");

			streaks.put(killInt, new KillStreak(enabled, announcement, commands));
		}
	}

	public KillStreak getStreak(int kills) {
		return streaks.get(kills);
	}

}
