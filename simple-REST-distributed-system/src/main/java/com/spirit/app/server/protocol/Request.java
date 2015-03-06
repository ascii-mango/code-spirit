package com.spirit.app.server.protocol;

/**
 * Request protocol
 **/
public abstract class Request implements java.io.Serializable{
    private String name;
    private int key;

    protected Request(String name, int key) {
        this.name = name;
        this.key = key;
    }

    public int getKey() {
        return key;
    }


    public String getName() {
        return name;
    }

}
