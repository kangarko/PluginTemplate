package org.mineacademy.template.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.ItemUtil;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.MenuTools;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompMonsterEgg;
import org.mineacademy.template.model.CustomDataStorage;
import org.mineacademy.template.model.SampleEnchant;
import org.mineacademy.template.model.SampleTool;

/**
 * A sample menu.
 */
public final class SampleMenu extends Menu {

	/**
	 * Sample buttons to demostrate different Foundation features.
	 */
	private final Button sampleButton;
	private final Button sampleSecondButton;
	private final Button sampleMenuButton;
	private final Button sampleToolsButton;

	/*
	 * Create a new menu, called using a static method below
	 */
	private SampleMenu() {

		setTitle("Sample Menu");
		setSize(9 * 4);

		// Create a new button with click handler
		this.sampleButton = Button.makeSimple(ItemCreator.of(CompMaterial.APPLE), player -> {
			animateTitle("Received item with custom enchant");

			ItemCreator.of(CompMaterial.DIAMOND_SWORD).enchant(SampleEnchant.getInstance()).give(player);
		});

		// Create a new button with anonymous class, showing how to connect it with settings
		this.sampleSecondButton = new Button() {

			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				restartMenu((CustomDataStorage.getInstance().switchDemoValue() ? "Enabled" : "Disabled") + " demo value");

			}

			@Override
			public ItemStack getItem() {

				final boolean hasValue = CustomDataStorage.getInstance().isDemoValue();

				return ItemCreator
						.of(CompMaterial.DIAMOND,
								"Diamond",
								"",
								"Demo value: " + hasValue)
						.glow(hasValue)
						.make();
			}
		};

		// Create a new button that will open another menu
		this.sampleMenuButton = new ButtonMenu(new SamplePaggedMenu(this), ItemCreator.of(
				CompMaterial.COAL,
				"Open menu",
				"",
				"Click to open",
				"another menu."));

		// Create a new button that will open a premade menu of tools
		this.sampleToolsButton = new ButtonMenu(new MenuTools() {

			@Override
			protected Object[] compileTools() {
				return new Object[] {
						SampleTool.getInstance()
				};
			}
		}, ItemCreator.of(CompMaterial.GOLDEN_AXE, "Tools Menu"));

	}

	/**
	 * @see org.mineacademy.fo.menu.Menu#getItemAt(int)
	 */
	@Override
	public ItemStack getItemAt(int slot) {

		if (slot == 9 + 1)
			return this.sampleButton.getItem();

		if (slot == 9 + 3)
			return this.sampleSecondButton.getItem();

		if (slot == 9 + 5)
			return this.sampleMenuButton.getItem();

		if (slot == 9 + 7)
			return this.sampleToolsButton.getItem();

		return null;
	}

	/**
	 * @see org.mineacademy.fo.menu.Menu#getInfo()
	 */
	@Override
	protected String[] getInfo() {

		return new String[] {
				"This is a sample menu which has",
				"a sample button and a submenu."
		};
	}

	/**
	 * Show this menu to the given player
	 *
	 * @param player
	 */
	public static void showTo(Player player) {
		new SampleMenu().displayTo(player);
	}

	/* ------------------------------------------------------------------------------- */
	/* Subclasses */
	/* ------------------------------------------------------------------------------- */

	/**
	 * A sample pagged menu listing different items of the given type.
	 */
	private final class SamplePaggedMenu extends MenuPagged<EntityType> {

		/*
		 * Create a new instance of this menu that will automatically add "Return Back"
		 * button pointing to the parent menu.
		 */
		private SamplePaggedMenu(Menu parent) {
			super(parent, compileItems());
		}

		/**
		 * @see org.mineacademy.fo.menu.MenuPagged#convertToItemStack(java.lang.Object)
		 */
		@Override
		protected ItemStack convertToItemStack(EntityType item) {
			return CompMonsterEgg.makeEgg(item);
		}

		/**
		 * @see org.mineacademy.fo.menu.MenuPagged#onPageClick(org.bukkit.entity.Player, java.lang.Object, org.bukkit.event.inventory.ClickType)
		 */
		@Override
		protected void onPageClick(Player player, EntityType item, ClickType click) {
			player.getInventory().addItem(CompMonsterEgg.makeEgg(item));

			animateTitle("Added " + ItemUtil.bountifyCapitalized(item) + " to inventory!");
		}

		/**
		 * Return null to hide the info icon.
		 *
		 * @see org.mineacademy.fo.menu.Menu#getInfo()
		 */
		@Override
		protected String[] getInfo() {
			return null;
		}
	}

	/*
	 * Compile a list of valid items here
	 */
	private static List<EntityType> compileItems() {
		final List<EntityType> list = new ArrayList<>();

		for (final EntityType type : EntityType.values())

			// Still, some of the invalid eggs will slip through
			if (type.isSpawnable() && type.isAlive())
				list.add(type);

		return list;
	}

}
