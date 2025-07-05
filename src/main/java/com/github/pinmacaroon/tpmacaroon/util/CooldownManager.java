package com.github.pinmacaroon.tpmacaroon.util;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    public static final long DEFAULT_COOLDOWN = 15L;
    public final long local_default_cooldown;

    public CooldownManager(long default_cooldown) {
        this.local_default_cooldown = default_cooldown;
    }

    public CooldownManager() {
        this.local_default_cooldown = DEFAULT_COOLDOWN;
    }

    private final Map<UUID, Long> cooldownPlayers = new HashMap<>();

    public void addCooldown(UUID player) {
        this.cooldownPlayers.put(player, Instant.now().getEpochSecond() + this.local_default_cooldown);
    }

    public void addCooldown(UUID player, long cooldown) {
        this.cooldownPlayers.put(player, Instant.now().getEpochSecond() + cooldown);
    }

    public void removeCooldown(UUID player) {
        this.cooldownPlayers.remove(player);
    }

    public long getCooldownEndTimestamp(UUID player){
        return this.cooldownPlayers.get(player);
    }

    public long getCooldownLeft(UUID player) {
        return this.cooldownPlayers.get(player) - Instant.now().getEpochSecond();
    }

    public boolean isOnCooldown(UUID player) {
        if(!this.cooldownPlayers.containsKey(player)) return false;
        else return this.getCooldownLeft(player) > 0;
    }
}
