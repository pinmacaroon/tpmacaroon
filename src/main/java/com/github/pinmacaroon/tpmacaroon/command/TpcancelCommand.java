package com.github.pinmacaroon.tpmacaroon.command;

import com.github.pinmacaroon.tpmacaroon.PluginMain;
import com.github.pinmacaroon.tpmacaroon.tp.TpManager;
import com.github.pinmacaroon.tpmacaroon.util.CooldownManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpcancelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player author)) {
            sender.spigot().sendMessage(Messages.SENDER_MUST_BE_PLAYER);
            return true;
        }

        TpManager.TP_MANAGER.removeRequest(author);
        author.spigot().sendMessage(Messages.CANCELLED_REQUEST);
        return true;
    }
}
