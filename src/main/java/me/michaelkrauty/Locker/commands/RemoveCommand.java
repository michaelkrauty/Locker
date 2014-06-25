package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class RemoveCommand {

	private Main main;

	public RemoveCommand(Main instance, Player player, Command cmd, String commandLabel, String[] args) {
		main = instance;
		Block targetBlock = player.getTargetBlock(null, 10);
		Location targetBlockLocation = targetBlock.getLocation();
		if (targetBlock.getType() != Material.CHEST) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "chest" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		if (main.getDataFile().getString(main.locationToString(targetBlockLocation)) == null) {
			player.sendMessage(ChatColor.GRAY + "That chest isn't locked!");
			return;
		}
		if (!main.getDataFile().getString(main.locationToString(targetBlockLocation)).split(",")[0].equals(player.getUniqueId().toString())) {
			player.sendMessage(ChatColor.GRAY + "You don't own that chest!");
			return;
		}
		main.getDataFile().delete(main.locationToString(targetBlockLocation));
		World w = targetBlockLocation.getWorld();
		int x = targetBlockLocation.getBlockX();
		int y = targetBlockLocation.getBlockY();
		int z = targetBlockLocation.getBlockZ();
		if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
			Location loc = new Location(w, x + 1, y, z);
			main.getDataFile().delete(main.locationToString(loc));
		}
		if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
			Location loc = new Location(w, x - 1, y, z);
			main.getDataFile().delete(main.locationToString(loc));
		}
		if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
			Location loc = new Location(w, x, y, z + 1);
			main.getDataFile().delete(main.locationToString(loc));
		}
		if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
			Location loc = new Location(w, x, y, z - 1);
			main.getDataFile().delete(main.locationToString(loc));
		}
		player.sendMessage(ChatColor.GRAY + "You successfully removed the lock from that chest.");
	}
}
