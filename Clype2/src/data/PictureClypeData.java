package data;

import javafx.scene.image.Image;



/**
 *
 * @author Jason
 */
public class PictureClypeData extends ClypeData{
    private Image picture;
    private String key;
    
    /**
     * Bringing in a picture message.
     * @param userName
     * @param picture
     * @param type 
     */
    public PictureClypeData(String userName, Image picture, int type){
        super(userName, type);
        this.picture = picture;
    }
    
    /**
     * Bringing in an encrypted picture.
     * @param userName
     * @param picture
     * @param key
     * @param type 
     */
    public PictureClypeData(String userName, Image picture, String key, int type){
        super(userName, type);
        this.picture = picture;
        this.key = key;
    }
    
    /**
     * Anonymously sending picture.
     * @param picture 
     */
    public PictureClypeData (Image picture){
        this("Anon", picture, 0);
    }
    
    /**
    * @return 
    * @see java.lang.Object#hashCode()
    */
    @Override
    public int hashCode() {
    	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((picture == null) ? 0 : picture.hashCode());
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
	PictureClypeData other = (PictureClypeData) obj;
	if (picture == null) {
		if (other.picture != null) {
			return false;
		}
	} else if (!picture.equals(other.picture)) {
		return false;
	}
	return true;
    }
    
    /**
     * Accesses the image sent;
     * @return 
     */
    public Image getPicture(){
        return picture;
    }
    
    @Override
    public String getData() {
        String data = picture.toString();
        return data;
    }

    @Override
    public String getData(String key) {
        String data = decrypt(this.picture.toString(), key);
        return data;
    }
    
}
