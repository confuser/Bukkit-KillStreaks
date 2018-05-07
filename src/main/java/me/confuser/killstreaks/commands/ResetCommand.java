package me.confuser.killstreaks.commands;

import me.confuser.bukkitutil.Message;
import me.confuser.bukkitutil.commands.BukkitCommand;
import me.confuser.killstreaks.KillStreaks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ResetCommand extends BukkitCommand<KillStreaks> {

  public ResetCommand() {
    super("ksreset");
  }

  @Override
  public boolean onCommand(final CommandSender sender, Command command, String commandName, String[] args) {
    if (args.length != 1) return false;

    final String playerName = args[0];
    final boolean isUUID = playerName.length() > 16;
    Player player;

    if (isUUID) {
      player = Bukkit.getPlayer(UUID.fromString(playerName));
    } else {
      player = Bukkit.getPlayer(playerName);
    }

    if (player == null) {
      Message.get("playerNotFound").sendTo(sender);
      return true;
    }

    plugin.getPlayerStorage().reset(player);

    Message.get("playerReset").sendTo(sender);

    return true;
  }


}
