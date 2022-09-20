package org.mineacademy.template.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.settings.ConfigItems;
import org.mineacademy.fo.settings.YamlConfig;

import lombok.Getter;
import lombok.NonNull;

/**
 * If you want to store your settings for a model instance in separate files,
 * this class shows you how to achieve it.
 *
 * For example, in our Boss plugin, each monster is stored in a separate file
 * in the bosses/ folder by its name, such as bosses/Musk.yml, bosses/Wrath.yml etc.
 */
@Getter
public final class PerFileDataStorage extends YamlConfig {

	/**
	 * The folder name where all items are stored
	 */
	private static final String FOLDER = "items";

	/**
	 * The config helper instance which loads and saves items
	 */
	private static final ConfigItems<PerFileDataStorage> loadedFiles = ConfigItems.fromFolder(FOLDER, PerFileDataStorage.class);

	/*
	 * A random property which is put into this instance when creating it from within the game
	 *
	 * Such as for Bosses, you type /boss new <bossName> <entityType> so the entity type would be the type,
	 * it needs to be placed into the constructor since you need to save both the Boss name and its type
	 * when creating it.
	 */
	private String type;

	/*
	 * Any other config key, see CustomDataStorage for more info.
	 */
	private String extraInfo;

	/*
	 * Loads a disk file, used when loading from disk
	 */
	private PerFileDataStorage(String name) {
		this(name, null);
	}

	/*
	 * Create a new file, used when creating new and you can pass through more data
	 */
	private PerFileDataStorage(String name, @Nullable String type) {
		this.type = type;

		this.setHeader(
				Common.configLine(),
				"This file stores information about a single entry, such as an arena or a mob by its name.",
				Common.configLine() + "\n");

		this.loadConfiguration(NO_DEFAULT, FOLDER + "/" + name + ".yml");
	}

	/**
	 * @see org.mineacademy.fo.settings.YamlConfig#onLoad()
	 */
	@Override
	protected void onLoad() {

		// Only load if not created via command

		// Skip loading if this class was instantiated from createItem() method below
		// because in this case there are no default keys (if you decide to use default
		// keys, make sure to edit this condition to the lines below and check if the Type
		// is only loaded when it is not passed through from the creation.
		//
		// if (this.type == null)
		//   this.type = getString("Type");
		//
		if (this.type != null) {
			this.save();

			return;
		}

		// Remove this line and use the two lines above in the comment if you want to use defaults
		this.type = this.getString("Type");
		this.extraInfo = this.getString("Extra_Info");
	}

	/**
	 * @see org.mineacademy.fo.settings.YamlConfig#serialize()
	 */
	@Override
	protected void onSave() {
		this.set("Type", this.type);
		this.set("Extra_Info", this.extraInfo);
	}

	// EXAMPLE: If you ever use Location object, make sure to return a clone of it otherwise it will get edited when
	// 			you call setX() methods for it!
	//public Location getLocation() {
	//	return this.location != null ? this.location.clone() : null;
	//}

	/* ------------------------------------------------------------------------------- */
	/* Static */
	/* ------------------------------------------------------------------------------- */

	/**
	 * @see ConfigItems#loadOrCreateItem(String, java.util.function.Supplier)
	 * @param name
	 * @param type
	 * @return
	 */
	public static PerFileDataStorage createItem(@NonNull final String name, @NonNull final String type) {
		return loadedFiles.loadOrCreateItem(name, () -> new PerFileDataStorage(name, type));
	}

	/**
	 * @see ConfigItems#loadItems()
	 */
	public static void loadItems() {
		loadedFiles.loadItems();
	}

	/**
	 * @param instance
	 * @see ConfigItems#removeItem(org.mineacademy.fo.settings.YamlConfig)
	 */
	public static void removeItem(final PerFileDataStorage instance) {
		loadedFiles.removeItem(instance);
	}

	/**
	 * @param name
	 * @return
	 * @see ConfigItems#isItemLoaded(String)
	 */
	public static boolean isItemLoaded(final String name) {
		return loadedFiles.isItemLoaded(name);
	}

	/**
	 * @param name
	 * @return
	 * @see ConfigItems#findItem(String)
	 */
	public static PerFileDataStorage findByName(@NonNull final String name) {
		return loadedFiles.findItem(name);
	}

	/**
	 *
	 * @param type
	 * @return
	 */
	public static List<PerFileDataStorage> findByType(final String type) {
		final List<PerFileDataStorage> items = new ArrayList<>();

		for (final PerFileDataStorage item : getItems())
			if (item.getType().equalsIgnoreCase(type))
				items.add(item);

		return items;
	}

	/**
	 * @return
	 * @see ConfigItems#getItems()
	 */
	public static Collection<PerFileDataStorage> getItems() {
		return loadedFiles.getItems();
	}

	/**
	 * @return
	 * @see ConfigItems#getItemNames()
	 */
	public static Set<String> getItemsNames() {
		return loadedFiles.getItemNames();
	}
}
