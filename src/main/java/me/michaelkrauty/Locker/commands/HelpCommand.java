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
public class HelpCommand {

	private Main main;

	public HelpCommand(Main instance, Player player, Command cmd, String commandLabel, String[] args) {
		main = instance;
		player.sendMessage(ChatColor.GRAY + "---help & stuff---");
		player.sendMessage(ChatColor.GRAY + "**Use commands looking directly at any chest**");
		player.sendMessage(ChatColor.RED + "/locker create " + ChatColor.GRAY + "claim a chest");
		player.sendMessage(ChatColor.RED + "/locker remove " + ChatColor.GRAY + "unclaim a chest");
		player.sendMessage(ChatColor.RED + "/locker users " + ChatColor.GRAY + "see who can access a chest");
		player.sendMessage(ChatColor.RED + "/locker users " + ChatColor.RED + "<add/remove> " + ChatColor.GRAY + "manage chest access");
		player.sendMessage(ChatColor.GREEN + "-" + ChatColor.RED + "Breaking a locked chest will also break the lock.");
	}
}
