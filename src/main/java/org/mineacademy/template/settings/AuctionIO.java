package org.mineacademy.template.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.constants.FoConstants;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.settings.YamlSectionConfig;

import icu.nonstop.auction.model.Auction;
import lombok.Getter;

public class AuctionIO extends YamlSectionConfig implements ConfigSerializable {

	private Auction auction;
	private UUID auctionId;

	@Getter
	private static volatile Map<UUID, AuctionIO> auctionMap = new HashMap<>();

	protected AuctionIO(final Auction auction) {
		super("Auctions." + auction.getAuctionId());

		this.auctionId = auction.getAuctionId();
		this.auction = auction;

		loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);
	}

	@Override
	protected void onLoadFinish() {
		if (isSet("Auction")) {
			this.auction = Auction.deserialize(getMap("Auction"));
		}

		if (isSet("Auction.Auction_Id")) {
			this.auctionId = UUID.fromString(getString("Auction.Auction_Id"));
		}

		//        AuctionPlugin.getInstance().getAuctionManager().addToAuctionQueue(this.auction);
	}

	@Override
	public SerializedMap serialize() {
		final SerializedMap map = new SerializedMap();

		map.put("Auction", this.auction.serialize());

		return map;
	}

	public boolean hasAuction() {
		return this.auction != null;
	}

	public Auction getAuction() {
		return this.auction;
	}

	public void setAuction(final Auction auction) {
		this.auction = auction;

		save();
	}

	public UUID getAuctionId() {
		return this.auctionId;
	}

	public void removeFromMemory() {
		synchronized (auctionMap) {
			auctionMap.remove(this.auctionId);
			Common.log("auctionmap: " + auctionMap.toString());
			Common.log("section: " + getSection());
		}
		save();
	}

	/* ------------------------------------------------------------------------------- */
	/* Static access */
	/* ------------------------------------------------------------------------------- */

	public static AuctionIO from(final Auction auction) {
		synchronized (auctionMap) {
			AuctionIO cache = auctionMap.get(auction.getAuctionId());

			if (cache == null) {
				cache = new AuctionIO(auction);

				auctionMap.put(auction.getAuctionId(), cache);
			}
			Common.log("auctionmap: " + auctionMap.toString());
			return cache;
		}
	}

	public static void clear() {
		synchronized (auctionMap) {
			auctionMap.clear();
		}
	}

}

/*
**NOTE**
Before Running the command which calls the removeFromMemory method this is the value of the auctionMap:
auctionmap: {72507b24-f8e8-45c9-864e-f0802922dec5=YamlSection{file=data.db, section=Auctions.72507b24-f8e8-45c9-864e-f0802922dec5, local path=}}
After running the command that calls removeFromMemory:
auctionmap: {}


So it is being removed from the cache, but seems like the save() is not removing it from the data.db file as this is still in there:
Auctions:
  72507b24-f8e8-45c9-864e-f0802922dec5:
    Auction:
      Auction_Id: 72507b24-f8e8-45c9-864e-f0802922dec5
      ItemStack:
        ==: org.bukkit.inventory.ItemStack
        type: GRASS
        amount: 64
      Current_Bid: 100
      Max_Bid: 100
      Starting_Bid: 100
      Owner_UUID: a65cbaad-67e1-367f-98fe-522d031e0398

I did try doing a save(getSection(), null), but all that seemed to do was add another key to the "72507b24-f8e8-45c9-864e-f0802922dec5" object like so:
Auctions:
  72507b24-f8e8-45c9-864e-f0802922dec5:
    Auction:
      Auction_Id: 72507b24-f8e8-45c9-864e-f0802922dec5
      ItemStack:
        ==: org.bukkit.inventory.ItemStack
        type: GRASS
        amount: 64
      Current_Bid: 100
      Max_Bid: 100
      Starting_Bid: 100
      Owner_UUID: a65cbaad-67e1-367f-98fe-522d031e0398
	  Auctions: {}
*/