package org.mineacademy.template.model;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.model.PacketListener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A sample packet listener utilizing ProtocolLib
 */
@AutoRegister(hideIncompatibilityWarnings = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Packets extends PacketListener {

	/**
	 * The instance of this class, hidden because the only call to this class is from
	 * our auto registration class.
	 */
	@Getter(value = AccessLevel.PRIVATE)
	private static final Packets instance = new Packets();

	/**
	 * Register and initiate packet listening
	 */
	@Override
	public void onRegister() {

		//
		// Implement your own packet listeners here, samples below:
		//

		// Receiving tab complete
		addReceivingListener(ListenerPriority.HIGHEST, PacketType.Play.Client.TAB_COMPLETE, event -> {
			final String buffer = event.getPacket().getStrings().read(0);

			Common.log("Received tab complete packet '" + buffer + "' to " + event.getPlayer().getName());
		});

		// A custom handler for sending chat messages (they are pretty complicated to decipher
		// so we made a wrapper for you)
		addPacketListener(new SimpleChatAdapter() {

			@Override
			protected String onMessage(String message) {
				Common.log("Sending chat packet '" + message + "' to " + this.getPlayer().getName());

				// If you want to prevent this message from being shown to player cancel the event here:
				//getEvent().setCancelled(true);

				return message;
			}
		});
	}
}
