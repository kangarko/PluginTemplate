package org.mineacademy.template.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mineacademy.fo.Messenger;
import org.mineacademy.template.PlayerCache;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A sample listener for events.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SampleListener implements Listener {

	/** 
	 * The singleton instance
	 */
	@Getter
	private static final SampleListener instance = new SampleListener();

	/**
	 * Listen for player join and loads his data
	 *
	 * @param event
	 */
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		PlayerCache.from(player); // Load player's cache
		Messenger.success(player, "Welcome to the game!");
	}
}
