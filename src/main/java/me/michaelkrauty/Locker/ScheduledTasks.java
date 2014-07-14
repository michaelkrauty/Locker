package me.michaelkrauty.Locker;

import org.bukkit.Material;

import java.util.Date;

/**
 * Created on 6/25/2014.
 *
 * @author michaelkrauty
 */
public class ScheduledTasks {

	private Main main;

	public ScheduledTasks(Main instance) {
		main = instance;
	}

	public void checkChests() {
		try {
			for (Locker locker : main.lockers) {
				if (locker.getLocation().getWorld().getBlockAt(locker.getLocation()) == null) {
					locker.delete();
				}
				Material blockType = locker.getLocation().getWorld().getBlockAt(locker.getLocation()).getType();
				if (blockType != Material.CHEST
						&& blockType != Material.TRAPPED_CHEST
						&& blockType != Material.FURNACE
						&& blockType != Material.BURNING_FURNACE) {
					locker.delete();
				}
			}
		} catch (Exception ignored) {
		}
	}

	public void checkExpiry() {
		// try {
		//	for (Locker locker : main.lockers) {
		//		Date lastInteract = new Date(locker.getLastInteract());
		//	}
		//} catch (Exception ignored) {
		//}
	}
}
