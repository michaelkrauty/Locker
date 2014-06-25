package me.michaelkrauty.Locker.listeners;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class PlayerListener implements Listener {

	private static Main main;

	private static Logger log = Logger.getLogger("MC");

	public PlayerListener(Main instance) {
		main = instance;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		// event.getPlayer().sendMessage(ChatColor.GRAY + "https://sessionserver.mojang.com/session/minecraft/profile/" + event.getPlayer().getUniqueId().toString());
		if (main.createQueue.contains(event.getPlayer().getUniqueId().toString())) {
			main.createQueue.remove(event.getPlayer().getUniqueId().toString());

			Block chest1 = event.getClickedBlock();
			Location chest1Location = chest1.getLocation();
			int chest1x = chest1Location.getBlockX();
			int chest1y = chest1Location.getBlockY();
			int chest1z = chest1Location.getBlockZ();
		}
		if (event.getClickedBlock().getType() == Material.CHEST) {
			if (isProtected(event.getClickedBlock().getLocation())) {
				if (!playerHasAccess(event.getPlayer(), event.getClickedBlock().getLocation())) {
					event.getPlayer().sendMessage(ChatColor.GRAY + "This chest is owned by " + getChestOwner(event.getClickedBlock().getLocation()));
					event.setCancelled(true);
				}
			}
		}
	}

	private void addChest(Player player, Location chest1, Location chest2) {
		String world1 = chest1.getWorld().getName();
		int x1 = chest1.getBlockX();
		int y1 = chest1.getBlockY();
		int z1 = chest1.getBlockZ();
		String world2 = chest2.getWorld().getName();
		int x2 = chest2.getBlockX();
		int y2 = chest2.getBlockY();
		int z2 = chest2.getBlockZ();
		main.getDataFile().set(world1 + "," + x1 + "," + y1 + "," + z1 + "," + world2 + "," + x2 + "," + y2 + "," + z2, player.getUniqueId().toString());
	}

	private boolean isProtected(Location loc) {
		return (main.getDataFile().getString(main.locationToString(loc)) != null);
	}

	// TODO
	private boolean playerHasAccess(Player player, Location loc) {
		boolean access = false;
		for (String str : main.getDataFile().getString(main.locationToString(loc)).split(",")) {
			if (str.equalsIgnoreCase(player.getUniqueId().toString())) {
				access = true;
			}
		}
		return access;
	}

	private String getChestOwner(Location loc) {
		return main.getServer().getOfflinePlayer(UUID.fromString(main.getDataFile().getString(main.locationToString(loc)).split(",")[0])).getName();
	}
}
