package medicalconsultation;

import exceptions.*;

import java.net.ConnectException;
import java.util.Date;

public class ConsultationTerminal {
 //???
    public void initRevision() throws HealthCardException, NotValidePrescriptionException, ConnectException {
       if(false)throw new HealthCardException("Not valid");
       if(false)throw new NotValidePrescriptionException("Not valid");
       if(false)throw new ConnectException("Not valid");
        //. . .
    }
    public void initPrescriptionEdition() throws AnyCurrentPrescriptionException, NotFinishedTreatmentException{
       if(false)throw new AnyCurrentPrescriptionException("Not valid");
       if(false)throw new NotFinishedTreatmentException("Not valid");
        //. . .
    }
    public void searchForProducts(String keyWord) throws AnyKeyWordMedicineException, ConnectException{
       if(false)throw new AnyKeyWordMedicineException("Not valid");
       if(false)throw new ConnectException("Not valid");
        //. . .
    }
    public void selectProduct(int option) throws AnyMedicineSearchException, ConnectException{
       if(false)throw new AnyMedicineSearchException("Not valid");
       if(false)throw new ConnectException("Not valid");
        //. . .
    }
    public void enterMedicineGuidelines(String[] instruc) throws AnySelectedMedicineException, IncorrectTakingGuidelinesException{
       if(false)throw new AnySelectedMedicineException("Not valid");
       if(false)throw new IncorrectTakingGuidelinesException("Not valid");
        //. . .
    }
    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException{
       if(false)throw new IncorrectEndingDateException("Not valid");
        //. . .
    }
    public void sendePrescription() throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription{
       if(false)throw new ConnectException("Not valid");
       if(false)throw new NotValidePrescription("Not valid");
       if(false)throw new eSignatureException("Not valid");
       if(false)throw new NotCompletedMedicalPrescription("Not valid");
        //. . .
    }
    /*
    //No hace falta contemplar la parte correspondiente a los servicios de impresión, a
    //fin de imprimir la hoja de tratamiento. Es por ello que no se pide la
    //implementación del método relacionado, ni de las excepciones relacionadas.
    //
    public void printePresc() throws printingException{
        //. . .
    }*/

 //??? // Other methods, apart from the input events
}
