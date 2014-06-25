package me.michaelkrauty.Locker;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

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
		if (main.getDataFile().getKeys("") != null) {

			for (String key : main.getDataFile().getKeys("")) {
				String[] location = key.split(",");
				Block block = main.getServer().getWorld(location[0]).getBlockAt(Integer.parseInt(location[1]), Integer.parseInt(location[2]), Integer.parseInt(location[3].split("\\.")[0]));
				if (block.getType() != Material.CHEST) {
					main.getDataFile().delete(main.locationToString(block.getLocation()));
				}
			}
		}
	}

	public void checkExpiry() {

	}
}
