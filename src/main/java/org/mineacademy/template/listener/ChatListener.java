package org.mineacademy.template.listener;

import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.event.SimpleListener;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * An example of an advanced listener that enables customizing
 * priority and ignoreCanceled flag from a file.
 */
@AutoRegister
public final class ChatListener extends SimpleListener<AsyncPlayerChatEvent> {

	/**
	 * The instance of this class, hidden because the only call to this class is from
	 * our auto registration class.
	 */
	@Getter(value = AccessLevel.PRIVATE)
	private static final ChatListener instance = new ChatListener();

	/**
	 * Create a new chat listener, registered automatically
	 *
	 * @param event
	 */
	private ChatListener() {
		// You could potentially parse event priority and ignoreCancelled flag from a config
		super(AsyncPlayerChatEvent.class, EventPriority.NORMAL, true);
	}

	@Override
	protected void execute(AsyncPlayerChatEvent event) {
		event.setFormat(Common.colorize("&8[&dPluginTemplate&8] &7%s&8: &f%s"));
	}
}
