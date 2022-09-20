package org.mineacademy.template.api;

import org.bukkit.entity.Player;
import org.mineacademy.template.PlayerCache;
import org.mineacademy.template.PluginTemplate;

/**
 * A sample API class you can build on to use for your plugin.
 */
public final class SampleAPI {

	/**
	 * Return the main instance of this plugin
	 *
	 * @return
	 */
	public static PluginTemplate getAPI() {
		return PluginTemplate.getInstance();
	}

	/**
	 * Get the player cache. Creates the cache if it does not exist.
	 *
	 * Please use with caution since we do create this cache on PlayerJoinEvent
	 * when the player joins automatically.
	 *
	 * @param player
	 * @return
	 */
	public static PlayerCache getPlayerCache(Player player) {
		return PlayerCache.from(player);
	}
}
