package org.mineacademy.template;

import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.Remain;

@AutoRegister
public final class SignEditCommand extends SimpleCommand {

	public SignEditCommand() {
		super("se");

		setPermission(null);
	}

	@Override
	protected void onCommand() {
		Remain.openSign(getPlayer(), Remain.getTargetBlock(getPlayer(), 10));
	}
}
