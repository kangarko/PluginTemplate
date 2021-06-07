package org.mineacademy.template.command;

import org.bukkit.ChatColor;
import org.mineacademy.fo.command.DebugCommand;
import org.mineacademy.fo.command.PermsCommand;
import org.mineacademy.fo.command.ReloadCommand;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.template.model.Permissions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Holds all /chc main commands
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SampleCommandGroup extends SimpleCommandGroup {

	/**
	 * The singleton of this class
	 */
	@Getter
	private final static SimpleCommandGroup instance = new SampleCommandGroup();

	/**
	 * @see org.mineacademy.fo.command.SimpleCommandGroup#getHeaderPrefix()
	 */
	@Override
	protected String getHeaderPrefix() {
		return "" + ChatColor.DARK_RED + ChatColor.BOLD;
	}

	/**
	 * @see org.mineacademy.fo.command.SimpleCommandGroup#registerSubcommands()
	 */
	@Override
	protected void registerSubcommands() {

		// Register a sample command for this group
		registerSubcommand(new SampleGroupCommand());

		// Register the premade commands from Foundation
		registerSubcommand(new PermsCommand(Permissions.class, "templateplugin.command.perms"));
		registerSubcommand(new DebugCommand("templateplugin.command.debug"));
		registerSubcommand(new ReloadCommand("templateplugin.command.reload"));

	}
}
