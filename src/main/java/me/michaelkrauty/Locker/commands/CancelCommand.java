package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 7/14/2014.
 *
 * @author michaelkrauty
 */
public class CancelCommand {

	public CancelCommand(CommandSender sender, Main main) {
		Player player = (Player) sender;
		if (main.copying.get(player) == null) {
			player.sendMessage(ChatColor.GRAY + "You don't have a locker in your clipboard!");
			return;
		}
		main.copying.remove(player);
		player.sendMessage(ChatColor.GRAY + "Emptied locker clipboard.");
		return;
	}
}
