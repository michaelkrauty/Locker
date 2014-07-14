package me.michaelkrauty.Locker.commands;

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
public class CreateCommand {

	public CreateCommand(CommandSender sender, Main main) {
		Player player = (Player) sender;
		Block targetBlock = player.getTargetBlock(null, 10);
		Location targetBlockLocation = targetBlock.getLocation();
		Material blockType = targetBlock.getType();
		if (main.getLocker(targetBlockLocation) != null) {
			player.sendMessage(ChatColor.GRAY + "That container is already locked.");
			return;
		}
		if (targetBlock == null) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "container" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		if (blockType != Material.CHEST
				&& blockType != Material.TRAPPED_CHEST
				&& blockType != Material.FURNACE
				&& blockType != Material.BURNING_FURNACE) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "container" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		main.createLocker(targetBlockLocation, player);
		if (blockType == Material.CHEST || blockType == Material.TRAPPED_CHEST) {
			World w = targetBlockLocation.getWorld();
			int x = targetBlockLocation.getBlockX();
			int y = targetBlockLocation.getBlockY();
			int z = targetBlockLocation.getBlockZ();
			if (w.getBlockAt(x + 1, y, z).getType() == blockType) {
				Location loc = new Location(w, x + 1, y, z);
				main.createLocker(loc, player);
			}
			if (w.getBlockAt(x - 1, y, z).getType() == blockType) {
				Location loc = new Location(w, x - 1, y, z);
				main.createLocker(loc, player);
			}
			if (w.getBlockAt(x, y, z + 1).getType() == blockType) {
				Location loc = new Location(w, x, y, z + 1);
				main.createLocker(loc, player);
			}
			if (w.getBlockAt(x, y, z - 1).getType() == blockType) {
				Location loc = new Location(w, x, y, z - 1);
				main.createLocker(loc, player);
			}
		}
		player.sendMessage(ChatColor.GRAY + "You successfully locked that container.");
		return;
	}
}
