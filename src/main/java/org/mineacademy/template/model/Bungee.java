package org.mineacademy.template.model;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.mineacademy.fo.bungee.BungeeMessageType;
import org.mineacademy.fo.bungee.BungeeListener;
import org.mineacademy.fo.bungee.message.IncomingMessage;
import org.mineacademy.fo.debug.Debugger;
import org.mineacademy.fo.exception.FoException;

import lombok.Getter;

/**
 * A sample bungee handler with listener to incoming packets from BungeeCord
 * and a sample protocol specification.
 */
public final class Bungee {

	/**
	 * Sample listener for upstream packets.
	 */
	@SuppressWarnings("unused")
	public static final class Listener extends BungeeListener {

		/**
		 * The singleton of this class
		 */
		@Getter
		private final static Listener instance = new Listener();

		/**
		 * Automatically registers a listener to incoming packets from BungeeCord.
		 * NB: Change "plugin:tmplugin" to your own channel name.
		 * You will have to implement handling of this on BungeeCord by yourself.
		 *
		 * NB: The channel name can only be 16 characters long! (or 20 on MC 1.16+)
		 */
		private Listener() {
			super("plugin:tmplugin", BungeePacket.class);
		}

		/**
		 * The presently read packet, a copy is stored here for convenience if you use it later
		 */
		private BungeePacket packet;

		/**
		 * The server the packet is coming from, a copy is stored here for convenience if you use it later
		 */
		private String server;

		/**
		 * The sender of the packet, a copy is stored here for convenience if you use it later
		 */
		private UUID senderUid;

		/**
		 * @see org.mineacademy.fo.bungee.BungeeListener#onMessageReceived(org.bukkit.entity.Player, org.mineacademy.fo.bungee.message.IncomingMessage)
		 */
		@Override
		public void onMessageReceived(Player player, IncomingMessage input) {

			this.packet = input.getAction();
			this.server = input.getServerName();
			this.senderUid = input.getSenderUid();

			Debugger.debug("bungee", "Received bungee packet " + this.packet + " from server " + this.server);

			if (this.packet == BungeePacket.SAMPLE_PACKET) {

				//
				// Implement your packet handling logic here
				//

				//final String demoField = input.readString();
				//final UUID demoField2 = input.readUUID();
				//final boolean demoField3 = input.readBoolean();
			}

			//
			// Implement handling of all the other packets here
			//
			// else if
			//

			else
				throw new FoException("Unhandled packet from BungeeControl: " + this.packet);
		}

	}

	/**
	 * A sample implementation of BungeeAction. You will need to keep an exact copy
	 * of this class in your BungeeCord plugin also.
	 *
	 * Our BungeeCord protocol always begins with
	 *
	 * 1) The UUID of the sender from which we send the packet, or null
	 * 2) The sender server name
	 * 3) The {@link BungeeMessageType}
	 *
	 * and the rest is the actual data within this enum.
	 *
	 */
	public enum BungeePacket implements BungeeMessageType {

		/**
		 * An example packet you can define.
		 */
		SAMPLE_PACKET(
				String.class /* example field */,
				UUID.class /* example field */,
				Boolean.class /* example field */
		),

		//
		// Implement more packets here
		//

		;

		/**
		 * Stores all valid values, the names of them are only used
		 * in the error message when the length of data does not match
		 */
		@Getter
		private final Class<?>[] content;

		/**
		 * Constructs a new bungee action
		 *
		 * @param validValues
		 */
		BungeePacket(final Class<?>... validValues) {
			this.content = validValues;
		}
	}

}
