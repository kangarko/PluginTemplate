package org.mineacademy.template.api;

import org.bukkit.event.HandlerList;
import org.mineacademy.fo.event.SimpleCancellableEvent;
import org.mineacademy.fo.event.SimpleEvent;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * A sample event extending {@link SimpleEvent}, you can also extend {@link SimpleCancellableEvent}
 * for convenience
 */
@Getter
@Setter
@RequiredArgsConstructor
public final class SampleEvent extends SimpleEvent {

	private static final HandlerList handlers = new HandlerList();

	//
	// A sample field parsed to this event
	//
	// private final Player player;

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}