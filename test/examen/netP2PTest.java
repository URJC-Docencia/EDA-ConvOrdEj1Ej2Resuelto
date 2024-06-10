
package examen;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author mayte
 */
public class netP2PTest {
    
    public netP2PTest() {
    }
    
    //private final Node superN = new Node(InetAddress.getByName("45.22.30.39"),6543,2001,100);
    private netP2P net ;
    private Node superN;
    netP2P inicia() throws UnknownHostException{
        InetAddress ip = InetAddress.getByName("45.22.30.39");
        superN = new Node("super",ip,6543,2001,100);
        net = new netP2P(superN);
        
        
        return net;
    }

    /**
     * Test of newNode method, of class netP2P.
     */
    @Test
    public void testNewNode() throws UnknownHostException{
        System.out.println("newNode");
        net = inicia();
        assertEquals(1,net.everyPair());
        Node n1 = new Node("n1",InetAddress.getByName("11.11.11.11"),6543,2001,2);
        Node n2 = new Node("n2",InetAddress.getByName("22.22.22.22"),6544,2001,4);
        Node n3 = new Node("n3",InetAddress.getByName("33.33.33.33"),6545,2001,1);
        Node n4 = new Node("n4",InetAddress.getByName("44.44.44.44"),6546,2001,1);
        net.newNode(n1);
        assertEquals(2,net.everyPair());
        assertEquals(1,net.clients().size());
        assertEquals(1,net.servers().size());
        net.newNode(n2);
        assertEquals(3,net.everyPair());
        assertEquals(2,net.clients().size());
        assertEquals(3,net.servers().size());
        net.newNode(n3);
        assertEquals(4,net.everyPair());
        assertEquals(3,net.clients().size());
        assertEquals(4,net.servers().size());
        net.newNode(n4);
        assertEquals(5,net.everyPair());
       assertEquals(4,net.clients().size());
        assertEquals(5,net.servers().size());
    }

    /**
     * Test of closeClient method, of class netP2P.
     */
    @Test
    public void testCloseClient() throws UnknownHostException {
        System.out.println("closeClient");
        net = inicia();
        Node n1 = new Node("n1",InetAddress.getByName("11.11.11.11"),6543,2001,2);
        Node n2 = new Node("n2",InetAddress.getByName("22.22.22.22"),6544,2001,4);
        Node n3 = new Node("n3",InetAddress.getByName("33.33.33.33"),6545,2001,1);
        Node n4 = new Node("n4",InetAddress.getByName("44.44.44.44"),6546,2001,1);
        net.newNode(n1);
        net.newNode(n2);
        net.newNode(n3);
        net.newNode(n4);
        
        
        boolean expResult = false;
        boolean result = net.closeClient(n4,n1);
        assertEquals(expResult, result);
        assertTrue(net.closeClient(n4, n2));
        
    }

    /**
     * Test of shutDownServer method, of class netP2P.
     */
    @Test
    public void testShutDownServer() throws UnknownHostException{
        System.out.println("shutDownServer");
        net = inicia();
        Node n1 = new Node("n1",InetAddress.getByName("11.11.11.11"),6543,2001,2);
        Node n2 = new Node("n2",InetAddress.getByName("22.22.22.22"),6544,2001,4);
        Node n3 = new Node("n3",InetAddress.getByName("33.33.33.33"),6545,2001,1);
        Node n4 = new Node("n4",InetAddress.getByName("44.44.44.44"),6546,2001,1);
        net.newNode(n1);
        net.newNode(n2);
        net.newNode(n3);
        net.newNode(n4);
        assertEquals(5,net.everyPair());
        assertEquals(4,net.clients().size());
        assertEquals(5,net.servers().size());
        net.shutDownServer(n2);
        assertEquals(5,net.everyPair());
        assertEquals(4,net.clients().size());
        assertEquals(4,net.servers().size());
    }

    /**
     * Test of completeSutDown method, of class netP2P.
     */
    @Test
    public void testCompleteShutDown() throws UnknownHostException{
        System.out.println("completeSutDown");
        net = inicia();
        Node n1 = new Node("n1",InetAddress.getByName("11.11.11.11"),6543,2001,2);
        Node n2 = new Node("n2",InetAddress.getByName("22.22.22.22"),6544,2001,4);
        Node n3 = new Node("n3",InetAddress.getByName("33.33.33.33"),6545,2001,1);
        Node n4 = new Node("n4",InetAddress.getByName("44.44.44.44"),6546,2001,1);
        net.newNode(n1);
        net.newNode(n2);
        net.newNode(n3);
        net.newNode(n4);
        assertEquals(5,net.everyPair());
        assertEquals(4,net.clients().size());
        assertEquals(5,net.servers().size());
        net.completeShutDown(n2);
        assertEquals(4,net.everyPair());
        assertEquals(3,net.clients().size());
        assertEquals(4,net.servers().size());
    }

    /**
     * Test of clientServer method, of class netP2P.
     */
    @Test
    public void testClientServer() throws UnknownHostException{
        System.out.println("clientServer");
        net = inicia();
        Node n1 = new Node("n1",InetAddress.getByName("11.11.11.11"),6543,2001,2);
        Node n2 = new Node("n2",InetAddress.getByName("22.22.22.22"),6544,2001,4);
        Node n3 = new Node("n3",InetAddress.getByName("33.33.33.33"),6545,2001,1);
        Node n4 = new Node("n4",InetAddress.getByName("44.44.44.44"),6546,2001,1);
        net.newNode(n1);
        net.newNode(n2);
        net.newNode(n3);
        net.newNode(n4);
        
        HashSet<Node> s = new HashSet<>(Arrays.asList(n1,n2,n3,n4));
        Collection<Node> clientServer = net.clientServer();
        assertTrue(s.containsAll(clientServer));
        assertTrue(clientServer.containsAll(s));
    }

    /**
     * Test of servers method, of class netP2P.
     */
    @Test
    public void testServers() throws UnknownHostException{
        System.out.println("servers");
        net = inicia();
        Node n1 = new Node("n1",InetAddress.getByName("11.11.11.11"),6543,2001,2);
        Node n2 = new Node("n2",InetAddress.getByName("22.22.22.22"),6544,2001,4);
        Node n3 = new Node("n3",InetAddress.getByName("33.33.33.33"),6545,2001,1);
        Node n4 = new Node("n4",InetAddress.getByName("44.44.44.44"),6546,2001,1);
        net.newNode(n1);
        net.newNode(n2);
        net.newNode(n3);
        net.newNode(n4);
        
        HashSet<Node> s = new HashSet<>(Arrays.asList(n1,n2,n3,n4,superN));
        Collection<Node> clientServer = net.servers();
        assertTrue(s.containsAll(clientServer));
        assertTrue(clientServer.containsAll(s));
    }

    /**
     * Test of clients method, of class netP2P.
     */
    @Test
    public void testClients() throws UnknownHostException{
        System.out.println("clients");
        net = inicia();
        Node n1 = new Node("n1",InetAddress.getByName("11.11.11.11"),6543,2001,2);
        Node n2 = new Node("n2",InetAddress.getByName("22.22.22.22"),6544,2001,4);
        Node n3 = new Node("n3",InetAddress.getByName("33.33.33.33"),6545,2001,1);
        Node n4 = new Node("n4",InetAddress.getByName("44.44.44.44"),6546,2001,1);
        net.newNode(n1);
        net.newNode(n2);
        net.newNode(n3);
        net.newNode(n4);
        
        HashSet<Node> s = new HashSet<>(Arrays.asList(n1,n2,n3,n4));
        Collection<Node> clientServer = net.clients();
        assertTrue(s.containsAll(clientServer));
        assertTrue(clientServer.containsAll(s));
    }
    
}
