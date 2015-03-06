package com.spirit.app.server.protocol;

/**
 * Store request query
 **/
public class StoreRequest extends Request{

    private String value;

    public StoreRequest(String name, int key, String value) {
        super(name, key);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
