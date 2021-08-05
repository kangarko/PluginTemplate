package org.mineacademy.template.settings;

import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.settings.YamlConfig;

import lombok.Getter;

/**
 * An example of storing custom data in a dedicated file with
 * the ability to set values from the game and save the file with comments.
 */
@Getter
public final class CustomDataStorage extends YamlConfig {

	/**
	 * The singleton of this class
	 */
	@Getter
	private final CustomDataStorage instance = new CustomDataStorage();

	/**
	 * An example value we can store
	 */
	private boolean demoValue;

	/*
	 * Automatically load the data upon calling CustomDataStorage#getInstance()
	 */
	private CustomDataStorage() {

		// Instead of NO_DEFAULT you can also create custom-data.yml file with values
		// and place it to src/main/resources in your source and we will automatically
		// copy it over to your plugin's folder with default values.
		this.loadConfiguration(NO_DEFAULT, "custom-data.yml");
	}

	/**
	 * Automatically called when the file is loaded, you can
	 * pull your values from the file here.
	 */
	@Override
	protected void onLoadFinish() {

		// The "false" is the default value in case it does not exist on the disk.
		// If you use a default file in your source folder then DO NOT use default values here.
		this.demoValue = this.getBoolean("Demo_Value", false);
	}

	/**
	 * Collect all data from this class that can be saved.
	 * Called automatically on save.
	 */
	@Override
	protected SerializedMap serialize() {
		return SerializedMap.ofArray(
				"Demo_Value", this.demoValue);
	}

	/**
	 * An example of setting a value to the disk and saving immediatelly.
	 *
	 * @param demoValue the demoValue to set
	 */
	public void setDemoValue(boolean demoValue) {
		this.demoValue = demoValue;

		this.save();
	}
}
