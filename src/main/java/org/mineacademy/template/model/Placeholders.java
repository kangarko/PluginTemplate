package org.mineacademy.template.model;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mineacademy.fo.ReflectionUtil;
import org.mineacademy.fo.model.SimpleExpansion;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * A sample placeholder hook utilizing PlaceholderAPI but also working without it.
 * 
 * Without PAPI: simply use {test1}
 * With PAPI: you need to prepend the variable with your plugin name, such as {chatcontrol_test1}
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Placeholders extends SimpleExpansion {

	/**
	 * The singleton of this class
	 */
	@Getter
	private static final SimpleExpansion instance = new Placeholders();

	/**
	 * @see org.mineacademy.fo.model.SimpleExpansion#onReplace(org.bukkit.command.CommandSender, java.lang.String)
	 */
	@Override
	protected String onReplace(@NonNull CommandSender sender, String identifier) {
		final Player player = sender instanceof Player && ((Player) sender).isOnline() ? (Player) sender : null;

		//
		// Static variables,
		// implement your logic here
		//
		switch (identifier) {
			case "test1":
				return "Hello";
		}

		//
		// Dynamic variables, with an example,
		// implement your logic here
		//
		if (identifier.startsWith("player_has_gamemode_")) {

			// Fix for Discord or console sender
			if (player == null)
				return "false";

			final String gamemodeName = join(3);
			final GameMode gamemode = ReflectionUtil.lookupEnumSilent(GameMode.class, gamemodeName.toUpperCase());

			return gamemode == null ? "invalid" : player.getGameMode() == gamemode ? "true" : "false";
		}

		return NO_REPLACE;
	}
}
