package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

			ArrayList<String> users = new ArrayList<String>();
			for (String user : main.getDataFile().getString(main.locationToString(main.editing.get(player.getUniqueId().toString()))).split(",")) {
				users.add(main.getServer().getOfflinePlayer(UUID.fromString(user)).getName());
			}
			player.sendMessage(ChatColor.GRAY + "Users: " + users.toString());
		}
	}
}
