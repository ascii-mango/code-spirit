package com.spirit.app.server.core;

import com.spirit.app.server.net.NodeRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Data structure to hold the node information
 */
public class Node{

    private int id;

    private String ip;
    private int port;

    //each node knows about the next node
    private Node nextNode;

    //map to hold the key value pairs
    private final Map<Integer, String> dataTable = Collections.synchronizedMap(new HashMap<Integer, String>());

    public Node(int id, String ip, int port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
        //open a server socket for each node on port
        new NodeRunner(this);
    }

    public String getIp() {
        return ip;
    }


    public int getPort() {
        return port;
    }


    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }


    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Node id:" + this.id +
                " ip:" + this.getIp()+
                " port:" + this.getPort()+
                " node next id:" + this.getNextNode().getId();
    }

    public String findValue(int key) {
        //no need to make thread safe as put is already made thread safe
        return this.dataTable.get(key);
    }

    public void storeValue(int key, String value) {
        //make it thread safe, as multiple http clients might try to put
        synchronized (this.dataTable){
            this.dataTable.put(key, value);
        }
    }
}
