/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messageReception;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;


@WebService(serviceName = "ReceptMessage")
@Stateless()
public class ReceptMessage {

    @Resource(mappedName="jms/queueCF")
    private QueueConnectionFactory factory;

    @Resource(mappedName="jms/messageQueue")
    private Queue queue;

    private Connection cnx;
    
    
    
    @WebMethod(operationName = "TreatmentOperation")
    public boolean processTreatment(@WebParam(name = "fileName") String fileName, @WebParam(name = "message") String message) {
        
        if(/*Message déchiffré*/){

            VerificationMessage(fileName, message);
            return true;

        } else {
            return false;
        }
    }
    
    private void VerificationMessage(String fileName, String message){
        
        try {

            Session session = cnx.createSession(true, 0);

            //création d'un producteur pour envoi du message vers la destination
            MessageProducer producer = session.createProducer(queue);

            //instanciation de l'entité
            Payment payment = new Payment(ccNumber, amount);

            //Création d'un message de type objet
            ObjectMessage obj = session.createObjectMessage(payment);
            producer.send(obj); //préparation de l'envoi du message

        } catch (JMSException ex) {
            Logger.getLogger(BankingService.class.getName()).log(Level.SEVERE, null, ex);
            throw new EJBException();
        }

    }
}
