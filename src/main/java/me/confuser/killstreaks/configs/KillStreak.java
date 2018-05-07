package me.confuser.killstreaks.configs;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class KillStreak {

  @Getter
  private boolean enabled;
  @Getter
  private String announcement;
  @Getter
  private List<String> commands;
}
