
package data;
import java.io.*;
/**
 * 
 * <h1>This class is a sub-class of ClypeData</h1>
 * 
 * This class is used for sending data other than a string message
 * The methods contained in this class are used to transfer files from Client to Server 
 *
 * @author TJ
 */

public class FileClypeData extends ClypeData {
    /**
     * FileName is the name associated with the file data
     */
    private String fileName;
    /**
     * FileContents is the data within the file, being sent/received
     */
    private String fileContents;

    /** 
     *  This constructor is used to generate a new instance of ClypeData Object
     *  instantiated as FileClypeData
     * @param userName The username associated with the user
     * @param fileName The name of the file 
     * @param type The type of user connection
     */
    public FileClypeData(String userName, String fileName, int type){
       super(userName,type);
        this.fileName = fileName;
        fileContents = null;
    }
    
    /**
     * No argument constructor used to generate a default FileClypeData Object
     */
    public FileClypeData(){
    this("Anon","FileName",0);
    }
    
    
    public void readFileContents(){

        String line = null;
        System.out.println("Reading file contents now..");
        try {
            FileReader fileReader = 
                new FileReader(fileName);
            System.out.println(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                 
        }
    }
    
    public void readFileContents(String key) {

                String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
               fileContents =" "+ encrypt(line,key);
               System.out.println(line);
            }    	
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                 
        }
    	
    }
    public void writeFileContents(){

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

           
            // append a newline character.
            bufferedWriter.write(fileContents);
           
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
        }
    }
    public void writeFileContents(String key){
    	
        try {
            FileWriter fileWriter = new FileWriter(fileName);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String decryptedContents = decrypt(fileContents, key);
            bufferedWriter.write(decryptedContents);
           
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
        }
    }
    
    /**
     * This method is used to change the existing file name to the arguments 
     * passed
     * 
     * @param newFileName The new name of the file
     */
    public void setFileName(String newFileName){
    this.fileName = newFileName;
    }
    /**
     * This method is used to return the existing file name of the object
     * it was invoked on
     * @return This will return the current name at the time of invocation
     */
    public String getFileName(){
    return fileName;
    }
    
    @Override
    public String getData() {
    	return fileContents;
    }
    /*
    @Override
    public String getData(String key) {
    	String data = decrypt(this.fileContents, key);
    	return data;
    }
    */
   
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileContents == null) ? 0 : fileContents.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		return result;
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
		FileClypeData other = (FileClypeData) obj;
		if (fileContents == null) {
			if (other.fileContents != null) {
				return false;
			}
		} else if (!fileContents.equals(other.fileContents)) {
			return false;
		}
		if (fileName == null) {
			if (other.fileName != null) {
				return false;
			}
		} else if (!fileName.equals(other.fileName)) {
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
    "The Username is: "+ this.getUserName() + "\n" +
    "The FileName is: "+ this.fileName + "\n" +
    "The Type is: "+ this.getType() + "\n"+
    "The Date is: "+ this.getDate();
    
    }
    @Override
    public String getData(String key) {
    	String data = decrypt(this.fileContents, key);
    	return data;
    }
	
}
