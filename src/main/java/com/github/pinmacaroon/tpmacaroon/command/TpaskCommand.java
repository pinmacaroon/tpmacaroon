package com.github.pinmacaroon.tpmacaroon.command;

import com.github.pinmacaroon.tpmacaroon.PluginMain;
import com.github.pinmacaroon.tpmacaroon.tp.TpManager;
import com.github.pinmacaroon.tpmacaroon.util.CooldownManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpaskCommand implements CommandExecutor {
    private CooldownManager manager = new CooldownManager(PluginMain.CONFIG.getLong("tpask.cooldown"));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Sender must be instanceof Player!");
            return true;
        }
        Player author = (Player) sender;
        if(manager.isOnCooldown(author.getUniqueId())) {
            sender.sendMessage("You must not use this command for " + manager.getCooldownLeft(author.getUniqueId()) + " seconds!");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("Not enough or too many arguments!");
            return true;
        }

        Player target = author.getServer().getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("Player " + args[0] + " was not found!");
            return true;
        }
        if (TpManager.TP_MANAGER.getRequestForPlayer(author) != null && !TpManager.TP_MANAGER.getRequestForPlayer(author).isDone()) {
            TpManager.TP_MANAGER.getRequestForPlayer(author).expire();
            if(!TpManager.TP_MANAGER.getRequestForPlayer(author).expired()) {
                sender.sendMessage("You already have an outgoing request");
                return true;
            }
            if(!TpManager.TP_MANAGER.getRequestForPlayer(author).isDone()) {
                sender.sendMessage("You already have an outgoing request");
                return true;
            }
        }

        TpManager.TP_MANAGER.addTpRequest(author, target);

        target.sendMessage(author.getName() + " is asking to teleport to you!", "Accept with \"/tpaccept\"!");
        author.sendMessage("You sent a teleport request to " + author.getName() + "!");
        manager.addCooldown(author.getUniqueId());
        return true;
    }
}
