package me.michaelkrauty.Locker;

import me.michaelkrauty.Locker.listeners.BlockListener;
import me.michaelkrauty.Locker.listeners.PlayerListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class Main extends JavaPlugin {

	public static Main main;

	public static DataFile dataFile;

	public void onEnable() {
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (String key : dataFile.getKeys("")) {
					String[] keys = key.split(",");
					Block block = getServer().getWorld(keys[0]).getBlockAt(Integer.parseInt(keys[1]), Integer.parseInt(keys[2]), Integer.parseInt(keys[3]));
					if (block.getType() != Material.CHEST) {
						getDataFile().delete(locationToString(block.getLocation()));
					}
				}
			}
		}, 0L, 1L);
		main = this;
		getCommand("locker").setExecutor(new LockerCommand(this));
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		checkDataFolder();
		dataFile = new DataFile();
	}

	public void checkDataFolder() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
	}

	public DataFile getDataFile() {
		return dataFile;
	}

	public String locationToString(Location loc) {
		String world = loc.getWorld().getName();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		return world + "," + x + "," + y + "," + z;
	}
}
