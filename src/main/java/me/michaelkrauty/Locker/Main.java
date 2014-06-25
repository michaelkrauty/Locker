package me.michaelkrauty.Locker;

import me.michaelkrauty.Locker.listeners.BlockListener;
import me.michaelkrauty.Locker.listeners.PlayerListener;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class Main extends JavaPlugin {

	public static Main main;

	public static DataFile dataFile;

	public static Logger log = Logger.getLogger("MC");

	public static ArrayList<String> createQueue = new ArrayList<String>();
	public static ArrayList<String> delQueue = new ArrayList<String>();

	public void onEnable() {
		main = this;
		getCommand("locker").setExecutor(new LockerCommand(this));
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		checkDataFolder();
		dataFile = new DataFile(this);
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
