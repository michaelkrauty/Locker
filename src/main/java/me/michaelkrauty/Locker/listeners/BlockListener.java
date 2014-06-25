package me.michaelkrauty.Locker.listeners;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class BlockListener implements Listener {

	private static Main main;

	public BlockListener(Main instance) {
		main = instance;
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Block block = event.getBlock();
		Location blockLocation = block.getLocation();
		if (block.getType() == Material.CHEST) {
			if (main.getDataFile().getString(main.locationToString(blockLocation)) != null) {
				main.getDataFile().delete(main.locationToString(blockLocation));
				event.getPlayer().sendMessage(ChatColor.GRAY + "Removed locker.");
			}
		}
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		Block block = event.getBlock();
		Location blockLocation = block.getLocation();
		if (block.getType() == Material.CHEST) {
			World w = blockLocation.getWorld();
			int x = blockLocation.getBlockX();
			int y = blockLocation.getBlockY();
			int z = blockLocation.getBlockZ();
			Location loc1 = new Location(w, x + 1, y, z);
			Location loc2 = new Location(w, x - 1, y, z);
			Location loc3 = new Location(w, x, y, z + 1);
			Location loc4 = new Location(w, x, y, z - 1);
			if (main.getDataFile().getString(main.locationToString(loc1)) != null) {
				main.getDataFile().set(main.locationToString(blockLocation), main.getDataFile().getString(main.locationToString(loc1)));
			}
			if (main.getDataFile().getString(main.locationToString(loc2)) != null) {
				main.getDataFile().set(main.locationToString(blockLocation), main.getDataFile().getString(main.locationToString(loc2)));
			}
			if (main.getDataFile().getString(main.locationToString(loc3)) != null) {
				main.getDataFile().set(main.locationToString(blockLocation), main.getDataFile().getString(main.locationToString(loc3)));
			}
			if (main.getDataFile().getString(main.locationToString(loc4)) != null) {
				main.getDataFile().set(main.locationToString(blockLocation), main.getDataFile().getString(main.locationToString(loc4)));
			}
		}
	}
}
