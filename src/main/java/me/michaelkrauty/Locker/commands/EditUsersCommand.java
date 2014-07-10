package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class EditUsersCommand {

	private Main main;

	public EditUsersCommand(Main instance, Player player, Command cmd, String commandLabel, String[] args) {
		main = instance;
		if (player.getTargetBlock(null, 10).getType() != Material.CHEST) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "chest" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		Location targetBlockLocation = player.getTargetBlock(null, 10).getLocation();
		if (main.getDataFile().getString(main.locationToString(targetBlockLocation) + ".owner") == null) {
			player.sendMessage(ChatColor.GRAY + "That chest isn't locked.");
			return;
		}
		ArrayList<String> userNames = new ArrayList<String>();
		ArrayList<String> userUUIDS = new ArrayList<String>();
		for (String uuid : main.getDataFile().getString(main.locationToString(targetBlockLocation) + ".users").split(",")) {
			UUID uuid1 = UUID.fromString(uuid);
			String name = main.getServer().getOfflinePlayer(uuid1).getName();
			userNames.add(name);
			userUUIDS.add(uuid1.toString());
		}
		if (args.length < 3) {
			player.sendMessage(ChatColor.GRAY + "Expires in: " + main.getDataFile().getInt(main.locationToString(targetBlockLocation) + ".expiry") + " minutes.");
			player.sendMessage(ChatColor.GRAY + "Users: " + userNames.toString());
			player.sendMessage(ChatColor.GRAY + "Add/Remove players with " + ChatColor.RED + "/locker users " + ChatColor.GRAY + "<" + ChatColor.GREEN + "add" + ChatColor.GRAY + "/" + ChatColor.GREEN + "remove" + ChatColor.GRAY + "> <" + ChatColor.GREEN + "user" + ChatColor.GRAY + ">" + ChatColor.GRAY + ".");
			return;
		}
		if (!player.hasPermission("locker.admin") || !main.getDataFile().getString(main.locationToString(targetBlockLocation) + ".owner").equals(player.getUniqueId().toString())) {
			player.sendMessage(ChatColor.GRAY + "You don't own that locker!");
			return;
		}
		if (args.length == 3) {
			if (args[1].equalsIgnoreCase("add")) {
				for (int i = 0; i < userUUIDS.size(); i++) {
					if (userUUIDS.contains(main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString())) {
						player.sendMessage(ChatColor.GRAY + "That player already has access to that chest.");
						return;
					}
				}
				main.getDataFile().set(main.locationToString(targetBlockLocation) + ".users", main.getDataFile().getString(main.locationToString(targetBlockLocation) + ".users") + "," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());


				/** check for other chests */
				World w = targetBlockLocation.getWorld();
				int x = targetBlockLocation.getBlockX();
				int y = targetBlockLocation.getBlockY();
				int z = targetBlockLocation.getBlockZ();
				if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x + 1, y, z);
					main.getDataFile().set(main.locationToString(loc) + ".users", main.getDataFile().getString(main.locationToString(loc) + ".users") + "," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());
				}
				if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x - 1, y, z);
					main.getDataFile().set(main.locationToString(loc) + ".users", main.getDataFile().getString(main.locationToString(loc) + ".users") + "," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());
				}
				if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z + 1);
					main.getDataFile().set(main.locationToString(loc) + ".users", main.getDataFile().getString(main.locationToString(loc) + ".users") + "," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());
				}
				if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z - 1);
					main.getDataFile().set(main.locationToString(loc) + ".users", main.getDataFile().getString(main.locationToString(loc) + ".users") + "," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());
				}
				/** /check for other chests */


				player.sendMessage(ChatColor.GRAY + "Added the player " + args[2] + " to the locker.");
			}
			if (args[1].equalsIgnoreCase("remove")) {
				for (int i = 0; i < userUUIDS.size(); i++) {
					if (!userUUIDS.contains(main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString())) {
						player.sendMessage(ChatColor.GRAY + "That player isn't added to that chest.");
						return;
					}
				}
				main.getDataFile().set(main.locationToString(targetBlockLocation) + ".users", main.getDataFile().getString(main.locationToString(targetBlockLocation) + ".users").replace("," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));


				/** check for other chests */
				World w = targetBlockLocation.getWorld();
				int x = targetBlockLocation.getBlockX();
				int y = targetBlockLocation.getBlockY();
				int z = targetBlockLocation.getBlockZ();
				if (w.getBlockAt(x + 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x + 1, y, z);
					main.getDataFile().set(main.locationToString(loc) + ".users", main.getDataFile().getString(main.locationToString(loc) + ".users").replace("," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));
				}
				if (w.getBlockAt(x - 1, y, z).getType() == Material.CHEST) {
					Location loc = new Location(w, x - 1, y, z);
					main.getDataFile().set(main.locationToString(loc) + ".users", main.getDataFile().getString(main.locationToString(loc) + ".users").replace("," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));
				}
				if (w.getBlockAt(x, y, z + 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z + 1);
					main.getDataFile().set(main.locationToString(loc) + ".users", main.getDataFile().getString(main.locationToString(loc) + ".users").replace("," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));
				}
				if (w.getBlockAt(x, y, z - 1).getType() == Material.CHEST) {
					Location loc = new Location(w, x, y, z - 1);
					main.getDataFile().set(main.locationToString(loc) + ".users", main.getDataFile().getString(main.locationToString(loc) + ".users").replace("," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));
				}
				/** /check for other chests */


				player.sendMessage(ChatColor.GRAY + "Removed the player " + args[2] + " from the locker.");
			}
		}
	}
}
