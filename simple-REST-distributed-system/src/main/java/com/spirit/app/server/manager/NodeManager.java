package com.spirit.app.server.manager;

import com.spirit.app.server.core.Node;
import com.spirit.app.server.exception.NodeNotFoundException;
import com.spirit.app.server.exception.ResultNotFoundException;

/**
 * Interface to the services, provides access to the nodes in ring
 * and do lookup and store new key value pairs
 */
public interface NodeManager {
    Node findNodeById(int id) throws NodeNotFoundException;
    void store(Node startNode, int key, String value);
    String lookup(Node startNode, int key) throws ResultNotFoundException;
    String ringView();
}
