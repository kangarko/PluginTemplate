package org.mineacademy.template;

import org.mineacademy.fo.Valid;
import org.mineacademy.fo.bungee.SimpleBungee;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.model.HookManager;
import org.mineacademy.fo.model.Variables;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.template.command.SampleCommand;
import org.mineacademy.template.command.SampleCommandGroup;
import org.mineacademy.template.listener.SampleListener;
import org.mineacademy.template.model.Bungee;
import org.mineacademy.template.model.Bungee.BungeePacket;
import org.mineacademy.template.model.Discord;
import org.mineacademy.template.model.Packets;
import org.mineacademy.template.model.Placeholders;

import lombok.Getter;

/**
 * PluginTemplate is a simple template you can use every time you make
 * a new plugin. This will save you time because you no longer have to
 * recreate the same skeleton and features each time.
 *
 * It uses Foundation for fast and efficient development process.
 */
public final class PluginTemplate extends SimplePlugin {

	/**
	 * Automatically registers the main command group. A command group holds different
	 * commands, such as /chatcontrol is the main command group holding commands
	 * /chatcontrol mute, /chatcontrol clear etc.
	 */
	@Getter
	private final SimpleCommandGroup mainCommand = SampleCommandGroup.getInstance();

	/**
	 * Automatically registers a listener to incoming packets from BungeeCord.
	 * NB: Change "plugin:templateplugin" to your own channel name.
	 * You will have to implement handling of this on BungeeCord by yourself.
	 *
	 * NB: The channel name can only be 20 characters long!
	 */
	@Getter
	private final SimpleBungee bungeeCord = new SimpleBungee("plugin:tmpltplugin", Bungee.Listener.getInstance(), BungeePacket.values());

	/**
	 * Automatically perform login ONCE when the plugin starts.
	 */
	@Override
	protected void onPluginStart() {
	}

	/**
	 * Automatically perform login when the plugin starts and each time it is reloaded.
	 */
	@Override
	protected void onReloadablesStart() {

		// You can check for necessary plugins and disable loading if they are missing
		Valid.checkBoolean(HookManager.isVaultLoaded(), "You need to install Vault so that we can work with packets, offline player data, prefixes and groups.");

		// Load parts of the plugin
		Packets.load();

		// Uncomment to load variables
		// Variable.loadVariables();

		// Register variables - PlaceholderAPI compatible
		Variables.addExpansion(Placeholders.getInstance());

		//
		// Add your own plugin parts to load automatically here
		//

		// Register commands
		registerCommand(new SampleCommand());

		//
		// Add your own commands here
		//

		// Register events
		registerEvents(SampleListener.getInstance());

		if (HookManager.isDiscordSRVLoaded())
			registerEvents(Discord.getInstance());

		//
		// Add your own events ere
		//
	}

	/* ------------------------------------------------------------------------------- */
	/* Static */
	/* ------------------------------------------------------------------------------- */

	/**
	 * Return the instance of this plugin, which simply refers to a static
	 * field already created for you in SimplePlugin but casts it to your
	 * specific plugin instance for your convenience.
	 *
	 * @return
	 */
	public static PluginTemplate getInstance() {
		return (PluginTemplate) SimplePlugin.getInstance();
	}
}
