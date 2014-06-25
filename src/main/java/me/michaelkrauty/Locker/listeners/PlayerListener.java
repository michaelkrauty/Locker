package me.michaelkrauty.Locker.listeners;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

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
		if (event.getClickedBlock().getType() == Material.CHEST) {
			if (isProtected(event.getClickedBlock().getLocation())) {
				if (!playerHasAccess(event.getPlayer(), event.getClickedBlock().getLocation())) {
					event.getPlayer().sendMessage(ChatColor.GRAY + "This chest is owned by " + getChestOwner(event.getClickedBlock().getLocation()));
					event.setCancelled(true);
				}
			}
		}
	}

	private boolean isProtected(Location loc) {
		if (main.getDataFile().getString(main.locationToString(loc)) != null) {
			return true;
		}
		return false;
	}

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
