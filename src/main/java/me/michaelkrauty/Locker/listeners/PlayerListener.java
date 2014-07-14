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
		if (clickedBlock.getType() == Material.CHEST
				|| clickedBlock.getType() == Material.TRAPPED_CHEST
				|| clickedBlock.getType() == Material.FURNACE
				|| clickedBlock.getType() == Material.BURNING_FURNACE) {
			if (!main.copying.isEmpty()) {
				if (main.copying.get(event.getPlayer()) != null) {
					if (main.getLocker(event.getClickedBlock().getLocation()) == null) {

						Location loc1 = main.copying.get(event.getPlayer());
						main.copyLocker(loc1, clickedBlock.getLocation());
						main.copying.remove(event.getPlayer());
						event.getPlayer().sendMessage(ChatColor.GRAY + "Successfully copied the locker.");

						Material blockType = event.getClickedBlock().getType();
						Location targetBlockLocation = event.getClickedBlock().getLocation();

						if (blockType == Material.CHEST || blockType == Material.TRAPPED_CHEST) {
							World w = targetBlockLocation.getWorld();
							int x = targetBlockLocation.getBlockX();
							int y = targetBlockLocation.getBlockY();
							int z = targetBlockLocation.getBlockZ();
							if (w.getBlockAt(x + 1, y, z).getType() == blockType) {
								Location loc = new Location(w, x + 1, y, z);
								main.copyLocker(loc1, loc);
							}
							if (w.getBlockAt(x - 1, y, z).getType() == blockType) {
								Location loc = new Location(w, x - 1, y, z);
								main.copyLocker(loc1, loc);
							}
							if (w.getBlockAt(x, y, z + 1).getType() == blockType) {
								Location loc = new Location(w, x, y, z + 1);
								main.copyLocker(loc1, loc);
							}
							if (w.getBlockAt(x, y, z - 1).getType() == blockType) {
								Location loc = new Location(w, x, y, z - 1);
								main.copyLocker(loc1, loc);
							}
						}
						event.setCancelled(true);
						return;
					}
					event.getPlayer().sendMessage(ChatColor.GRAY + "That locker already exists! Locker still in clipboard.");
					event.setCancelled(true);
					return;
				}
			}
			if (main.lockerExists(clickedBlock.getLocation())) {
				Locker locker = main.getLocker(clickedBlock.getLocation());
				if (!locker.userHasAccess(event.getPlayer().getUniqueId())) {
					event.getPlayer().sendMessage(ChatColor.GRAY + "This locker is owned by " + main.getServer().getOfflinePlayer(main.getLocker(clickedBlock.getLocation()).getOwner()).getName());
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
			if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST
					|| w.getBlockAt(x + 1, y, z).getType() == Material.TRAPPED_CHEST) {
				Location loc = new Location(w, x + 1, y, z);
				if (main.getLocker(loc) != null)
					main.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST
					|| w.getBlockAt(x - 1, y, z).getType() == Material.TRAPPED_CHEST) {
				Location loc = new Location(w, x - 1, y, z);
				if (main.getLocker(loc) != null)
					main.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST
					|| w.getBlockAt(x, y, z + 1).getType() == Material.TRAPPED_CHEST) {
				Location loc = new Location(w, x, y, z + 1);
				if (main.getLocker(loc) != null)
					main.copyLocker(loc, blockLocation);
				return;
			}
			if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST
					|| w.getBlockAt(x, y, z - 1).getType() == Material.TRAPPED_CHEST) {
				Location loc = new Location(w, x, y, z - 1);
				if (main.getLocker(loc) != null)
					main.copyLocker(loc, blockLocation);
				return;
			}
		}
	}
}
