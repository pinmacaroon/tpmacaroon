package com.github.pinmacaroon.tpmacaroon.tp;

import com.github.pinmacaroon.tpmacaroon.PluginMain;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpManager {
    public static final TpManager TP_MANAGER = new TpManager(PluginMain.CONFIG.getInt("teleport.lifespan", 60));
    private final Map<UUID, TpRequest> requests = new HashMap<>();

    private final int request_lifespan;

    public TpManager(int request_lifespan){
        this.request_lifespan = request_lifespan;
    }

    public TpManager(){
        this.request_lifespan = 60;
    }

    public void addTpRequest(Player author, Player target){
        this.requests.put(
                author.getUniqueId(),
                new TpRequest(
                        author.getUniqueId(),
                        target.getUniqueId(),
                        Instant.now().getEpochSecond() + this.request_lifespan
                )
        );
    }

    //@Nullable
    public TpRequest getRequestForPlayer(Player player){
        return this.requests.get(player.getUniqueId());
    }

    public boolean acceptRequest(Player author, Player target){
        if(!this.requests.containsKey(author.getUniqueId())) return false;
        if(this.requests.get(author.getUniqueId()).expired()) {
            this.requests.get(author.getUniqueId()).expire();
            this.requests.remove(author.getUniqueId());
            return false;
        }
        if(this.requests.get(author.getUniqueId()).isDone()) return false;
        TpRequest request = this.requests.get(author.getUniqueId());
        author.teleport(target);
        request.finish();
        this.requests.remove(author.getUniqueId());
        return true;
    }

    public void removeRequest(Player author){
        this.requests.remove(author.getUniqueId());
    }
}
