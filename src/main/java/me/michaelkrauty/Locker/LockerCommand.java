package me.michaelkrauty.Locker;

import me.michaelkrauty.Locker.commands.CreateCommand;
import me.michaelkrauty.Locker.commands.EditUsersCommand;
import me.michaelkrauty.Locker.commands.HelpCommand;
import me.michaelkrauty.Locker.commands.RemoveCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

/**
 * Created on 6/24/2014.
 *
 * @author michaelkrauty
 */
public class LockerCommand implements CommandExecutor {

	private Main main;

	public LockerCommand(Main instance) {
		main = instance;
	}

	Logger log = Logger.getLogger("MC");

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			log.info("You can't lock chests!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length < 1) {
			new HelpCommand(player);
			return true;
		}

		/** locker commands */
		if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("lock") || args[0].equalsIgnoreCase("claim")) {
			new CreateCommand(sender, main);
			return true;
		}
		if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("unlock") || args[0].equalsIgnoreCase("unclaim")) {
			new RemoveCommand(sender, main);
			return true;
		}
		if (args[0].equalsIgnoreCase("users") || args[0].equalsIgnoreCase("user") || args[0].equalsIgnoreCase("owner") || args[0].equalsIgnoreCase("friends") || args[0].equalsIgnoreCase("players") || args[0].equalsIgnoreCase("allowed") || args[0].equalsIgnoreCase("info")) {
			new EditUsersCommand(sender, args, main);
			return true;
		}
		if (args[0].equalsIgnoreCase("help")) {
			new HelpCommand(player);
			return true;
		}

		new HelpCommand(player);
		return true;
	}
}
