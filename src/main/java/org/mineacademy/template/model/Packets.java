package org.mineacademy.template.model;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.PacketUtil;
import org.mineacademy.fo.PacketUtil.SimpleChatAdapter;
import org.mineacademy.fo.model.HookManager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A sample packet listener utilizing ProtocolLib
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Packets {

	/**
	 * Register and initiate packet listening
	 */
	public static void load() {

		if (!HookManager.isProtocolLibLoaded()) {
			Common.log("&cWarning: &fNo ProtocolLib found, some features will not be available.");

			return;
		}

		//
		// Implement your own packet listeners here, samples below:
		//

		// Receiving tab complete
		PacketUtil.addReceivingListener(ListenerPriority.HIGHEST, PacketType.Play.Client.TAB_COMPLETE, event -> {
			final String buffer = event.getPacket().getStrings().read(0);

			Common.log("Received tab complete packet '" + buffer + "' to " + event.getPlayer().getName());
		});

		// A custom handler for sending chat messages (they are pretty complicated to decipher 
		// so we made a wrapper for you)
		PacketUtil.addPacketListener(new SimpleChatAdapter() {

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
