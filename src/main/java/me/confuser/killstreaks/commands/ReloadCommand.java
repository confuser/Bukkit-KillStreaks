package me.confuser.killstreaks.commands;

import me.confuser.bukkitutil.Message;
import me.confuser.bukkitutil.commands.BukkitCommand;
import me.confuser.killstreaks.KillStreaks;
import me.confuser.killstreaks.configs.MessagesConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends BukkitCommand<KillStreaks> {

  public ReloadCommand() {
    super("ksreload");
  }

  @Override
  public boolean onCommand(final CommandSender sender, Command command, String commandName, String[] args) {
    plugin.getConfiguration().load();
    new MessagesConfig().load();

    sender.sendMessage(Message.get("configReloaded").toString());

    return true;
  }


}
