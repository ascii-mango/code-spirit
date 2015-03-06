package com.spirit.app.server.net;

import com.spirit.app.server.protocol.Request;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TCP/IP client that opens a connection with server sends the query object
 * returns the result string from node
 */
public class QueryClient {

    public static final Logger LOG = Logger.getLogger(QueryClient.class);

    public String sendQueryString(int port, Request query){
        try {
            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), port);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(query);

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String response = (String) objectInputStream.readObject();
            LOG.info("Response from node server: " + response);

            objectInputStream.close();
            objectOutputStream.close();
            return response;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
