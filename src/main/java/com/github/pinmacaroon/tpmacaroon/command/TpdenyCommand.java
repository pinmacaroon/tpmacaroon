package com.github.pinmacaroon.tpmacaroon.command;

import com.github.pinmacaroon.tpmacaroon.tp.TpManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpdenyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player target)) {
            sender.spigot().sendMessage(Messages.SENDER_MUST_BE_PLAYER);
            return true;
        }
        if (args.length != 1) {
            sender.spigot().sendMessage(Messages.NOT_ENOUGH_ARGS);
            return true;
        }

        Player author = target.getServer().getPlayerExact(args[0]);
        if (author == null) {
            sender.spigot().sendMessage(Messages.PLAYER_NOT_FOUND(args[0]));
            return true;
        }

        if(TpManager.TP_MANAGER.getRequestForPlayer(author) == null) {
            sender.spigot().sendMessage(Messages.NO_REQUEST(args[0]));
            return true;
        }

        if(TpManager.TP_MANAGER.getRequestForPlayer(author).isDone() || TpManager.TP_MANAGER.getRequestForPlayer(author).expired()) {
            sender.spigot().sendMessage(Messages.REQUEST_EXPIRED(args[0]));
            TpManager.TP_MANAGER.removeRequest(author);
            return true;
        }

        TpManager.TP_MANAGER.removeRequest(author);
        target.spigot().sendMessage(Messages.REQUEST_HAS_BEEN_DENIED(author.getName()));
        author.spigot().sendMessage(Messages.DENIED_REQUEST(target.getName()));
        return true;
    }
}
