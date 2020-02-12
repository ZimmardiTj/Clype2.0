package main;

import java.io.IOException;
import java.net.*;
import java.util.*;
import data.ClypeData;

/**
 * Handles data that passes through a server.
 * @author Jason
 */
public class ClypeServer {
    private int port;
    private boolean closeConnection;
    private ArrayList<ServerSideClientIO> serverSideClientIOList;
    
   /**
    * Brings in port number, but sets data to null.
    * @param port 
    */
    public ClypeServer(int port) {
        this.port = port;
        this.serverSideClientIOList = new ArrayList<ServerSideClientIO>();
    }
    
    /**
     * Default constructor with 7000 as port number.
     */
    public ClypeServer( ) {
        this(7000);
        this.serverSideClientIOList = new ArrayList<ServerSideClientIO>();
    }
    
    /**
     * Starts communication with client
     */
    public void start( ) {
        try {
            ServerSocket sskt = new ServerSocket(port);
            
            while(closeConnection == false) {
                Socket clientSocket = sskt.accept();
                ServerSideClientIO clientIO = new ServerSideClientIO(this, clientSocket);
                serverSideClientIOList.add(clientIO);
                Thread thread = new Thread(clientIO);
                thread.start();
            }
            
            sskt.close();
        } catch (UnknownHostException uhe) {
            System.err.println("Unknown host.");
        } catch (NoRouteToHostException ne) {
            System.err.println("Cannot find route.");
        } catch (ConnectException ce) {
            System.err.println("Cannot connect to sever.");
        } catch (IOException ioe) {
            System.err.println("IO issue.");
        }
    }
    
    /**
     * Gathers a list of users on the server.
     * @return 
     */
    public String userList() {
        String names = "";
        for(int i = 0; i < serverSideClientIOList.size(); i++) {
            ServerSideClientIO clientIO = serverSideClientIOList.get(i);
            names += clientIO.getDataToReceiveFromClient().getUserName() + ", ";//Character.toString('\n');
        }
        return names;
    }
    
    /**
     * Broadcasts data to all clients
     * @param dataToBroadcastToClients
     */
    public synchronized void broadcast(ClypeData dataToBroadcastToClients) {
        for(int i = 0; i < serverSideClientIOList.size(); i++) {
            ServerSideClientIO clientIO = serverSideClientIOList.get(i);
            clientIO.setDataToSendToClient(dataToBroadcastToClients);
            clientIO.sendData();
        }
    }
    
    /**
     * Removes a single client.
     * @param serverSideClientToRemove
     */
    public synchronized void remove(ServerSideClientIO serverSideClientToRemove) {
        serverSideClientIOList.remove(serverSideClientToRemove);
    }
    
    /**
     * Returns port number.
     * @return 
     */
    public int getPort( ) {
        return port;
    }
    
    /**
     * Sets a hash code.
     * @return 
     */
    @Override
    public int hashCode() {
        int result = 23;
        result = 31 * result + port;
        if(closeConnection == true) {
            result = 31 * result + 1;
        } else {
            result = 31 * result + 0;
        }
        return result;
 }
    
    /**
     * Checks to see if servers are alike.
     * @param other
     * @return 
     */
    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if(!(other instanceof ClypeServer))
            return false;
        ClypeServer otherFile = (ClypeServer)other;
        return (this.port == otherFile.port &&
                this.closeConnection == otherFile.closeConnection);
    }
    
    /**
     * Returns all variables as a string.
     * @return 
     */
    @Override
    public String toString( ) {
        return ("Port: " + port + " Connection is closed: " + closeConnection);
    }
    
    /**
     * Tests the ability to host.
     * @param args 
     */
    public static void main(String[] args) {
        ClypeServer server = new ClypeServer();
        server.start();
    }
}
