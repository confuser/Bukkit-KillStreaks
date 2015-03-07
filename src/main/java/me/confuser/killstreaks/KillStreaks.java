package me.confuser.killstreaks;

import lombok.Getter;
import me.confuser.bukkitutil.BukkitPlugin;
import me.confuser.killstreaks.commands.ReloadCommand;
import me.confuser.killstreaks.configs.DefaultConfig;
import me.confuser.killstreaks.configs.MessagesConfig;
import me.confuser.killstreaks.listeners.PlayerListener;
import me.confuser.killstreaks.storage.PlayerStorage;

public class KillStreaks extends BukkitPlugin {
	@Getter
	private static KillStreaks plugin;
	
	@Getter
	private DefaultConfig configuration;
	
	@Getter
	private PlayerStorage playerStorage;

	@Override
	public String getPermissionBase() {
		return "fckillstreaks";
	}

	@Override
	public String getPluginFriendlyName() {
		return "KillStreaks";
	}

	@Override
	public void onEnable() {
		plugin = this;
		
		setupConfigs();
		setupStorage();
		setupListeners();
		setupCommands();
	}

	@Override
	public void setupCommands() {
		new ReloadCommand().register();
	}

	@Override
	public void setupConfigs() {
		new MessagesConfig().load();

	    configuration = new DefaultConfig();
	    configuration.load();
	}
	
	public void setupStorage() {
		playerStorage = new PlayerStorage();
	}

	@Override
	public void setupListeners() {
		new PlayerListener().register();
	}

	@Override
	public void setupRunnables() {
	}

}
