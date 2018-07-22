package me.confuser.killstreaks.listeners;

import me.confuser.bukkitutil.Message;
import me.confuser.bukkitutil.listeners.Listeners;
import me.confuser.killstreaks.KillStreaks;
import me.confuser.killstreaks.configs.KillStreak;
import me.confuser.killstreaks.storage.KillStreakPlayer;
import me.confuser.killstreaks.storage.PlayerStorage;
import me.confuser.killstreaks.storage.VictimPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.List;

public class PlayerListener extends Listeners<KillStreaks> {

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
    KillStreakPlayer ksVictim = playerStorage.get(player);
    final boolean hadStreak = ksVictim.isStreaksAwarded();
    final int kills = ksVictim.getKills().intValue();

    playerStorage.reset(player);

    if (!(event.getEntity().getKiller() instanceof Player)) return;

    Player killer = event.getEntity().getKiller();

    if (killer.getUniqueId().equals(player.getUniqueId())) return;
    if (!killer.hasPermission("killstreaks.enabled")) return;

    KillStreakPlayer ksKiller = playerStorage.get(killer);

    if (hadStreak) {
      String msg = Message.get("streakEnded").set("kills", kills).toString();
      String announcement = handleVariables(msg, killer, player);

      if (!announcement.isEmpty()) {
        plugin.getServer().broadcast(announcement, "killstreaks.announcements");
      }
    }

    if (plugin.getConfiguration().getKillAbuseConfig().isEnabled()) {
      VictimPlayer victim = ksKiller.getVictim(player);
      boolean withinLimit = System.currentTimeMillis() - victim.getLastKilledAt() >= plugin.getConfiguration()
                                                                                           .getKillAbuseConfig()
                                                                                           .getDuration();
      if (withinLimit && victim.getDeaths().intValue() != plugin.getConfiguration().getKillAbuseConfig().getMaxKills()) {
        victim.getDeaths().increment();
        victim.setLastKilledAt(System.currentTimeMillis());
        ksKiller.getKills().increment();

        if (victim.getDeaths().intValue() == plugin.getConfiguration().getKillAbuseConfig().getMaxKills()) {
          victim.getDeaths().setValue(0);
        }
      } else {
        return;
      }
    } else {
      ksKiller.getKills().increment();
    }

    List<KillStreak> levels = plugin.getConfiguration().getLevelsConfig().getLevels(ksKiller.getKills().intValue());

    if (!levels.isEmpty()) {
      for (KillStreak level : levels) {
        handleKillStreak(level, killer, player);
      }
    }

    KillStreak streak = plugin.getConfiguration().getStreaksConfig().getStreak(ksKiller.getKills().intValue());

    if (streak != null) {
      ksKiller.setStreaksAwarded(true);
      handleKillStreak(streak, killer, player);
    }
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
