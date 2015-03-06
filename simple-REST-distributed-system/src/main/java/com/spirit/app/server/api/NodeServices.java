package com.spirit.app.server.api;

import com.spirit.app.server.core.Node;
import com.spirit.app.server.exception.NodeNotFoundException;
import com.spirit.app.server.exception.ResultNotFoundException;
import com.spirit.app.server.manager.NodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


/**
 * A Jersey REST service for client access to nodes cluster
 */
@Component
@Path("/nodeService")
public class NodeServices {

    @Autowired
    NodeManager nodeManager;


    /**
     * Gives an overview of the ring created by distributed nodes
     * @return String value with details for each node
     */
    @GET
    @Path("/ringView/")
	public Response getNodeRingView() {
            return Response.status(200).entity(nodeManager.ringView()).build();
	}


    /**
     * find a value from any of the nodes in the ring
     * @param startNode id of node from where the search should start
     * @param key the key for which the value should be searched
     * @return value of the key otherwise the error message
     */
	@GET
	@Path("/lookup/{startNode}/{key}/")
	public Response lookupValues(@PathParam("startNode") String startNode, @PathParam("key") String key) {

        String value;
        try {
            Node node = nodeManager.findNodeById(Integer.valueOf(startNode));
            value = nodeManager.lookup(node , Integer.valueOf(key));
            return Response.status(200).entity(value).build();
        } catch (ResultNotFoundException e) {
            return Response.status(200).entity(e.getMessage()).build();
        } catch (NodeNotFoundException e) {
           return Response.status(200).entity(e.getMessage()).build();
        }

    }

    /**
     * Store a key value pair on a node
     * @param node node where the pair should be stored
     * @param key key of the pair
     * @param value value of the pair
     * @return String value stored if success otherwise the error message
     */
    @GET
    @Path("/store/{node}/{key}/{value}")
	public Response storeValues(@PathParam("node") String node, @PathParam("key") String key, @PathParam("value") String value) {

        try {
            Node ringNode = nodeManager.findNodeById(Integer.valueOf(node));
            nodeManager.store(ringNode, Integer.valueOf(key), value);
            return Response.status(200).entity("Stored").build();
        } catch (NodeNotFoundException e) {
            return Response.status(200).entity(e.getMessage()).build();
        }
	}



}
