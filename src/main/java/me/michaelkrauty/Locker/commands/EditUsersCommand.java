package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
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
		if (main.editing.get(player.getUniqueId().toString()) == null) {
			if (!main.editQueue.contains(player.getUniqueId().toString())) {
				main.editQueue.add(player.getUniqueId().toString());
				player.sendMessage(ChatColor.GRAY + "Now select the locked chest whose users you want to edit");
				player.sendMessage(ChatColor.GRAY + "Use " + ChatColor.RED + "/locker cancel" + ChatColor.GRAY + " to cancel editing.");
				return;
			}
			player.sendMessage(ChatColor.GRAY + "Already editing a chest's users!");
			return;
		} else {
			ArrayList<String> userNames = new ArrayList<String>();
			ArrayList<String> userUUIDS = new ArrayList<String>();
			for (String uuid : main.getDataFile().getString(main.locationToString(main.editing.get(player.getUniqueId().toString()))).split(",")) {
				UUID uuid1 = UUID.fromString(uuid);
				String name = main.getServer().getOfflinePlayer(uuid1).getName();
				userNames.add(name);
				userUUIDS.add(uuid1.toString());
			}
			if (args.length < 3) {
				player.sendMessage(ChatColor.GRAY + "Users: " + userNames.toString());
				player.sendMessage(ChatColor.GRAY + "Add/Remove players with " + ChatColor.RED + "/locker users " + ChatColor.GRAY + "<" + ChatColor.GREEN + "add" + ChatColor.GRAY + "/" + ChatColor.GREEN + "remove" + ChatColor.GRAY + "> <" + ChatColor.GREEN + "user" + ChatColor.GRAY + ">" + ChatColor.GRAY + ".");
			}
			if (args.length == 3) {
				if (args[1].equalsIgnoreCase("add")) {
					for (int i = 0; i < userUUIDS.size(); i++) {
						if (userUUIDS.contains(main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString())) {
							player.sendMessage(ChatColor.GRAY + "That player already has access to that chest.");
							return;
						}
					}
					main.getDataFile().set(main.locationToString(main.editing.get(player.getUniqueId().toString())), main.getDataFile().getString(main.locationToString(main.editing.get(player.getUniqueId().toString()))) + "," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString());
					player.sendMessage(ChatColor.GRAY + "Added the player " + args[2] + " to the locker.");
				}
				if (args[1].equalsIgnoreCase("remove")) {
					for (int i = 0; i < userUUIDS.size(); i++) {
						if (!userUUIDS.contains(main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString())) {
							player.sendMessage(ChatColor.GRAY + "That player isn't added to that chest.");
							return;
						}
					}
					main.getDataFile().set(main.locationToString(main.editing.get(player.getUniqueId().toString())), main.getDataFile().getString(main.locationToString(main.editing.get(player.getUniqueId().toString()))).replace("," + main.getServer().getOfflinePlayer(args[2]).getUniqueId().toString(), ""));
					player.sendMessage(ChatColor.GRAY + "Removed the player " + args[2] + " from the locker.");
				}
			}
		}
	}
}
