package me.confuser.killstreaks.configs;

import me.confuser.bukkitutil.Message;
import me.confuser.bukkitutil.configs.Config;
import me.confuser.killstreaks.KillStreaks;

public class MessagesConfig extends Config<KillStreaks> {

  public MessagesConfig() {
    super("messages.yml");
  }

  public void afterLoad() {
    Message.load(conf);
  }

  public void onSave() {

  }
}
