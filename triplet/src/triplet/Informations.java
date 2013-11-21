package triplet;

import java.io.Serializable;

public class Informations implements Serializable {

    private String fileName;
    private String key;
    private String secretInformation;
    
    public Informations(String fileName, String key, String secretInformation) {
           
        this.fileName = fileName;
        this.key = key;
        this.secretInformation = secretInformation;
    }
    
    public String getFileName(){
        
        return fileName;
    }
    
    public String getKey(){
        
        return key;
    }
    
    public String getSecretInformation(){
        
        return secretInformation;
    }
    
    @Override
    public String toString(){
        
        return "Nom du fichier : " + fileName + ". Clé de chiffrement : " + key + ". Information secrète : " + secretInformation + ".";
    }
}