package com.aek.yagoubi.sac;

public class SacClient {

    private Sac sac;
    private Client client;

    public SacClient(Sac sac, Client client) {
        this.sac = sac;
        this.client = client;
    }

    public Sac getSac() {
        return sac;
    }

    public Client getClient() {
        return client;
    }
}
