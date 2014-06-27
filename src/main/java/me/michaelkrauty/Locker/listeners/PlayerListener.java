package me.michaelkrauty.Locker.listeners;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
		try {
			if (event.getClickedBlock().getType() == Material.CHEST) {
				if (isProtected(event.getClickedBlock().getLocation())) {
					if (!playerHasAccess(event.getPlayer(), event.getClickedBlock().getLocation())) {
						event.getPlayer().sendMessage(ChatColor.GRAY + "This chest is owned by " + main.getDataFile().getString(event.getClickedBlock().getLocation() + ".owner"));
						event.setCancelled(true);
					}
				}
			}
		} catch (Exception ignored) {
		}
	}

	private boolean isProtected(Location loc) {
		if (main.getDataFile().getString(main.locationToString(loc) + ".owner") != null) {
			return true;
		}
		return false;
	}

	private boolean playerHasAccess(Player player, Location loc) {
		boolean access = false;
		for (String str : main.getDataFile().getString(main.locationToString(loc) + ".users").split(",")) {
			if (str.equalsIgnoreCase(player.getUniqueId().toString())) {
				access = true;
			}
		}
		return access;
	}
}
