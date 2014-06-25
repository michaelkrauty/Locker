package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class CancelCommand {

	private Main main;

	public CancelCommand(Main instance, Player player, Command cmd, String commandLabel, String[] args) {
		main = instance;

		Logger.getLogger("MC").info(main.createQueue.toString());
		if (main.createQueue.contains(player.getUniqueId().toString()) || main.delQueue.contains(player.getUniqueId().toString())) {
			if (main.createQueue.contains(player.getUniqueId().toString()) || main.delQueue.contains(player.getUniqueId().toString())) {
				if (main.createQueue.contains(player.getUniqueId().toString())) {
					main.createQueue.remove(player.getUniqueId().toString());
				}
				if (main.delQueue.contains(player.getUniqueId().toString())) {
					main.delQueue.remove(player.getUniqueId().toString());
				}
				player.sendMessage(ChatColor.GRAY + "Cancelled.");
			}
			return;
		}
		player.sendMessage(ChatColor.GRAY + "You're not locking/unlocking a chest!");
		return;
	}
}
