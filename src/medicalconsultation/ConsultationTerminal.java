package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import medicalconsultation.enumeration.FqUnit;
import medicalconsultation.enumeration.dayMoment;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

public class ConsultationTerminal {
    private HealthNationalService HNS;
    private HealthCardID CIP;
    private MedicalPrescription MP;

    private List<ProductSpecification> productSpec_List;
    private ProductSpecification ps;


    public void setHNS(HealthNationalService s){
        this.HNS=s;
    }

    public void initRevision(ScheduledVisitAgenda visitAgenda) throws HealthCardException, NotValidePrescriptionException, ConnectException {

        if(visitAgenda.getHealthCardID()==null){
            throw new HealthCardException("The HealthCard id not valid");
        }else{
            CIP = visitAgenda.getHealthCardID();
        }

        if(HNS.getePrescription(CIP)==null){
            throw new NotValidePrescriptionException("The ePrescriprion is not valid");
        }else{
            MP= HNS.getePrescription(CIP);
        }
        // si falla la conexió ja ho fara una classe delegada
        //throw new ConnectException("Not valid");

        //. . .
    }

    public void initPrescriptionEdition() throws AnyCurrentPrescriptionException, NotFinishedTreatmentException{

        if(MP.equals(null))throw new AnyCurrentPrescriptionException("No prescription in running");

        if(new Date().before(MP.getEndDate())){
            throw new NotFinishedTreatmentException("Current treatment not finalised yet.");
        }else{
            System.out.println("Start of Edition");
        }


        //. . .
    }

    public void searchForProducts(String keyWord) throws AnyKeyWordMedicineException, ConnectException{

        if(HNS.getProductsByKW(keyWord).isEmpty()) throw new AnyKeyWordMedicineException("Not valid");
        productSpec_List=HNS.getProductsByKW(keyWord);

        // si falla la conexió ja ho fara una classe delegada
        //if(false)throw new ConnectException("Not valid");

        //. . .
    }
    public void selectProduct(int option) throws AnyMedicineSearchException, ConnectException{

        if(productSpec_List.isEmpty())throw new AnyMedicineSearchException("Not valid");
        ps = HNS.getProductSpecific(option);

        // si falla la conexió ja ho fara una classe delegada
        //if(false)throw new ConnectException("Not valid");

        //. . .
    }
    public void enterMedicineGuidelines(String[] instruc) throws AnySelectedMedicineException, IncorrectTakingGuidelinesException{

        if (ps.equals(null))throw new AnySelectedMedicineException("Not valid");

        //cuando el formato de la pauta o la posología son incorrectos, o bien la información es incompleta
        if(instruc.length!=7){
            throw new IncorrectTakingGuidelinesException("Not valid");
        }else {
            TakingGuideline tgl = new TakingGuideline(dayMoment.valueOf(instruc[0]), Float.valueOf(instruc[2]), instruc[3], Float.valueOf(instruc[4]), Float.valueOf(instruc[5]), FqUnit.valueOf(instruc[6]));
            Posology p = tgl.getPosology();
        }
        MP.addLine(ps.getUPCcode(),instruc);


        //. . .
    }
    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException{
        if(date.before(new Date()))throw new IncorrectEndingDateException("Not valid end date");
        MP.setPrescDate(new Date());
        MP.setEndDate(date);
        //. . .
    }
    public void sendePrescription() throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription{

        //if(MP.)
        //MP.seteSign(new DigitalSignature(new Byte[] {Byte.valueOf("yokese d'on treure aixo")}));
        MP=HNS.sendePrescription(MP);

        //if(false)throw new ConnectException("Not valid");
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

    public List<ProductSpecification> getProductSpec_List() {
        return productSpec_List;
    }

    public void setProductSpec_List(List<ProductSpecification> productSpec_List) {
        this.productSpec_List = productSpec_List;
    }
}
