package org.mineacademy.template.command;

import java.util.List;

import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
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

		setUsage("<args...>");
		setDescription("An example command");
		setMinArguments(1);
		setPermission(Permissions.Command.SAMPLE);

		// Uncomment to get rid of the automatically generated help text: https://i.imgur.com/Q79RKN0.png
		//setAutoHandleHelp(false);
	}

	/**
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
		tell("Executed command /{label} " + joinArgs(0));
	}

	/**
	 * @see org.mineacademy.fo.command.SimpleCommand#tabComplete()
	 */
	@Override
	protected List<String> tabComplete() {
		return completeLastWordPlayerNames();
	}
}
