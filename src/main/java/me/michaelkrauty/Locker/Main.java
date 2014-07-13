package me.michaelkrauty.Locker;

import me.michaelkrauty.Locker.listeners.PlayerListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class Main extends JavaPlugin {

	public static Main main;

	public ArrayList<Locker> lockers = new ArrayList<Locker>();

	public static ScheduledTasks scheduledTasks;

	public void onEnable() {
		main = this;
		getCommand("locker").setExecutor(new LockerCommand(this));
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		checkDataFolder();
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

	public String locationToString(Location loc) {
		String world = loc.getWorld().getName();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		return world + "," + x + "," + y + "," + z;
	}

	public void loadLockers() {
		for (File file : new File(getDataFolder() + "/lockers").listFiles()) {
			String locString = file.getName().split("\\.")[0];
			String[] loc = locString.split(",");
			lockers.add(new Locker(this, new Location(getServer().getWorld(loc[0]), Integer.parseInt(loc[1]), Integer.parseInt(loc[2]), Integer.parseInt(loc[3]))));
		}
	}

	public Locker getLocker(Location loc) {
		if (lockers.size() != 0) {
			for (int i = 0; i < lockers.size(); i++) {
				if (locationToString(lockers.get(i).getLocation()).equals(locationToString(loc))) {
					return lockers.get(i);
				}
			}
		}
		return null;
	}

	public void createLocker(Location loc, Player owner) {
		createLocker(loc, owner.getUniqueId());
	}

	public void createLocker(Location loc, UUID owner) {
		if (!lockerExists(loc)) {
			lockers.add(new Locker(this, loc, owner));
		}
	}

	public boolean lockerExists(Location loc) {
		return getLocker(loc) != null;
	}

	public void copyLocker(Location loc1, Location loc2) {
		createLocker(loc2, getLocker(loc1).getOwner());
		getLocker(loc2).setUsers(getLocker(loc1).getUsers());
		getLocker(loc2).setLastInteract();
	}
}
