package org.mineacademy.component;

import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.remain.Remain;

import lombok.Getter;

@Getter
public class HoverEvent {

	private final String action;
	private final Object value;

	public HoverEvent(final String text) {
		this.action = "show_text";
		this.value = text;
	}

	public HoverEvent(final ItemStack item) {
		this.action = "show_item";
		this.value = Remain.toJson(item);
	}
}
