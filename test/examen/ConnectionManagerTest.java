
package examen;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class ConnectionManagerTest {
    
    public ConnectionManagerTest() {
    }
    private ConnectionManager server;
    
    private void inicia() throws UnknownHostException{
        server = new ConnectionManager(100, InetAddress.getByName("2.4.5.1"),80);
        
        server.newConnection(InetAddress.getByName("192.176.215.2"), 7823);
        server.newConnection(InetAddress.getByName("193.177.216.3"), 7824);
        server.newConnection(InetAddress.getByName("194.178.217.4"), 7825);
        server.newConnection(InetAddress.getByName("195.179.218.5"), 7826);
        server.newConnection(InetAddress.getByName("196.180.219.6"), 7827);
        server.newConnection(InetAddress.getByName("197.181.10.2"), 7828);
        server.newConnection(InetAddress.getByName("198.182.11.3"), 7829);
        server.newConnection(InetAddress.getByName("199.183.12.4"), 7830);
        server.newConnection(InetAddress.getByName("88.184.13.5"), 6712);
        server.newConnection(InetAddress.getByName("89.185.14.6"), 6713);
        server.newConnection(InetAddress.getByName("90.186.15.7"), 6714);
    }

    /**
     * Test of newConnection method, of class ConnectionManager.
     */
    @Test
    public void testNewConnection() throws UnknownHostException {
        System.out.println("newConnection");
        InetAddress ip = InetAddress.getByName("192.176.215.2");
        int port = 7823;
        ConnectionManager instance = new ConnectionManager(1,InetAddress.getByName("2.3.1.4"),80);
       
        boolean result = instance.newConnection(ip, port);
        assertTrue(result);
        InetAddress ip2 = InetAddress.getByName("201.6.125.48");
        int port2 = 9274;
        assertFalse(instance.newConnection(ip2, port2));
    }

    /**
     * Test of closeConnection method, of class ConnectionManager.
     */
    @Test
    public void testCloseConnection() throws UnknownHostException {
        System.out.println("closeConnection");
        inicia();
        
       assertTrue(server.closeConnection(InetAddress.getByName("197.181.10.2"), 7828));
       assertFalse(server.closeConnection(InetAddress.getByName("197.181.10.2"), 7828));
    }

    /**
     * Test of closeAllConnection method, of class ConnectionManager.
     */
    @Test
    public void testCloseAllConnection() throws UnknownHostException {
        System.out.println("closeAllConnection");
        inicia();
        assertEquals(11,server.closeAllConnection());
        assertEquals(0,server.closeAllConnection());
    }

    /**
     * Test of numberOfConnection method, of class ConnectionManager.
     */
    @Test
    public void testNumberOfConnection() throws UnknownHostException {
        System.out.println("numberOfConnection");
        inicia();
        assertEquals(11,server.numberOfConnection());
    }
    
}
