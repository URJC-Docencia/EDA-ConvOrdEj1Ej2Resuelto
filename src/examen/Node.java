
package examen;

import java.net.InetAddress;

/**
 *
 * @author Mayte
 */
public class Node {
    private final String name;
    private final InetAddress ip;
    private final int clientPort;
    private final int serverPort;
    private final ConnectionManager server;

    public Node(String name, InetAddress ip, int clientPort, int serverPort, int max) {
        this.name = name;
        this.ip = ip;
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        server = new ConnectionManager(max,ip,serverPort);
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getClientPort() {
        return clientPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public ConnectionManager getServer() {
        return server;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.ip.hashCode();
        hash = 41 * hash + this.clientPort;
        hash = 41 * hash + this.serverPort;
        return hash;
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.clientPort != other.clientPort) {
            return false;
        }
        if (this.serverPort != other.serverPort) {
            return false;
        }
        return this.ip.equals(other.ip);
    }
    
    
    
}
