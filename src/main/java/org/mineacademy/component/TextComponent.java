package org.mineacademy.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.remain.CompChatColor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Getter
@RequiredArgsConstructor
public final class TextComponent {

	@SneakyThrows
	public static void main(final String[] args) {
		final TextComponent mainText = new TextComponent("Hello, Minecraft World!");
		final TextComponent extraText = new TextComponent("Click me!");

		extraText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/say Hi!"));
		extraText.setHoverEvent(new HoverEvent(new ItemStack(Material.DIAMOND)));

		mainText.addExtra(extraText);

		System.out.println(mainText.toJson());
	}

	private static final Map<String, String> COLOR_MAP = new HashMap<>();

	private final String text;
	private List<TextComponent> extra;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;
	private String color;
	private Boolean bold;
	private Boolean italic;
	private Boolean underlined;
	private Boolean strikethrough;
	private Boolean obfuscated;
	private String insertion;

	public void addExtra(final TextComponent component) {
		if (extra == null)
			extra = new ArrayList<>();

		extra.add(component);
	}

	public void setClickEvent(final ClickEvent clickEvent) {
		this.clickEvent = clickEvent;
	}

	public void setHoverEvent(final HoverEvent hoverEvent) {
		this.hoverEvent = hoverEvent;
	}

	public void setColor(final ChatColor color) {
		this.setColor(color.name());
	}

	public void setColor(final CompChatColor color) {
		this.setColor(color.getName());
	}

	public void setColor(final String color) {
		if (color.startsWith("#") && color.length() == 7)
			this.color = color;

		else {
			this.color = COLOR_MAP.get(color.toLowerCase());

			Valid.checkNotNull(this.color, "Unknown color: " + color);
		}
	}

	public void setBold(final boolean bold) {
		this.bold = bold;
	}

	public void setItalic(final boolean italic) {
		this.italic = italic;
	}

	public void setUnderlined(final boolean underlined) {
		this.underlined = underlined;
	}

	public void setStrikethrough(final boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	public void setObfuscated(final boolean obfuscated) {
		this.obfuscated = obfuscated;
	}

	public void setInsertion(final String insertion) {
		this.insertion = insertion;
	}

	public String toJson() {
		return new com.google.gson.Gson().toJson(this);
	}

	static {
		COLOR_MAP.put("black", "#000000");
		COLOR_MAP.put("dark_blue", "#0000AA");
		COLOR_MAP.put("dark_green", "#00AA00");
		COLOR_MAP.put("dark_aqua", "#00AAAA");
		COLOR_MAP.put("dark_red", "#AA0000");
		COLOR_MAP.put("dark_purple", "#AA00AA");
		COLOR_MAP.put("gold", "#FFAA00");
		COLOR_MAP.put("gray", "#AAAAAA");
		COLOR_MAP.put("dark_gray", "#555555");
		COLOR_MAP.put("blue", "#5555FF");
		COLOR_MAP.put("green", "#55FF55");
		COLOR_MAP.put("aqua", "#55FFFF");
		COLOR_MAP.put("red", "#FF5555");
		COLOR_MAP.put("light_purple", "#FF55FF");
		COLOR_MAP.put("yellow", "#FFFF55");
		COLOR_MAP.put("white", "#FFFFFF");
	}
}