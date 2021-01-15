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
    private DigitalSignature eSignature;
    private ScheduledVisitAgenda visitAgenda;

    public ConsultationTerminal(DigitalSignature es,HealthNationalService s,ScheduledVisitAgenda va) {
        this.eSignature = es;
        this.HNS=s;
        this.visitAgenda=va;
    }

    public void initRevision() throws HealthCardException, NotValidePrescriptionException, ConnectException {

        if(visitAgenda.getHealthCardID()==null)throw new HealthCardException("The HealthCard id not valid");
        CIP = visitAgenda.getHealthCardID();

        if(HNS.getePrescription(CIP)==null)throw new NotValidePrescriptionException("The ePrescriprion is not valid");
        MP= HNS.getePrescription(CIP);

        // si falla la conexió ja ho fara una classe delegada
        //throw new ConnectException("Not valid");

        //. . .
    }

    public void initPrescriptionEdition() throws AnyCurrentPrescriptionException, NotFinishedTreatmentException{
        if(MP.equals(null))throw new AnyCurrentPrescriptionException("No prescription in running");
        if(new Date().before(MP.getEndDate())) throw new NotFinishedTreatmentException("Current treatment not finalised yet.");
        //System.out.println("Start of Edition");

    }

    public void searchForProducts(String keyWord) throws AnyKeyWordMedicineException, ConnectException{

        if(HNS.getProductsByKW(keyWord).isEmpty()) throw new AnyKeyWordMedicineException("Not valid");
        productSpec_List=HNS.getProductsByKW(keyWord);

        // si falla la conexió ja ho fara una classe delegada
        //if(false)throw new ConnectException("Not valid");

    }
    public void selectProduct(int option) throws AnyMedicineSearchException, ConnectException{

        if(productSpec_List.isEmpty())throw new AnyMedicineSearchException("No medicine searched");
        ps = HNS.getProductSpecific(option);

        // si falla la conexió ja ho fara una classe delegada
        //if(false)throw new ConnectException("Not valid");

    }
    public void enterMedicineGuidelines(String[] instruc) throws AnySelectedMedicineException, IncorrectTakingGuidelinesException{

        if (ps.equals(null))throw new AnySelectedMedicineException("Not valid");

        //cuando el formato de la pauta o la posología son incorrectos, o bien la información es incompleta
        if(instruc.length!=7){
            throw new IncorrectTakingGuidelinesException("Not valid");
        }else {
            TakingGuideline tgl = new TakingGuideline(dayMoment.valueOf(instruc[0]), Float.valueOf(instruc[1]), instruc[2], Float.valueOf(instruc[3]), Float.valueOf(instruc[4]), FqUnit.valueOf(instruc[5]));
            Posology p = tgl.getPosology();
        }
        MP.addLine(ps.getUPCcode(),instruc);

    }
    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException{
        if(date.before(new Date()))throw new IncorrectEndingDateException("Not valid end date");
        MP.setPrescDate(new Date());
        MP.setEndDate(date);
    }

    public void sendePrescription(DigitalSignature eSign) throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription{

        if(eSign.getSignatureCode().length==0)throw new eSignatureException("Not valid");
        MP.seteSign(eSign);


        if(MP.getHcID()==null)throw new NotValidePrescription("The ePrescriprion is not valid");

        if(MP.getEndDate().equals(null) || MP.getPrescDate().equals(null) || MP.getHcID().equals(null) || MP.geteSign().equals(null))throw new NotCompletedMedicalPrescription("Not completed medical prescription failure");
        MP=HNS.sendePrescription(MP);


        //if(false)throw new ConnectException("Not valid");

    }



    //No hace falta contemplar la parte correspondiente a los servicios de impresión, a
    //fin de imprimir la hoja de tratamiento. Es por ello que no se pide la
    //implementación del método relacionado, ni de las excepciones relacionadas.
    public void printePresc() throws printingException{
        // Not suposed to make
    }

 //??? // Other methods, apart from the input events
}
