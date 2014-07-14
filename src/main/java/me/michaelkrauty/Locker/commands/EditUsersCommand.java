package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Locker;
import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class EditUsersCommand {

	public EditUsersCommand(CommandSender sender, String[] args, Main main) {
		Player player = (Player) sender;
		Location targetBlockLocation = player.getTargetBlock(null, 10).getLocation();
		Material blockType = player.getTargetBlock(null, 10).getType();
		if (blockType != Material.CHEST
				&& blockType != Material.TRAPPED_CHEST
				&& blockType != Material.FURNACE
				&& blockType != Material.BURNING_FURNACE) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "container" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		Locker locker = main.getLocker(targetBlockLocation);
		if (locker == null) {
			player.sendMessage(ChatColor.GRAY + "That container isn't locked.");
			return;
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.GRAY + "Users: " + locker.getUserNames());
			player.sendMessage(ChatColor.GRAY + "Add/Remove players with " + ChatColor.RED + "/locker users " + ChatColor.GRAY + "<" + ChatColor.GREEN + "add" + ChatColor.GRAY + "/" + ChatColor.GREEN + "remove" + ChatColor.GRAY + "> <" + ChatColor.GREEN + "user" + ChatColor.GRAY + ">" + ChatColor.GRAY + ".");
			return;
		}
		if (!locker.userIsOwner(player.getUniqueId())) {
			player.sendMessage(ChatColor.GRAY + "You don't own that locker!");
			return;
		}
		if (args.length == 3) {
			if (args[1].equalsIgnoreCase("add")) {
				if (main.getServer().getOfflinePlayer(args[2]) == null) {
					player.sendMessage(ChatColor.GRAY + "That player doesn't exist!");
					return;
				}
				locker.addUser(main.getServer().getOfflinePlayer(args[2]).getUniqueId());

				if (blockType == Material.CHEST || blockType == Material.TRAPPED_CHEST) {
					World w = targetBlockLocation.getWorld();
					int x = targetBlockLocation.getBlockX();
					int y = targetBlockLocation.getBlockY();
					int z = targetBlockLocation.getBlockZ();
					if (w.getBlockAt(x + 1, y, z).getType() == blockType) {
						Location loc = new Location(w, x + 1, y, z);
						if (main.getLocker(loc) != null)
							main.copyLocker(targetBlockLocation, loc);
					}
					if (w.getBlockAt(x - 1, y, z).getType() == blockType) {
						Location loc = new Location(w, x - 1, y, z);
						if (main.getLocker(loc) != null)
							main.copyLocker(targetBlockLocation, loc);
					}
					if (w.getBlockAt(x, y, z + 1).getType() == blockType) {
						Location loc = new Location(w, x, y, z + 1);
						if (main.getLocker(loc) != null)
							main.copyLocker(targetBlockLocation, loc);
					}
					if (w.getBlockAt(x, y, z - 1).getType() == blockType) {
						Location loc = new Location(w, x, y, z - 1);
						if (main.getLocker(loc) != null)
							main.copyLocker(targetBlockLocation, loc);
					}
				}

				player.sendMessage(ChatColor.GRAY + "Added the player " + args[2] + " to the locker.");
				return;
			}
			if (args[1].equalsIgnoreCase("remove")) {
				if (!locker.userHasAccess(main.getServer().getOfflinePlayer(args[2]).getUniqueId())) {
					player.sendMessage(ChatColor.GRAY + "That player isn't added to that locker.");
					return;
				}
				if (locker.userIsOwner(main.getServer().getOfflinePlayer(args[2]).getUniqueId())) {
					player.sendMessage(ChatColor.GRAY + "You can't remove yourself from your own locker!");
					player.sendMessage(ChatColor.GRAY + "Remove the chest with " + ChatColor.RED + "/locker remove");
					return;
				}
				locker.removeUser(main.getServer().getOfflinePlayer(args[2]).getUniqueId());


				if (blockType == Material.CHEST || blockType == Material.TRAPPED_CHEST) {
					World w = targetBlockLocation.getWorld();
					int x = targetBlockLocation.getBlockX();
					int y = targetBlockLocation.getBlockY();
					int z = targetBlockLocation.getBlockZ();
					if (w.getBlockAt(x + 1, y, z).getType() == blockType) {
						Location loc = new Location(w, x + 1, y, z);
						if (main.getLocker(loc) != null)
							main.getLocker(loc).removeUser(main.getServer().getOfflinePlayer(args[2]).getUniqueId());
					}
					if (w.getBlockAt(x - 1, y, z).getType() == blockType) {
						Location loc = new Location(w, x - 1, y, z);
						if (main.getLocker(loc) != null)
							main.getLocker(loc).removeUser(main.getServer().getOfflinePlayer(args[2]).getUniqueId());
					}
					if (w.getBlockAt(x, y, z + 1).getType() == blockType) {
						Location loc = new Location(w, x, y, z + 1);
						if (main.getLocker(loc) != null)
							main.getLocker(loc).removeUser(main.getServer().getOfflinePlayer(args[2]).getUniqueId());
					}
					if (w.getBlockAt(x, y, z - 1).getType() == blockType) {
						Location loc = new Location(w, x, y, z - 1);
						if (main.getLocker(loc) != null)
							main.getLocker(loc).removeUser(main.getServer().getOfflinePlayer(args[2]).getUniqueId());
					}
				}


				player.sendMessage(ChatColor.GRAY + "Removed the player " + args[2] + " from the locker.");
				return;
			}
			return;
		}
		return;
	}
}
