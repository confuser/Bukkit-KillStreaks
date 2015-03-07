package me.confuser.killstreaks.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

import me.confuser.bukkitutil.listeners.Listeners;
import me.confuser.killstreaks.KillStreaks;
import me.confuser.killstreaks.configs.KillStreak;
import me.confuser.killstreaks.storage.PlayerStorage;

public class PlayerListener extends Listeners<KillStreaks>{
	private PlayerStorage playerStorage = plugin.getPlayerStorage();
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		playerStorage.add(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onQuit(PlayerQuitEvent event) {
		playerStorage.remove(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		playerStorage.reset(player);
		
		if (!(event.getEntity().getKiller() instanceof Player))
			return;
		
		Player killer = event.getEntity().getKiller();
		
		if (!killer.hasPermission("killstreaks.enabled")) return;
		
		playerStorage.addKill(killer);
		
		List<KillStreak> levels = plugin.getConfiguration().getLevelsConfig().getLevels(playerStorage.getKills(killer));
		
		if (!levels.isEmpty()) {
			for (KillStreak level : levels) {
				handleKillStreak(level, killer, player);
			}
		}
		
		KillStreak streak = plugin.getConfiguration().getStreaksConfig().getStreak(playerStorage.getKills(killer));
		
		if (streak != null) handleKillStreak(streak, killer, player);
	}
	
	private void handleKillStreak(KillStreak killStreak, Player killer, Player victim) {
		if (!killStreak.isEnabled()) return;
		
		if (!killStreak.getAnnouncement().isEmpty()) {
			String announcement = handleVariables(killStreak.getAnnouncement(), killer, victim);
			plugin.getServer().broadcast(announcement, "killstreaks.announcements");
		}
		
		for (String command : killStreak.getCommands()) {
			String cmd = handleVariables(command, killer, victim);
			
			ServerCommandEvent event = new ServerCommandEvent(plugin.getServer().getConsoleSender(), cmd);
			plugin.getServer().getPluginManager().callEvent(event);
			
			plugin.getServer().dispatchCommand(event.getSender(), event.getCommand());
		}
	}
	
	private String handleVariables(String str, Player killer, Player victim) {
		return str.replace("[name]", killer.getName())
		   .replace("[uuid]", killer.getUniqueId().toString())
		   .replace("[displayName]", killer.getDisplayName())
		   .replace("[world]", killer.getWorld().getName())
		   .replace("[victimName]", victim.getName())
		   .replace("[victimUuid]", victim.getUniqueId().toString())
		   .replace("[victimDisplayName]", victim.getDisplayName());

	}
}
