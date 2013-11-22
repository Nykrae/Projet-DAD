package triplet;

import java.io.Serializable;

public class Informations implements Serializable {

    private String fileName;
    private String key;
    private String message;
    
    public Informations(String fileName, String key, String message) {
           
        this.fileName = fileName;
        this.key = key;
        this.message = message;
    }
    
    public String getFileName(){
        
        return fileName;
    }
    
    public String getKey(){
        
        return key;
    }
    
    public String getMessage(){
        
        return message;
    }
    
    @Override
    public String toString(){
        
        return "Nom du fichier : " + fileName + ". Clé de chiffrement : " + key + ". Message : " + message + ".";
    }
}