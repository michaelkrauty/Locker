package me.michaelkrauty.Locker.listeners;

import me.michaelkrauty.Locker.Locker;
import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class PlayerListener implements Listener {

	private static Main main;

	public PlayerListener(Main instance) {
		main = instance;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Block clickedBlock = event.getClickedBlock();
		if (clickedBlock == null)
			return;
		if (clickedBlock.getType() == Material.CHEST) {
			if (main.lockerExists(clickedBlock.getLocation())) {
				Locker locker = main.getLocker(clickedBlock.getLocation());
				if (!locker.userHasAccess(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage(ChatColor.GRAY + "This chest is owned by " + main.getServer().getOfflinePlayer(main.getLocker(clickedBlock.getLocation()).getOwner()).getName());
					event.setCancelled(true);
					return;
				}
				return;
			}
			return;
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock().getWorld().getBlockAt(event.getClickedBlock().getX() + event.getBlockFace().getModX(), event.getClickedBlock().getY() + event.getBlockFace().getModY(), event.getClickedBlock().getZ() + event.getBlockFace().getModZ());
			Location blockLocation = block.getLocation();
			World w = blockLocation.getWorld();
			int x = blockLocation.getBlockX();
			int y = blockLocation.getBlockY();
			int z = blockLocation.getBlockZ();
			if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
				Location loc = new Location(w, x + 1, y, z);
				if (main.getLocker(loc) != null)
					main.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
				Location loc = new Location(w, x - 1, y, z);
				if (main.getLocker(loc) != null)
					main.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
				Location loc = new Location(w, x, y, z + 1);
				if (main.getLocker(loc) != null)
					main.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
				Location loc = new Location(w, x, y, z - 1);
				if (main.getLocker(loc) != null)
					main.copyLocker(loc, blockLocation);
				return;
			}
		}
	}
}
