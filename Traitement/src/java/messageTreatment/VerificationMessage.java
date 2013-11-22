package messageTreatment;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import triplet.Informations;

@MessageDriven(
    mappedName="jms/messageQueue",
    activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
    }
)

public class VerificationMessage implements MessageListener{
    
    public VerificationMessage() {
    }
    
    @Override
    public void onMessage(Message message) {
     
        if(message instanceof ObjectMessage)  { 
            
            ObjectMessage objMsg = (ObjectMessage) message;
            try {

                Informations information = (Informations) objMsg.getObject();

                System.out.println("Nom du fichier : " + information.getFileName() + ". Clé de chiffrement : " + information.getKey() + ". Message : " + information.getMessage() + ".");

            } catch (JMSException ex) {
                Logger.getLogger(VerificationMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }
}