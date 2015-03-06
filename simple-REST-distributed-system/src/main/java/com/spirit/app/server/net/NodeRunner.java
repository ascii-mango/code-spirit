package com.spirit.app.server.net;

import com.spirit.app.server.core.Node;
import org.apache.log4j.Logger;

import java.io.IOException;

public class NodeRunner implements Runnable{
    public static final Logger LOG = Logger.getLogger(NodeRunner.class);

    private Node node;

    public NodeRunner(Node node) {
        this.node = node;
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        NodeServer nodeServer = new NodeServer(this.node);
        try {
            //make non blocking handle each connection separately
            nodeServer.handleConnection();
        } catch (IOException e) {
            LOG.error("Failed to start Node server handler. on port: " +
                    this.node.getPort() +
                    " error message: " + e.getMessage());
        }
    }
}
