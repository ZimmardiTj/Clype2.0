package data;

import java.util.*;
import java.io.Serializable;

/**
 * The class handles data associated with a Clype user and session.
 * @author Jason
 */
public abstract class ClypeData implements Serializable{
    private String userName;
    private final int type;
    private Date date;
    
    /**
     * Brings in username and type while creating the date.
     * @param userName
     * @param type 
     */
    public ClypeData(String userName, int type) {
        this.userName = userName;
        this.type = type;
        this.date = new Date( );
    }
    
    /**
     * Brings in the type while setting the name and calling the other
     * method.
     * @param type 
     */
    public ClypeData(int type) {
        this("Anon", type);
    }
    
    /**
     * Default constructor.
     */
    public ClypeData( ) {
        this(0);
    }
    
    /**
     * Returns type.
     * @return 
     */
    public int getType( ) {
        return type;
    }
    
    /**
     * Returns name.
     * @return 
     */
    public String getUserName( ) {
        /**
         * Returns name.
         */
        return userName;
    }
    
    /**
     * Returns date.
     * @return 
     */
    public Date getDate( ) {
        return date;
    }
    
    /**
     * Can return message or file contents.
     * @return 
     */
    public abstract String getData( );
    
    /**
     * Returns the original data of an encryption.
     * @param key
     * @return 
     */
    public abstract String getData(String key);
    
    /**
     * Brings in a message and encrypts it using a key.
     * @param inputStringToEncrypt
     * @param key
     * @return 
     */
    protected String encrypt(String inputStringToEncrypt, String key) {
        String encrypted = "";
        inputStringToEncrypt = inputStringToEncrypt.toUpperCase();
        for (int cnt = 0, i = 0; cnt < inputStringToEncrypt.length(); cnt++) {
             char m = inputStringToEncrypt.charAt(cnt);
             if (m >= 'A' && m <= 'Z') {
                 encrypted += (char)((m + key.charAt(i) - 2 * 'A') % 26 + 'A');
                 i++;
                 if(i == key.length())
                     i = 0;
             } else{
                 encrypted += m;
             }
        }
        return encrypted;
    }
    
    /**
     * Brings in a message to decrypt using a key.
     * @param inputStringToDecrypt
     * @param key
     * @return 
     */
    protected String decrypt(String inputStringToDecrypt, String key) {
        String decrypted = "";
        inputStringToDecrypt = inputStringToDecrypt.toUpperCase();
        for (int cnt = 0, i = 0; cnt < inputStringToDecrypt.length(); cnt++) {
             char m = inputStringToDecrypt.charAt(cnt);
             if (m >= 'A' && m <= 'Z') {
                 decrypted += (char)((m - key.charAt(i) + 26) % 26 + 'A');
                 i++;
                 if(i == key.length())
                     i = 0;
             } else {
                 decrypted += m;
             }
        }
        return decrypted;
    }
}
