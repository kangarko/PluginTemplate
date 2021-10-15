package org.mineacademy.template.model;

import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.model.DiscordListener;

import github.scarsz.discordsrv.api.events.DiscordGuildMessagePreProcessEvent;
import github.scarsz.discordsrv.api.events.GameChatMessagePreProcessEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.dependencies.jda.api.entities.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A sample Discord listener utilizing DiscordSRV
 */
// Hide "DiscordListener requires DiscordSRV" console message.
// Optional, we always register any DiscordListener class even without this annotation.
@AutoRegister(hideIncompatibilityWarnings = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Discord extends DiscordListener {

	/**
	 * The instance of this class, hidden because the only call to this class is from
	 * our auto registration class.
	 */
	@Getter(value = AccessLevel.PRIVATE)
	private static final Discord instance = new Discord();

	/**
	* Listen to DiscordSRV sending messages from Minecraft to Discord
	*
	* @see org.mineacademy.fo.model.DiscordListener#onMessageSent(github.scarsz.discordsrv.api.events.GameChatMessagePreProcessEvent)
	*/
	@Override
	protected void onMessageSent(GameChatMessagePreProcessEvent event) {

		//
		// Implement your own logic of handling Minecraft>Discord here
		//
		//

		// You can completely prevent DiscordSRV from dealing with chat here
		event.setCancelled(true);

		// And forward MC chat to your own Discord channel instead
		sendWebhookMessage(event.getPlayer(), "standard", event.getMessage());
	}

	/**
	 * Listen to DiscordSRV receiving messages from Discord to Minecraft
	 *
	 * @see org.mineacademy.fo.model.DiscordListener#onMessageReceived(github.scarsz.discordsrv.api.events.DiscordGuildMessagePreProcessEvent)
	 */
	@Override
	protected void onMessageReceived(DiscordGuildMessagePreProcessEvent event) {

		final Member member = event.getMember();
		final User author = event.getAuthor();
		final TextChannel discordChannel = event.getChannel();
		final String senderName = this.findPlayerName(member, author);

		String message = event.getMessage().getContentDisplay();

		// Remove emoji
		message = ChatUtil.removeEmoji(message);

		if (!message.trim().isEmpty()) {

			//
			// Implement your own logic of handling Discord>Minecraft here
			//

			Common.broadcast("&8[&dDiscord / " + discordChannel.getName() + "&8] &7" + senderName + "&8: &f" + message);
		}

		// Prevent DiscordSRV making the message appear in chat in case you want to send it above
		event.setCancelled(true);
	}

}
