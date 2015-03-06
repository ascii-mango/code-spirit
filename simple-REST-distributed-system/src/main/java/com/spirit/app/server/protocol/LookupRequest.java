package com.spirit.app.server.protocol;

/**
 *Lookup request query
 * */
public class LookupRequest extends Request {
    public LookupRequest(String name, int key) {
        super(name, key);
    }
}
