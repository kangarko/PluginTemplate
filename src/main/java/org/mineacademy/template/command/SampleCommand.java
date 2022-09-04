package org.mineacademy.template.command;

import java.util.List;

import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.Remain;
import org.mineacademy.template.model.Permissions;

/**
 * A sample standalone command.
 */
@AutoRegister
public final class SampleCommand extends SimpleCommand {

	/**
	 * Create a new standalone command /sample
	 */
	public SampleCommand() {
		super("sample");

		this.setUsage("<args...>");
		this.setDescription("An example command");

		// Automatically check if at least one argument is typed, such as "/sample hello"
		// and returns with error message to the sender if not (you can edit the error message in
		// localization/messages_en.yml resource file)
		//this.setMinArguments(1);
		this.setPermission(Permissions.Command.SAMPLE);

		// Uncomment to get rid of the automatically generated help text: https://i.imgur.com/Q79RKN0.png
		//this.setAutoHandleHelp(false);
	}

	/**
	 * Shown in the Usages section when you type the command label (/sample):
	 * https://i.imgur.com/huVRarA.png
	 *
	 * You can edit the format in the localization/messages_en.yml resource file
	 *
	 * @see org.mineacademy.fo.command.SimpleCommand#getMultilineUsageMessage()
	 */
	@Override
	protected String[] getMultilineUsageMessage() {
		return new String[] {
				"An example command that will print",
				"what's being written into it."
		};
	}

	/**
	 * Perform the main command logic.
	 */
	@Override
	protected void onCommand() {

		// We provide a ton of useful shortcuts for conditions such as:
		//
		// if (sender instanceof Player) {
		//   sender.sendMessage("You must be a player!");
		//
		//   return;
		// }
		//
		// This one line does the same as the condition above. If you want to change the
		// error message to the console, see SimpleLocalization class or simply edit
		// localization/messages_en.yml resource file.
		//
		//this.checkConsole();

		// A shortcut for sending a message to the sender, note the use of placeholders and joinArgs to automatically join
		// all command arguments with spaces such as /sample hello world will be joined to "hello world" from index 0,
		// or simply "world" when you call joinArgs(1)
		//tell("Executed command /{label} " + this.joinArgs(0));

		// You can access "args" array directly. However, checking and returning arguments like this is deprecated,
		//
		//if (args.length == 0)
		//	returnTell("Please at least specify one command argument!");
		//
		// Rather, use one-liner that will do the same thing:
		//this.checkArgs(1, "Please at least specify one command argument.");

		// We convert args[0] to lowerCase, so player can type small and large letters, command will be executed nevertheless. (+ we don't need to use equalsIgnoreCase method always)
		final String param = this.args[0].toLowerCase();

		if ("example".equals(param)) {
			this.checkBoolean(this.args.length == 1, "Do not type anything after '/{label} sample'!");

			this.tell("&7You typed '&b/{label} sample&7'!");

		} else if ("find".equals(param)) {
			this.checkArgs(2, "Please specify a player's name: /{label} {sublabel} <playerName>");

			// This will automatically run the method inside if the offline player was found (even if he is offline)
			// And if not, we automatically return with an error message, editable in localization/messages_en.yml
			this.findOfflinePlayer(this.args[1], target -> {
				this.tell(target.getName() + " last joined " + TimeUtil.getFormattedDateShort(target.getLastPlayed()));
			});

		} else if ("actionbar".equals(param)) {
			this.checkArgs(3, "Usage is: /{label} {sublabel} <playerName> [message]");

			// Automatically finds an online player (BungeeCord is NOT supported here)
			// If the player is not on this server, we automatically return with an error message,
			// editable in localization/messages_en.yml
			final Player target = this.findPlayer(args[1]);
			final String message = Common.joinRange(2, this.args);

			// Our compatibility library sends the action bar on Minecraft 1.8.8+ and as text on older MC.
			Remain.sendActionBar(target, message);

		} else
			this.returnInvalidArgs();
	}

	/**
	 * @see org.mineacademy.fo.command.SimpleCommand#tabComplete()
	 */
	@Override
	protected List<String> tabComplete() {

		// Automatically returns tab completion suggestions for each amount of command arguments
		switch (args.length) {

			// Player typed /sample and pressed tab, he is completing the first argument.
			case 1:
				return completeLastWord("example", "find", "actionbar");

			// Player typed /sample SOMETHING and pressed tab, he might have typed /sample example,
			// /sample find or /sample actionbar, either way, after that we complete all online player names.
			case 2:
				return completeLastWordPlayerNames();
		}

		return NO_COMPLETE;
	}
}
