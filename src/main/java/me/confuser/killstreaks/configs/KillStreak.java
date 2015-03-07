package me.confuser.killstreaks.configs;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class KillStreak {
	@Getter
	private boolean enabled;
	@Getter
	private String announcement;
	@Getter
	private List<String> commands;
}
