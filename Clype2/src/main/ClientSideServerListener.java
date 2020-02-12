package main;
import data.*;

public class ClientSideServerListener implements Runnable{
    private ClypeClient client;
    
    /**
     * Brings in the client
     * @param client 
     */
    public ClientSideServerListener(ClypeClient client) {
        this.client = client;
    }
    
    /**
     * Runs the data reception and printing.
     */
    @Override
    public void run(){
        boolean closeConnection = false;
        int done = 8;
        while(closeConnection != true && done != 0){
                closeConnection = client.recieveData();
                client.printData();
                done = client.getData().getType();
        }
    }
}