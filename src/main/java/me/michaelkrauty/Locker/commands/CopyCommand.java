package me.michaelkrauty.Locker.commands;

import me.michaelkrauty.Locker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created on 7/14/2014.
 *
 * @author michaelkrauty
 */
public class CopyCommand {

	public CopyCommand(CommandSender sender, Main main) {
		Player player = (Player) sender;
		Block targetBlock = player.getTargetBlock(null, 10);
		Location targetBlockLocation = targetBlock.getLocation();
		Material blockType = targetBlock.getType();
		if (main.getLocker(targetBlockLocation) == null) {
			player.sendMessage(ChatColor.GRAY + "That container isn't locked.");
			return;
		}
		if (targetBlock == null) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "container" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		if (blockType != Material.CHEST
				&& blockType != Material.TRAPPED_CHEST
				&& blockType != Material.FURNACE
				&& blockType != Material.BURNING_FURNACE) {
			player.sendMessage(ChatColor.GRAY + "Make sure you're looking at a " + ChatColor.GREEN + "container" + ChatColor.GRAY + " within " + ChatColor.GREEN + "10 blocks" + ChatColor.GRAY + " of you");
			return;
		}
		if (!main.getLocker(targetBlockLocation).getOwner().equals(player.getUniqueId())) {
			player.sendMessage(ChatColor.GRAY + "You don't own that locker!");
			return;
		}
		main.copying.put(player, targetBlockLocation);
		player.sendMessage(ChatColor.GRAY + "Copied to clipboard! Now click the container you want to lock");
		player.sendMessage(ChatColor.GRAY + "Use " + ChatColor.GREEN + "/locker cancel" + ChatColor.GRAY + "to cancel.");
		return;
	}
}
