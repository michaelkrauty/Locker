package me.michaelkrauty.Locker;

import me.michaelkrauty.Locker.listeners.BlockListener;
import me.michaelkrauty.Locker.listeners.PlayerListener;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class Main extends JavaPlugin {

	public static Main main;

	public static DataFile dataFile;
	public static Config config;
	public static ScheduledTasks scheduledTasks;

	public void onEnable() {
		main = this;
		getCommand("locker").setExecutor(new LockerCommand(this));
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		checkDataFolder();
		dataFile = new DataFile();
		config = new Config(this);
		scheduledTasks = new ScheduledTasks(this);
		final BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				scheduledTasks.checkChests();
			}
		}, 0L, 1L);
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				scheduledTasks.checkExpiry();
			}
		}, 0L, 1200L);
	}


	public void checkDataFolder() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
	}

	public DataFile getDataFile() {
		return dataFile;
	}

	public Config getConfigFile() {
		return config;
	}

	public String locationToString(Location loc) {
		String world = loc.getWorld().getName();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		return world + "," + x + "," + y + "," + z;
	}

	public void copyStats(Location loc1, Location loc2) {
		getDataFile().set(locationToString(loc2), getDataFile().getString(locationToString(loc1)));
	}
}
