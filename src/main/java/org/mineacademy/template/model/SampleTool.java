package org.mineacademy.template.model;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.tool.Tool;
import org.mineacademy.fo.remain.CompMaterial;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * An automatically registered tool you can use in the game
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SampleTool extends Tool {

	/**
	 * The singular tool instance
	 */
	@Getter
	private static final Tool instance = new SampleTool();

	/**
	 * The actual item stored here for maximum performance
	 */
	private ItemStack item;

	/**
	 * @see org.mineacademy.fo.menu.tool.Tool#getItem()
	 */
	@Override
	public ItemStack getItem() {

		if (this.item == null)
			this.item = ItemCreator.of(
					CompMaterial.GOLDEN_AXE,
					"Sample Tool",
					"",
					"Click a block to",
					"demostrate tool",
					"listener ability.")
					.make();

		return this.item;
	}

	/**
	 * @see org.mineacademy.fo.menu.tool.Tool#onBlockClick(org.bukkit.event.player.PlayerInteractEvent)
	 */
	@Override
	protected void onBlockClick(PlayerInteractEvent event) {

		//
		// Handle tool logic here,
		//

		Messenger.success(event.getPlayer(), "Clicked " + event.getAction() + " with " + this.item.getType() + " tool!");
	}

	/**
	 * Cancel the event so that we don't destroy blocks when selecting them
	 *
	 * @see org.mineacademy.fo.menu.tool.Tool#autoCancel()
	 */
	@Override
	protected boolean autoCancel() {
		return true;
	}
}
