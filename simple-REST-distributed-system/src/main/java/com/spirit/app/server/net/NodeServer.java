package com.spirit.app.server.net;

import com.spirit.app.server.core.Node;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NodeServer {

   public static final Logger LOG = Logger.getLogger(NodeServer.class);

   private ServerSocket server;
   private Node node;

    public NodeServer(Node node) {
        this.node = node;
        //start server socket and keep listening
        this.createServerSocket();
    }

    private void createServerSocket(){
        LOG.info("Node starting on port: " + this.node.getPort());
        try {
            server = new ServerSocket(this.node.getPort());
        } catch (IOException e) {
            LOG.error("Error creating server socket. on port:" +
                                this.node.getPort() +
                                " error message: " +
                                e.getMessage());
        }
    }

    public void handleConnection() throws IOException {
        LOG.info("Waiting for other nodes messages...");
        while (true) {
                Socket socket = server.accept();
                new ConnectionHandler(socket, this.node);
        }
    }
}
