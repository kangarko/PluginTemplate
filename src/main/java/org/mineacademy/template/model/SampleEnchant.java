package org.mineacademy.template.model;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.model.SimpleEnchantment;
import org.mineacademy.fo.remain.CompParticle;
import org.mineacademy.fo.remain.CompSound;

import lombok.Getter;

/**
 * An example of a custom enchantment
 */
@AutoRegister(hideIncompatibilityWarnings = true)
public final class SampleEnchant extends SimpleEnchantment {

	/**
	 * The instance of this class
	 */
	@Getter
	private static final Enchantment instance = new SampleEnchant();

	/*
	 * Construct a new custom enchant
	 */
	private SampleEnchant() {
		super("Throwaway", 5);
	}

	/**
	 * Called automatically only when the damager is a living entity having a hand item containing this enchant
	 */
	@Override
	protected void onDamage(final int level, final LivingEntity damager, final EntityDamageByEntityEvent event) {

		if (event.getEntity() instanceof LivingEntity) {
			final LivingEntity victim = (LivingEntity) event.getEntity();

			CompSound.BLAZE_HIT.play(damager.getLocation());
			CompParticle.CRIT.spawn(victim.getLocation());

			// This custom enchant throws victim into the air and sets her on fire
			victim.setFireTicks(3 * 20);
			victim.setVelocity(damager.getEyeLocation().getDirection().multiply(4).setY(5));
		}
	}

	/**
	 * Example of how you can make the enchant lore colored
	 *
	 * @param level
	 * @return
	 */
	@Override
	public String getLore(int level) {
		return ChatColor.RED + super.getLore(level);
	}
}
