package com.github.pinmacaroon.tpmacaroon.command;

import com.github.pinmacaroon.tpmacaroon.PluginMain;
import com.github.pinmacaroon.tpmacaroon.tp.TpManager;
import com.github.pinmacaroon.tpmacaroon.util.CooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpaskCommand implements CommandExecutor {
    private CooldownManager manager = new CooldownManager(PluginMain.CONFIG.getLong("tpask.cooldown"));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player author)) {
            sender.spigot().sendMessage(Messages.SENDER_MUST_BE_PLAYER);
            return true;
        }
        if(manager.isOnCooldown(author.getUniqueId())) {
            sender.spigot().sendMessage(Messages.COOLDOWNED(manager.getCooldownLeft(author.getUniqueId())));
            return true;
        }
        if (args.length != 1) {
            sender.spigot().sendMessage(Messages.NOT_ENOUGH_ARGS);
            return true;
        }

        Player target = author.getServer().getPlayerExact(args[0]);
        //noinspection StringEquality
        if (target == null || target.getUniqueId().toString() == author.getUniqueId().toString()) {
            sender.spigot().sendMessage(Messages.PLAYER_NOT_FOUND(args[0]));
            return true;
        }
        if (TpManager.TP_MANAGER.getRequestForPlayer(author) != null && !TpManager.TP_MANAGER.getRequestForPlayer(author).isDone()) {
            TpManager.TP_MANAGER.getRequestForPlayer(author).expire();
            if(!TpManager.TP_MANAGER.getRequestForPlayer(author).expired()) {
                sender.spigot().sendMessage(Messages.ALREADY_HAVE_OUTGOING);
                return true;
            }
            if(!TpManager.TP_MANAGER.getRequestForPlayer(author).isDone()) {
                sender.spigot().sendMessage(Messages.ALREADY_HAVE_OUTGOING);
                return true;
            }
        }

        TpManager.TP_MANAGER.addTpRequest(author, target);

        target.spigot().sendMessage(Messages.HAS_SENT_YOU_A_REQUEST(author.getName()));
        author.spigot().sendMessage(Messages.YOU_SENT_REQUEST(target.getName()));
        manager.addCooldown(author.getUniqueId());
        return true;
    }
}
