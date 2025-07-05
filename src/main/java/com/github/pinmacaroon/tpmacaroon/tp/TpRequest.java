package com.github.pinmacaroon.tpmacaroon.tp;

import java.time.Instant;
import java.util.UUID;

public class TpRequest {

    private final UUID author;
    private final UUID target;
    private final long expiration;
    private boolean done;

    public TpRequest(UUID author, UUID target, long expiration) {
        this.author = author;
        this.target = target;
        this.expiration = expiration;
        this.done = false;
    }

    public boolean expired() {
        return Instant.now().getEpochSecond() >= expiration;
    }

    public long getExpiration() {
        return expiration;
    }

    public UUID getAuthor() {
        return author;
    }

    public UUID getTarget() {
        return target;
    }

    public boolean isDone() {
        return done;
    }

    public void finish() {
        this.done = true;
    }

    public void expire() {
        if(this.expired()) this.finish();
    }
}
