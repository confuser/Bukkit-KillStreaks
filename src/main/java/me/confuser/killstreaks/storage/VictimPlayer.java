package me.confuser.killstreaks.storage;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.mutable.MutableInt;

public class VictimPlayer {

  @Getter
  @Setter
  private long lastKilledAt;
  @Getter
  private MutableInt deaths;

  public VictimPlayer() {
    lastKilledAt = 0;
    deaths = new MutableInt();
  }
}
