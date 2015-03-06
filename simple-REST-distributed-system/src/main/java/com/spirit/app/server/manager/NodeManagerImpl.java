package com.spirit.app.server.manager;

import com.spirit.app.server.core.CLinkedList;
import com.spirit.app.server.core.Node;
import com.spirit.app.server.exception.NodeNotFoundException;
import com.spirit.app.server.exception.ResultNotFoundException;
import com.spirit.app.server.net.QueryClient;
import com.spirit.app.server.protocol.LookupRequest;
import com.spirit.app.server.protocol.Request;
import com.spirit.app.server.protocol.StoreRequest;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Interface to the services, provides access to the nodes in ring
 * and do lookup and store new key value pairs
 */
public class NodeManagerImpl implements NodeManager {

    public static final Logger LOG = Logger.getLogger(NodeManagerImpl.class);

    private CLinkedList dhtRing;
    private List<Node> nodeList;

    public NodeManagerImpl(CLinkedList dhtRing, List<Node> nodeList) {
        this.dhtRing = dhtRing;
        this.nodeList = nodeList;
    }

    /**
     * Initialize the ring with nodes
     */
    public void initialize(){
        LOG.info("Init. add nodes to the ring.");
        for (Node node : nodeList) {
            dhtRing.join(node);
        }
        LOG.info("Init. nodes are added to the ring");
    }

    /**
     * Find node in the ring node by id
     * @param id id of the node to find
     * @return Node object
     * @throws NodeNotFoundException
     */
    public Node findNodeById(int id) throws NodeNotFoundException {
        return dhtRing.findNode(id);
    }

    /**
     * Store the key value pair on a node
     * @param node where to store the pair
     * @param key int key of the pair
     * @param value string value of the pair
     */
    public void store(Node node, int key, String value) {
        //Create a query protocol to be sent to the server
        Request storeRequest = new StoreRequest("store", key, value);
        //send query to node via a tcp/ip client
        queryNode(storeRequest, node);
    }

    /**
     * Lookup the value for the key starting from the provided node in ring
     * @param startNode starting node
     * @param key for which value to look for
     * @return String value of the key
     * @throws ResultNotFoundException
     */
    public String lookup(Node startNode, int key) throws ResultNotFoundException {
        //Create a query protocol to be sent to the server
        Request lookupRequest = new LookupRequest("lookup", key);
        Node currentNode = startNode;
        //iterate till you reach the starting point again which means not found
        do{
            //send query to node via a tcp/ip client
            String result = queryNode(lookupRequest, currentNode);
            if(result != null)
                return result;
            currentNode = currentNode.getNextNode();
        }while (currentNode.getId() != startNode.getId());

        throw new ResultNotFoundException("Failed to lookup value for the key.");
    }

    public String ringView() {
        return this.dhtRing.ringView();
    }

    private String queryNode(Request request, Node node){
        QueryClient queryClient = new QueryClient();
        return queryClient.sendQueryString(node.getPort(), request);
    }

}
