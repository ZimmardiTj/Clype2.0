package data;

/**
 *<h1>This class is a subclass of ClypeData</h1>
 * This class represents the messages sent between users
 * All data must be inherited from ClypeData Object Class
 * @author TJ
 */
public  class MessageClypeData extends ClypeData {

private String message;    
    
/**
 * This constructor creates a new ClypeData Object, instantiated as MessageClypeData
 * @param userName The username to be associated with user
 * @param message The message being sent, or received
 * @param type The type of user connection
 */
public MessageClypeData(String userName, String message, int type){
    /**
     * calling the super constructor, passing the Username 
     * and Type of user connection
     */
    super(userName,type);
    this.message = message;
}

public MessageClypeData(String userName, String message, String key, int type){
    super(userName, type);
    this.message = message;
    
    
   // this.message = super.encrypt(message,key);
  //  System.out.println("New message clype data object being created");
}


public MessageClypeData(){
    /**
     * The default case calling previous MessageClypeData constructor
     */
    this("Anon","default message",0);
}



/**
 * @return 
 * @see java.lang.Object#hashCode()
 */
@Override
public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((message == null) ? 0 : message.hashCode());
	return result;
}

/**
 * @return 
 * @see java.lang.Object#equals(java.lang.Object)
 */
@Override
public boolean equals(Object obj) {
	if (this == obj) {
		return true;
	}
	if (!super.equals(obj)) {
		return false;
	}
	if (getClass() != obj.getClass()) {
		return false;
	}
	MessageClypeData other = (MessageClypeData) obj;
	if (message == null) {
		if (other.message != null) {
			return false;
		}
	} else if (!message.equals(other.message)) {
		return false;
	}
	return true;
}

/**
     * This method is used to create a descriptive output of all instance variables
     * @return This returns a multi-line detailed output
     */
@Override
public String toString(){
return
    "The Username is: "+ super.getUserName() + "\n" +
    "The Message is: "+ this.message + "\n" +
    "The Type is: "+ super.getType() + "\n"+
    "The Date is: "+ super.getDate();
    
}

@Override
public String getData() {
	String data = message;
	//String data = decrypt(this.message, KEY);
	return data;
}

@Override
public String getData(String key) {
	// TODO Auto-generated method stub
	return null;
}



}
