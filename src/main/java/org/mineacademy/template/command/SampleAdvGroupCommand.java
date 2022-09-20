package org.mineacademy.template.command;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.exception.FoException;
import org.mineacademy.fo.exception.InvalidCommandArgException;
import org.mineacademy.fo.settings.Lang;

/**
 * An example of a single group command which implements another set of commands
 * i.e. subcommand in a subcommand, this example was taken from the /boss region
 * command from the Boss plugin.
 *
 * See {@link SampleGroupCommand} for fundamentals.
 */
final class SampleAdvGroupCommand extends SimpleSubCommand {

	SampleAdvGroupCommand() {
		super("region|rg");

		this.setDescription(Lang.of("Commands.Region.Description"));
		this.setUsage("<params ...>");
		this.setMinArguments(1);
	}

	@Override
	protected String[] getMultilineUsageMessage() {
		return new String[] {
				"/{label} {sublabel} new - Create a new region.",
				"/{label} {sublabel} tp <name> - Teleport to a region.",
				"/{label} {sublabel} view [name] - Visualize regions with center less than 100 blocks from you or the given region.",
				"/{label} {sublabel} remove <name> - Delete a region.",
				"/{label} {sublabel} menu <name> - Open region menu.",
				"/{label} {sublabel} list - Browse available regions."
		};
	}

	@Override
	protected void onCommand() {

		final String regionName = this.args.length > 1 ? this.args[1] : null;
		//final BossRegion region = regionName != null ? BossRegion.findRegion(regionName) : null;
		final Param param = Param.find(this.args[0]);

		if (param == null)
			throw new InvalidCommandArgException();

		//
		// Commands without a region.
		//
		if (param == Param.LIST)
			return;
		else if (param == Param.NEW)
			return;

		// Require region name for all params below, except view, but when it is provided, check it
		if (param != Param.VIEW || (param == Param.VIEW && regionName != null))
			this.checkNotNull(regionName, "&cPlease specify the region name.");
		//checkNotNull(region, "&cRegion '" + regionName + "' doesn't exists.");

		if (param == Param.MENU)
			this.checkConsole();
		else if (param == Param.REMOVE) {
			// Implement command logic
		}

		else if (param == Param.VIEW)
			this.checkConsole();
		else if (param == Param.TELEPORT) {
			// Implement command logic
		}

		// Useful for you if you forget to implement a new parameter
		else
			throw new FoException("Unhandled param " + param);
	}

	@Override
	public List<String> tabComplete() {

		final Param param = this.args.length > 0 ? Param.find(this.args[0]) : null;

		switch (this.args.length) {
			case 1:
				return this.completeLastWord(Param.values());

			case 2:
				return param == Param.LIST || param == Param.NEW ? NO_COMPLETE : this.completeLastWord(/*BossRegion.getRegionNames()*/ "EMPTY");
		}

		return NO_COMPLETE;
	}

	/**
	 * The parameter for this command
	 */
	private enum Param {

		/**
		 * Create a new region.
		 */
		NEW("new"),

		/**
		 * Remove a region.
		 */
		REMOVE("remove", "rm"),

		/**
		 * Show particles around region.
		 */
		VIEW("view", "v"),

		/**
		 * Teleport to a region.
		 */
		TELEPORT("teleport", "tp"),

		/**
		 * Show region menu.
		 */
		MENU("menu"),

		/**
		 * List installed regions.
		 */
		LIST("list", "l");

		/**
		 * The label for this command arg.
		 */
		private final String label;

		/**
		 * The sublabels for this command arg.
		 */
		private final String[] aliases;

		/*
		 * Create a new param for this subcommand.
		 */
		Param(final String label, final String... aliases) {
			this.label = label;
			this.aliases = aliases;
		}

		/**
		 * Return a parameter from the string, or null.
		 *
		 * @param argument
		 * @return
		 */
		@Nullable
		private static Param find(String argument) {
			argument = argument.toLowerCase();

			for (final Param param : values()) {
				if (param.label.toLowerCase().equals(argument))
					return param;

				if (param.aliases != null && Arrays.asList(param.aliases).contains(argument))
					return param;
			}

			return null;
		}

		/**
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.label;
		}
	}
}