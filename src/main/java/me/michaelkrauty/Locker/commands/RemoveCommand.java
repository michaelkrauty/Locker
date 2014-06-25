package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
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
		if (!main.delQueue.contains(player.getUniqueId().toString())) {
			main.delQueue.add(player.getUniqueId().toString());
			player.sendMessage(ChatColor.GRAY + "Now select the locked chest you want to remove");
			player.sendMessage(ChatColor.GRAY + "Use " + ChatColor.RED + "/locker cancel" + ChatColor.GRAY + " to cancel removal.");
			return;
		}
		player.sendMessage(ChatColor.GRAY + "Already unlocking a chest!");
		return;
	}
}
