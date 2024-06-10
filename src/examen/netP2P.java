
package examen;

import java.util.Collection;

/**
 *
 * @author escribe_aquí_tu_nombre
 */
public class netP2P {
    
    
    
    public netP2P(Node n){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Adds a new node to the network. Connections are established 
     * between this node and the rest of the nodes in the network. 
     * With the super node, it only connects as a client.
     * @param n 
     */
    public void newNode(Node n){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * This method receives as parameters the client node and the server node and, if the connection between them exists, closes it.
     * @param client
     * @param server
     * @return true if the connection existed 
     */
    public boolean closeClient(Node client, Node server){
       throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Stops the node's server. Although the node continues to function as a client
     * @param server 
     */
    public void shutDownServer(Node server){    
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
    /**
     * Removes the node from the network
     * @param n 
     */
    public void completeShutDown(Node n){
       
       throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns a collection of nodes acting as client and server.
     * @return 
     */
    public Collection<Node> clientServer(){

        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Returns a collection of nodes act as server
     * @return 
     */
    public Collection<Node> servers(){
        throw new UnsupportedOperationException("Not supported yet.");
    
    }
    
    /**
     * Returns a collection of nodes act as clients
     * @return 
     */
    public Collection<Node> clients(){
       throw new UnsupportedOperationException("Not supported yet.");
    
    }
    
    /**
     * Returns the number of nodes in the net
     * @return 
     */
    public int everyPair(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
