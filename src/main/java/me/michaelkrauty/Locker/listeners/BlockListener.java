package me.michaelkrauty.Locker.listeners;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.logging.Logger;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class BlockListener implements Listener {

	private static Main main;

	private static Logger log = Logger.getLogger("MC");

	public BlockListener(Main instance) {
		main = instance;
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Block block = event.getBlock();
		Location blockLocation = event.getBlock().getLocation();
		if (block.getType().equals(Material.CHEST)) {
			if (main.getDataFile().getString(main.locationToString(blockLocation)) != null) {
				main.getDataFile().delete(main.locationToString(blockLocation));
				event.getPlayer().sendMessage(ChatColor.GRAY + "Removed locker.");
			}
		}
	}
}
