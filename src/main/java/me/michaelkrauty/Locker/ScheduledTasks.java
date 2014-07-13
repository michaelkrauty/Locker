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
				if (locker.getLocation().getWorld().getBlockAt(locker.getLocation()).getType() != Material.CHEST) {
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
