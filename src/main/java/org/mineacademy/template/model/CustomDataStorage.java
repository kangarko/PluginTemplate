package org.mineacademy.template.model;

import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.settings.YamlConfig;

import lombok.Getter;

/**
 * An example of storing custom data in a dedicated file with
 * the ability to set values from the game and save the file with comments.
 */
@Getter
// Optional annotation here, only used to make this class load automatically on plugin startup
@AutoRegister
public final class CustomDataStorage extends YamlConfig {

	/**
	 * The singleton of this class
	 */
	@Getter
	private final static CustomDataStorage instance = new CustomDataStorage();

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
	protected void onLoad() {

		// The "false" is the default value in case it does not exist on the disk.
		// If you use a default file in your source folder then DO NOT use default values here.
		this.demoValue = this.getBoolean("Demo_Value", false);
	}

	/**
	 * Set all data from this class that can be saved. Called automatically on save.
	 */
	@Override
	protected void onSave() {
		this.set("Demo_Value", this.demoValue);
	}

	/**
	 * Toggles the demo value on/off, returning the new current state
	 *
	 * @return
	 */
	public boolean switchDemoValue() {
		this.setDemoValue(!this.demoValue);

		return this.demoValue;
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
