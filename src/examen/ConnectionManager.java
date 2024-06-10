
package examen;

import java.net.InetAddress;


/**
 *
 * @author escribe_aquí_tu_nombre
 */
public class ConnectionManager {
    
    
    
    /**
     * Creates a connection manager with a maximum number of conecctions (n)
     * @param n
     * @param ip
     * @param port
     */
    
    public ConnectionManager(int n,InetAddress ip, int port){
        
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
    /***
     * Creates a new connection with the host, if possible 
     * @param ip
     * @param port
     * @return true if the new connection is created
     */
    public boolean newConnection(InetAddress ip, int port){
        
       throw new UnsupportedOperationException("Not supported yet.");
       
        
    }
    
    /***
     * Closes the connection if it exists
     * @param ip
     * @param port 
     * @return  true if the connection exists false otherwise
     */
    public boolean closeConnection (InetAddress ip, int port){
        
      throw new UnsupportedOperationException("Not supported yet.");
      
            
    }
    
    /***
     * Closes all the active connections 
     * @return the number of the closed connetions
     */
    public int closeAllConnection (){
        
       throw new UnsupportedOperationException("Not supported yet.");
         
    }
    
    /***
     * Indicates the number host's actives connections 
     * @return the number of actives connection 
     */
    public int  numberOfConnection(){
       
       throw new UnsupportedOperationException("Not supported yet.");
        
    }   
    
    
 
}
