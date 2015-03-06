package com.spirit.app.server.net;

import com.spirit.app.server.core.Node;
import com.spirit.app.server.protocol.LookupRequest;
import com.spirit.app.server.protocol.Request;
import com.spirit.app.server.protocol.StoreRequest;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
    public static final Logger LOG = Logger.getLogger(ConnectionHandler.class);

    private Socket socket;
    private Node node;

    public ConnectionHandler(Socket socket, Node node) {
        this.socket = socket;
        this.node = node;
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String result = processRequest(objectInputStream);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);

            objectInputStream.close();
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String processRequest(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Request request = (Request) objectInputStream.readObject();
        LOG.info("Message Received with request: " + request.getName());
        String result = "";
        if(request instanceof LookupRequest)
            result = this.node.findValue(request.getKey());
        if(request instanceof StoreRequest)
            this.node.storeValue(request.getKey(), ((StoreRequest) request).getValue());
        return result;
    }


}
