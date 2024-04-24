package org.mineacademy.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ClickEvent {

	private final String action;
	private final String value;

	public ClickEvent(final Action action, final String value) {
		this.action = action.action;
		this.value = value;
	}

	@RequiredArgsConstructor
	public enum Action {
		OPEN_URL("open_url"),
		OPEN_FILE("open_file"),
		RUN_COMMAND("run_command"),
		SUGGEST_COMMAND("suggest_command"),
		CHANGE_PAGE("change_page"),
		COPY_TO_CLIPBOARD("copy_to_clipboard");

		private final String action;
	}
}