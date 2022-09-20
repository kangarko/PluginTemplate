package org.mineacademy.template.command;

import java.util.List;

import org.bukkit.ChatColor;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.DebugCommand;
import org.mineacademy.fo.command.PermsCommand;
import org.mineacademy.fo.command.ReloadCommand;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.model.SimpleComponent;
import org.mineacademy.template.model.Permissions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A sample command group. A command group is a collection of commands,
 * such as for the Boss plugin, the command group is /boss, where
 * subcommands can be "spawn", "remove" etc. Example:
 *
 * /boss spawn
 * /boss remove
 * (etc..)
 *
 */
@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SampleCommandGroup extends SimpleCommandGroup {

	/**
	 * The instance of this class, hidden because the only call to this class is from
	 * our auto registration class.
	 */
	@Getter(value = AccessLevel.PRIVATE)
	private static final SampleCommandGroup instance = new SampleCommandGroup();

	/**
	 * @see org.mineacademy.fo.command.SimpleCommandGroup#getHeaderPrefix()
	 */
	@Override
	protected String getHeaderPrefix() {
		return "" + ChatColor.DARK_RED + ChatColor.BOLD;
	}

	// Change this to edit the default message shown when you type the main
	// command group label, here: /plugintemplate
	@Override
	protected List<SimpleComponent> getNoParamsHeader() {
		return super.getNoParamsHeader();
	}

	// Change this to remove "Visit MineAcademy" link we by default have for our plugins
	@Override
	protected String getCredits() {
		return super.getCredits();
	}

	// Change this to edit the messages at the top of our help command, defaults to
	// typing "/plugintemplate ?" or "/plugintemplate help" (you can change ?/help by
	// overriding "getHelpLabel()")
	@Override
	protected String[] getHelpHeader() {
		return super.getHelpHeader();
	}

	/**
	 * @see org.mineacademy.fo.command.SimpleCommandGroup#registerSubcommands()
	 */
	@Override
	protected void registerSubcommands() {

		// Register a sample command for this group
		this.registerSubcommand(new SampleGroupCommand());

		// Register the premade commands from Foundation
		this.registerSubcommand(new PermsCommand(Permissions.class, "templateplugin.command.perms"));
		this.registerSubcommand(new DebugCommand("templateplugin.command.debug"));
		this.registerSubcommand(new ReloadCommand("templateplugin.command.reload"));
	}
}
