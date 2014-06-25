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
public class CreateCommand {

	private Main main;

	public CreateCommand(Main instance, Player player, Command cmd, String commandLabel, String[] args) {
		main = instance;
		if (!main.createQueue.contains(player.getUniqueId().toString())) {
			main.createQueue.add(player.getUniqueId().toString());
			player.sendMessage(ChatColor.GRAY + "Now select the chest you want to lock");
			player.sendMessage(ChatColor.GRAY + "Use " + ChatColor.RED + "/locker cancel" + ChatColor.GRAY + " to cancel chest locking.");
			return;
		}
		player.sendMessage(ChatColor.GRAY + "Already locking a chest!");
		return;
	}
}
