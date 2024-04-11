package org.mineacademy.template;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class of this plugin.
 */
public final class PluginTemplate extends JavaPlugin implements Listener {

	/**
	 * Automatically called when the plugin starts or after /reload.
	 */
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	/**
	 * Automatically called when the server is stopped or on /reload.
	 */
	@Override
	public void onDisable() {
	}

	/* ------------------------------------------------------------------------------- */
	/* Events */
	/* ------------------------------------------------------------------------------- */

	/**
	 * An example event that checks if the right clicked entity is a cow, and makes an explosion.
	 * You can write your events to your main class without having to register a listener.
	 *
	 * @param event
	 */
	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getType() == EntityType.COW)
			event.getRightClicked().getWorld().createExplosion(event.getRightClicked().getLocation(), 5);
	}

	/* ------------------------------------------------------------------------------- */
	/* Static */
	/* ------------------------------------------------------------------------------- */

	/**
	 * Return the instance of this plugin
	 *
	 * @return
	 */
	public static PluginTemplate getInstance() {
		return PluginTemplate.getPlugin(PluginTemplate.class);
	}
}
