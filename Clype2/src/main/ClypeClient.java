
package main;
import data.*;
import java.io.*;
import java.net.*;
import javafx.application.Platform;



//THINGS TO DO: Start Method needs completion, Check recieveData, sendData.

/**
 * <h1>Clype Instant Message Client </h1>
 * 
 * This class represents the client user.
 * It allows the users to connect to a single host and communicate.
 * 
 * 
 * @author Thomas Zimmardi
 * 
 */
public class ClypeClient implements Runnable{

        private ClypeClientGUI cGUI;
	protected String userName;
	protected String hostName;
	public int port;
	boolean closeConnection;
	ClypeData dataToSendToServer;
	ClypeData dataToRecieveFromServer;
	private static final int DEFAULT_PORT = 7000;
	public ClientSideServerListener listener;
	ObjectInputStream inFromServer;
	ObjectOutputStream outToServer;
	String message;
	Socket skt;
	Object lock = new Object();


	/**
	 * This Constructor is used to generate a new Clype Client instance
	 * @param userName The username being passed
	 * @param hostName The host name, in which is used to connect other clients
	 * @param port The port number, needed to connect other clients
     * @param cGUI
	 */


	/*public static void main(String[] args) {
		
	String input = JOptionPain;
        Scanner kbd = new Scanner(input);
        kbd.useDelimiter("[@:]");
        ClypeClient client = new ClypeClient(kbd.next(), kbd.next(), kbd.nextInt());
        ClypeData joining = new MessageClypeData(client.getUserName(),"I have joined the room", 3);
        client.setDataToSendToServer(joining);
        client.start();
//		Scanner scan;
//		String userName;
//		String hostName;
//		int port;
//		String temp;
//		if(args.length == 0) {
//			ClypeClient client1 = new ClypeClient();
//			client1.start();	
//		}
//		else if(args.length == 1) {
//			scan = new Scanner(args[0]);
//			scan.useDelimiter("@");
//
//			if(args[0].contains("@")) {
//				userName = scan.next();
//				temp = scan.next();
//
//				if(temp.contains(":")) {
//					String[] str = temp.split(":");
//					hostName = str[0];
//					port = Integer.parseInt(str[1]);
//					ClypeClient client3 = new ClypeClient(userName,hostName,port);
//					client3.start();
//				}
//				else {
//					hostName = temp;
//					ClypeClient client4 = new ClypeClient(userName,hostName);
//					client4.start();
//				}
//			}
//			else {
//				userName = args[0];
//				ClypeClient client2 = new ClypeClient(userName);
//				client2.start();
//			}
//		}
	}*/

	public ClypeClient(String userName, String hostName, int port, ClypeClientGUI cGUI){
		if(userName == null) {
			throw new IllegalArgumentException("Username cannot be empty");
		}
		if(hostName == null) {
			throw new IllegalArgumentException("hostName cannot be empty");
		}
		if(port <= 1023) {
			throw new IllegalArgumentException("Port must be greater than 1023");
		}
		this.userName = userName;
		this.hostName = hostName;
		this.port = port;
		  this.port = port;
	        this.closeConnection = false;
	        this.dataToSendToServer = null;
	        this.dataToRecieveFromServer = null;
	        this.inFromServer = null;
	        this.outToServer = null;
                this.cGUI = cGUI;
	}
	/**
	}

	/**
	 * 
	 * @param userName The username being passed
	 * @param hostName The host name, in which is used to connect other clients
         * @param cGUI
	 */
	public ClypeClient(String userName, String hostName, ClypeClientGUI cGUI){
		this(userName,hostName,DEFAULT_PORT, cGUI);//Calling other constructor, passing default port
		if(userName == null) {
			throw new IllegalArgumentException("Username cannot be empty");
		}
		if(hostName == null) {
			throw new IllegalArgumentException("hostName cannot be empty");
		}

	}

	/**
	 * 
	 * @param userName The username being passed
         * @param cGUI
	 */
	public ClypeClient(String userName, ClypeClientGUI cGUI){
		this(userName,"localhost", cGUI);//Default hostname and port number
		if(userName == null) {
			throw new IllegalArgumentException("Username cannot be empty");
		}

	}
	/**
	 * No argument constructor
	 * Passing default values for new instance of ClypeClient
         * @param cGUI
	 */
	public ClypeClient(ClypeClientGUI cGUI){
		this("Anon", cGUI);

	}

        /**
         * 
         * @param closeConnection
         */
        public void setConnection(boolean closeConnection){
            this.closeConnection = closeConnection;
        }
        
	public void sendData(){
		//Need to fix this method
		try {
			if(dataToSendToServer != null)
			outToServer.writeObject(dataToSendToServer);
			//System.out.println("accessing send data in client.." + dataToSendToServer.getData());
		}
		catch(IOException ioe){
			System.out.println("IOException in sendData(): "+ioe.getMessage());
		}
	}
	public boolean recieveData( ) {
        try {
            if(closeConnection == false)
                dataToRecieveFromServer = (ClypeData)inFromServer.readObject();
        } catch (IOException ioe) {
            System.err.println("Problem with IO.");
        } catch (NullPointerException npe) {
            System.err.println("There is no data to get.");
        } catch (ClassNotFoundException cne) {
            System.err.println("ClypeData class not found.");
        }
        
        return closeConnection;
    }
        @Override
	public void run(){
		listener = new ClientSideServerListener(this);
		
		try {
			skt = new Socket(hostName, port);
			outToServer = new ObjectOutputStream(skt.getOutputStream());
			inFromServer = new ObjectInputStream(skt.getInputStream());
			Thread t = new Thread(listener);
			
			t.start();//This?
			sendData();
			while(!closeConnection) {
				//this.readClientData();
                            //this.sendData();
			}
			t.join();
                        System.out.println("Quitting");
			outToServer.close();
			inFromServer.close();
			skt.close();
		}
		catch(UnknownHostException uhe) {
			System.out.println("IOException: "+uhe.getMessage());
		}catch(NoRouteToHostException rthe) {
			System.out.println("No Route To Host: "+rthe.getMessage());
		}catch(ConnectException ce) {
			System.out.println("Connection Refused."+ce.getMessage());
		}catch(IOException ioe) {
			System.out.println("IOException: "+ioe.getMessage());
		} catch (InterruptedException e) {
			System.out.println("Interrupted Exception: "+e.getMessage());
		}
		
		
		
	}
	public synchronized void printData(){
	    try {
            if(dataToRecieveFromServer.getType() == 1){
                Platform.runLater(() -> {
                    cGUI.userList.setText(dataToRecieveFromServer.getData());
                });
            if(dataToRecieveFromServer.getType() == 4){
                Platform.runLater(() -> {
                   cGUI.recentPicture.setImage(((PictureClypeData)dataToRecieveFromServer).getPicture());
                });
            }
            } else if(dataToRecieveFromServer != null && !(dataToRecieveFromServer.getUserName().equals(this.userName))){
                Platform.runLater(() -> {
                    cGUI.otherUser.setText(dataToRecieveFromServer.getUserName() + ": " + dataToRecieveFromServer.getData());
                });
            }
        }catch(NullPointerException npe) {
            System.err.println("Cannot display data.");
        }		
		
//		if(this.dataToRecieveFromServer.getType() == 2) {
//			
//			System.out.println(((FileClypeData)this.dataToRecieveFromServer).getData());
//		}
//		if(this.dataToRecieveFromServer.getType() == 3) {
//			
//			System.out.println(((MessageClypeData)this.dataToRecieveFromServer).getData());
//		}
//		if(this.dataToRecieveFromServer.getType() == 0) {
//			System.out.println(((MessageClypeData)this.dataToRecieveFromServer).getData());
//		}
//		if(this.dataToRecieveFromServer.getType() == 1) {
//			System.out.println(((MessageClypeData)this.dataToRecieveFromServer).getData());
//		}

	}
	/*public void readClientData(){

		String input = inFromStd.next();

		if(input.equalsIgnoreCase("done")){
			System.out.println("Logging out...");
			//synchronized(lock) {
			this.closeConnection = true;
			//}
			dataToSendToServer = new MessageClypeData(this.userName, input, 0);
		}
		else if(input.equalsIgnoreCase("sendfile")) {
			//String arguString = input;
			inFromStd.reset();
			String fileName = inFromStd.next();

			//initialize fileClypeData object
			dataToSendToServer = new FileClypeData(userName,fileName, 2);
			((FileClypeData)dataToSendToServer).readFileContents();
			//System.out.println(dataToSendToServer.getData());
		}
		else if(input.equalsIgnoreCase("LISTUSERS")) {
			System.out.println("Listing users in session...");
			dataToSendToServer = new MessageClypeData(this.userName, input, 1);
			
		}
		else {
			String message = input;
			//System.out.println("before scanner reset.. message: "+message);
			inFromStd.reset();
			message += inFromStd.nextLine();
			//System.out.println("Message is:" +message);
			dataToSendToServer = new MessageClypeData(userName, message, "KEY", 3);

		}
	}*/

	/**
	 * Used to return user name when called
	 * @return This method returns the user name being used in instance
	 */

	
	public String getUserName(){
		return userName;
	}
	public ClypeData getData() {
		return dataToRecieveFromServer;
	}
	/**
	 * Used to return host name when called
	 * @return This method returns the host name being used in instance
	 */
	public String getHostName(){
		return hostName;
	}
	/**
	 * Used to return port number when called
	 * @return This method returns the port number being used in instance
	 */
	public int getPort(){
		return port;
	}
	
	public Object getLock() {
		return lock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataToRecieveFromServer == null) ? 0 : dataToRecieveFromServer.hashCode());
		result = prime * result + ((dataToSendToServer == null) ? 0 : dataToSendToServer.hashCode());
		result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + port;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	/**
	 * This method is used to create a descriptive output of all instance variables
	 * @return This returns a multi-line detailed output
	 */
	@Override
	public String toString(){
		return 
				"The username is: "+this.userName+"\n"+
				"The hostname is: "+this.hostName+"\n"+
				"The port number is: "+this.port+"\n";
	}

	public void setDataToSendToServer(ClypeData dataSent) {
		
		dataToSendToServer = dataSent;
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
		ClypeClient other = (ClypeClient) obj;
		if (dataToRecieveFromServer == null) {
			if (other.dataToRecieveFromServer != null) {
				return false;
			}
		} else if (!dataToRecieveFromServer.equals(other.dataToRecieveFromServer)) {
			return false;
		}
		if (dataToSendToServer == null) {
			if (other.dataToSendToServer != null) {
				return false;
			}
		} else if (!dataToSendToServer.equals(other.dataToSendToServer)) {
			return false;
		}
		if (hostName == null) {
			if (other.hostName != null) {
				return false;
			}
		} else if (!hostName.equals(other.hostName)) {
			return false;
		}
		if (port != other.port) {
			return false;
		}
		if (userName == null) {
			if (other.userName != null) {
				return false;
			}
		} else if (!userName.equals(other.userName)) {
			return false;
		}
		return true;
	}
}

