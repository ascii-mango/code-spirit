package com.spirit.app.server.core;

import com.spirit.app.server.exception.NodeNotFoundException;

/**
 * circular link list/Ring of nodes
 * Each node know which is its next node, the next of last node is the first node
 * The purpose is to know other nodes that exist, nodes can be added removed flexibility
 * */
public class CLinkedList {
    Node head;
    Node tail;
    int size = 0 ;

    /**
     * Add node to the ring, update size of the ring and add information about other nodes to the node
     * @param newNode node object to add to the ring
     */
    public void join(Node newNode){
        newNode.setNextNode(this.head);
        if(this.tail == null)
            this.head = newNode;
        else
            this.tail.setNextNode(newNode);

        this.tail = newNode;
        this.size += 1;
    }

    /**
     * Find a node by its id in the ring
     * @param id id of the node to search
     * @return Node object
     * @throws NodeNotFoundException exception if node is not found in the ring
     */
    public Node findNode(int id) throws NodeNotFoundException {
        Node node = this.head;

        do{
            if(node.getId() == id)
                return node;
            node = node.getNextNode();
        }while (node.getId() != this.head.getId());

        throw new NodeNotFoundException("Node with id:" + id + " doesn't exist in the ring");
    }

    /**
     * Utility function to give an overview of ring
     * @return String giving information for each node
     */
    public String ringView(){
        Node node = this.head;
        String view = "";

        do{
             view = view.concat(node.toString() + "|||");
             node = node.getNextNode();
        }while (node.getId() != this.head.getId());

        return view;
    }

}
