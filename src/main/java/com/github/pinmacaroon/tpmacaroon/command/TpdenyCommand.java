package com.github.pinmacaroon.tpmacaroon.command;

import com.github.pinmacaroon.tpmacaroon.tp.TpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpdenyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Sender must be instanceof Player!");
            return true;
        }
        Player target = (Player) sender;
        if (args.length != 1) {
            sender.sendMessage("Not enough or too many arguments!");
            return true;
        }

        Player author = target.getServer().getPlayerExact(args[0]);
        if (author == null) {
            sender.sendMessage("Player " + args[0] + " was not found!");
            return true;
        }

        if(TpManager.TP_MANAGER.getRequestForPlayer(author) == null) {
            sender.sendMessage("Player " + args[0] + " has not sent you a request!");
            return true;
        }

        if(TpManager.TP_MANAGER.getRequestForPlayer(author).isDone() || TpManager.TP_MANAGER.getRequestForPlayer(author).expired()) {
            sender.sendMessage("Player " + args[0] + "'s request has expired!");
            TpManager.TP_MANAGER.removeRequest(author);
            return true;
        }

        TpManager.TP_MANAGER.removeRequest(author);
        target.sendMessage(author.getName() + "'s request has been denied!");
        author.sendMessage(target.getName() + " has denied your request!");
        return true;
    }
}
