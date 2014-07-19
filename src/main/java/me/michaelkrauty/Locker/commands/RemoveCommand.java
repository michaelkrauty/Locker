package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Locker;
import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class RemoveCommand {

	public RemoveCommand(CommandSender sender, Main main) {
		Player player = (Player) sender;
		Block targetBlock = player.getTargetBlock(null, 10);
		Material blockType = targetBlock.getType();
		Location targetBlockLocation = targetBlock.getLocation();
		if (blockType != Material.CHEST
				&& blockType != Material.TRAPPED_CHEST
				&& blockType != Material.FURNACE
				&& blockType != Material.BURNING_FURNACE) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "container" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		Locker locker = main.getLocker(targetBlockLocation);
		if (locker == null) {
			player.sendMessage(ChatColor.GRAY + "That container isn't locked!");
			return;
		}
		if (!locker.userHasAccess(player.getUniqueId())) {
			if (!player.hasPermission("locker.admin")) {
				player.sendMessage(ChatColor.GRAY + "You don't have access to locker!");
				return;
			}
		}
		locker.delete();

		if (blockType == Material.CHEST || blockType == Material.TRAPPED_CHEST) {
			World w = targetBlockLocation.getWorld();
			int x = targetBlockLocation.getBlockX();
			int y = targetBlockLocation.getBlockY();
			int z = targetBlockLocation.getBlockZ();
			if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
				Location loc = new Location(w, x + 1, y, z);
				Locker lock;
				if ((lock = main.getLocker(loc)) != null) {
					lock.delete();
				}
			}
			if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
				Location loc = new Location(w, x - 1, y, z);
				Locker lock;
				if ((lock = main.getLocker(loc)) != null) {
					lock.delete();
				}
			}
			if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
				Location loc = new Location(w, x, y, z + 1);
				Locker lock;
				if ((lock = main.getLocker(loc)) != null) {
					lock.delete();
				}
			}
			if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
				Location loc = new Location(w, x, y, z - 1);
				Locker lock;
				if ((lock = main.getLocker(loc)) != null) {
					lock.delete();
				}
			}
		}

		player.sendMessage(ChatColor.GRAY + "You successfully removed the lock from that chest.");
		return;
	}
}
