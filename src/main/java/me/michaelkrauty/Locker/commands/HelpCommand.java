package me.michaelkrauty.Locker.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class HelpCommand {

	public HelpCommand(Player player) {
		player.sendMessage(ChatColor.DARK_GRAY + "------" + ChatColor.GRAY + "[ " + ChatColor.DARK_PURPLE + "Locker Help " + ChatColor.GRAY + "]" + ChatColor.DARK_GRAY + "------");
		player.sendMessage(ChatColor.RED + "** " + ChatColor.LIGHT_PURPLE + "Use commands looking directly at any container " + ChatColor.LIGHT_PURPLE + "**");
		player.sendMessage(ChatColor.LIGHT_PURPLE + "/locker lock " + ChatColor.GRAY + "lock the targeted container");
		player.sendMessage(ChatColor.LIGHT_PURPLE + "/locker unlock " + ChatColor.GRAY + "unlock the targeted container" + ChatColor.DARK_GRAY + " (" + ChatColor.GRAY + "you must be its owner" + ChatColor.DARK_GRAY + ")");
		player.sendMessage(ChatColor.LIGHT_PURPLE + "/locker users " + ChatColor.GRAY + "see who can access the targeted container");
		player.sendMessage(ChatColor.LIGHT_PURPLE + "/locker users " + ChatColor.DARK_PURPLE + "<add/remove> " + ChatColor.GRAY + "manage locker access");
		player.sendMessage(ChatColor.GRAY + "-" + ChatColor.RED + "Protection will expire if you don't open your locker in " + ChatColor.DARK_RED + "30days");
		player.sendMessage(ChatColor.GRAY + "- " + ChatColor.RED + "Destroying a locked container with explosives will break the lock.");
	}
}
