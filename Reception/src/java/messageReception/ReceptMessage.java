package messageReception;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import triplet.Informations;


    
@WebService(serviceName = "ReceptMessage")
@Stateless()
public class ReceptMessage {

    @Resource(mappedName="jms/queueCF")
    private QueueConnectionFactory factory;

    @Resource(mappedName="jms/messageQueue")
    private Queue queue;

    private Connection cnx;
    
    @PostConstruct
    protected void init(){
        try {
            //connexion au provider JMS établie
                 cnx = factory.createConnection();//connexion au JMS provider établie
        } catch (JMSException ex) {
            Logger.getLogger(ReceptMessage.class.getName()).log(Level.SEVERE, null, ex);
            throw new EJBException();
        }
    }

    //fermeture de la connexion dans une méthode déclenchée lorsque l'instance du session bean est détruite
    @PreDestroy
    protected void clear(){
        try {
            cnx.close(); //il faut fermer la connexion
        } catch (JMSException ex) {
            Logger.getLogger(ReceptMessage.class.getName()).log(Level.SEVERE, null, ex);
            throw new EJBException();
        }
    }
    
    @WebMethod(operationName = "TreatmentOperation")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean processTreatment(@WebParam(name = "fileName") String fileName, @WebParam(name = "key") String key, @WebParam(name = "message") String message) {
        
        sendMessage(fileName, key, message);
        return true;
    }

    private void sendMessage(String fileName, String key, String message){
        
        try {

            Session session = cnx.createSession(true, 0);

            //création d'un producteur pour envoi du message vers la destination
            MessageProducer producer = session.createProducer(queue);

            //instanciation de l'entité
            Informations information = new Informations(fileName, key, message);

            //Création d'un message de type objet
            ObjectMessage obj = session.createObjectMessage(information);
            producer.send(obj); //préparation de l'envoi du message

        } catch (JMSException ex) {
            Logger.getLogger(ReceptMessage.class.getName()).log(Level.SEVERE, null, ex);
            throw new EJBException();
        }
    }
}
