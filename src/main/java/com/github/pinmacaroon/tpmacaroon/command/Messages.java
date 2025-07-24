package com.github.pinmacaroon.tpmacaroon.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;

import java.awt.*;

public class Messages {
    public static final BaseComponent[] SENDER_MUST_BE_PLAYER = new ComponentBuilder()
            .append("Sender must be instanceof Player!")
            .color(ChatColor.DARK_RED)
            .create();

    public static final BaseComponent[] NOT_ENOUGH_ARGS = new ComponentBuilder()
            .append("Not enough or too many arguments!")
            .color(ChatColor.DARK_RED)
            .create();

    public static final BaseComponent[] ALREADY_HAVE_OUTGOING = new ComponentBuilder()
            .append("You already have an outgoing request!")
            .color(ChatColor.DARK_RED)
            .create();

    public static final BaseComponent[] CANCELLED_REQUEST = new ComponentBuilder()
            .append("Successfully cancelled your request!")
            .color(ChatColor.GREEN)
            .create();

    public static BaseComponent[] PLAYER_NOT_FOUND(String name){
        return new ComponentBuilder()
                .append("Player " + name + " was not found!")
                .color(ChatColor.DARK_RED)
                .create();
    }
    public static BaseComponent[] NO_REQUEST(String name){
        return new ComponentBuilder()
                .append("Player " + name + " was not found!")
                .color(ChatColor.DARK_RED)
                .create();
    }
    public static BaseComponent[] REQUEST_EXPIRED(String name){
        return new ComponentBuilder()
                .append("Player " + name + "'s request has expired!")
                .color(ChatColor.DARK_RED)
                .create();
    }

    public static BaseComponent[] TELEPORTING_TO(String name){
        return new ComponentBuilder()
                .append("Teleporting to " + name + "!")
                .color(ChatColor.GREEN)
                .create();
    }

    public static BaseComponent[] HAS_TELEPORTED_TO_YOU(String name){
        return new ComponentBuilder()
                .append(name + " has teleported to you!")
                .color(ChatColor.GREEN)
                .create();
    }

    public static BaseComponent[] REQUEST_HAS_BEEN_DENIED(String name){
        return new ComponentBuilder()
                .append(name + "'s request has been denied!")
                .color(ChatColor.GREEN)
                .create();
    }

    public static BaseComponent[] DENIED_REQUEST(String name){
        return new ComponentBuilder()
                .append(name + " has denied your request!")
                .color(ChatColor.DARK_RED)
                .create();
    }

    public static BaseComponent[] COOLDOWNED(long i){
        return new ComponentBuilder()
                .append("You must not use this command for " + i + " seconds!")
                .color(ChatColor.DARK_RED)
                .create();
    }

    public static BaseComponent[] HAS_SENT_YOU_A_REQUEST(String name){
        TextComponent button = new TextComponent(" [ACCEPT]");
        button.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tpaccept " + name));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/tpaccept " + name).create()));
        button.setItalic(true);
        button.setColor(ChatColor.GRAY);
        return new ComponentBuilder()
                .append(name + " is asking to teleport to you!")
                .color(ChatColor.YELLOW)
                .append(button)
                .create();
    }

    public static BaseComponent[] YOU_SENT_REQUEST(String name){
        return new ComponentBuilder()
                .append("You sent a teleport request to " + name + "!")
                .color(ChatColor.GREEN)
                .create();
    }

}
