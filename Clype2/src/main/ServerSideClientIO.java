package main;

import data.ClypeData;
import data.MessageClypeData;
import java.io.*;
import java.net.*;

public class ServerSideClientIO implements Runnable{
    boolean closeConnection;
    ClypeData dataToReceiveFromClient;
    ClypeData dataToSendToClient;
    ObjectInputStream inFromClient;
    ObjectOutputStream outToClient;
    ClypeServer server;
    Socket clientSocket;
    
    /**
     * Brings in a server and client socket.
     * @param server
     * @param clientSocket 
     */
    public ServerSideClientIO(ClypeServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        closeConnection = false;
        dataToReceiveFromClient = null;
        dataToSendToClient = null;
        inFromClient = null;
        outToClient = null;
    }
    
    /**
     * Controls the sending and receiving of data.
     */
    @Override
    public void run() {
        try{
            outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromClient = new ObjectInputStream(clientSocket.getInputStream());
            
            while(closeConnection == false) {
                receiveData();
                dataToSendToClient = dataToReceiveFromClient;
                if(!dataToSendToClient.getData().equals("DONE") && 
                        !dataToSendToClient.getData().equals("LISTUSERS"))
                    this.server.broadcast(dataToSendToClient);
            }
        } catch(IOException ioe) {
            System.err.println("Issue with IO");
        }
    }
    
    /**
     * Receives data from client
     */
    public void receiveData() {
        try {
            dataToReceiveFromClient = (ClypeData)inFromClient.readObject();
            
            if(dataToReceiveFromClient.getType() == 0) {
                dataToSendToClient = dataToReceiveFromClient;
                this.sendData();
                this.server.remove(this);
                closeConnection = true;
            }
            
            if(dataToReceiveFromClient.getType() == 1) {
                dataToSendToClient = new MessageClypeData(dataToReceiveFromClient.getUserName(),
                                        server.userList(), 1);
                this.sendData();
            }
        } catch (IOException ioe) {
            System.err.println("Cannot receive.");
        } catch (NullPointerException npe) {
            System.err.println("There is no data to get.");
        } catch (ClassNotFoundException cne) {
            System.err.println("ClypeData class not found.");
        }
    }
    
    /**
     * Sends data to client
     */
    public void sendData() {
        try {
            //System.out.println(dataToSendToClient);
            if(dataToSendToClient != null)
                outToClient.writeObject(dataToSendToClient);
        } catch (IOException ioe) {
            System.err.println("Cannot output to client.");
        } catch (NullPointerException npe) {
            System.err.println("There is no data");
        }
    }
    
    /**
     * Prepares data for sending.
     * @param dataToSendToClient 
     */
    public void setDataToSendToClient(ClypeData dataToSendToClient) {
        this.dataToSendToClient = dataToSendToClient;
    }
    
    /**
     * Returns data that will be sent out.
     * @return 
     */
    public ClypeData getDataToReceiveFromClient(){
        return dataToReceiveFromClient;
    }
}
