package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import medicalconsultation.enumeration.FqUnit;
import medicalconsultation.enumeration.dayMoment;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        HealthCardID s = visitAgenda.getHealthCardID();
        if(visitAgenda.getHealthCardID()==null)throw new HealthCardException("The HealthCard id not valid");
        CIP = visitAgenda.getHealthCardID();

        if(HNS.getePrescription(CIP)==null)throw new NotValidePrescriptionException("The ePrescriprion is not valid");
        MP= HNS.getePrescription(CIP);

        // si falla la conexió ja ho fara una classe delegada
        //throw new ConnectException("Not valid");

        //. . .
    }

    public void initPrescriptionEdition() throws AnyCurrentPrescriptionException, NotFinishedTreatmentException{
        if(MP==null)throw new AnyCurrentPrescriptionException("No prescription in running");
        if( new Date(System.currentTimeMillis()).before(MP.getEndDate())) throw new NotFinishedTreatmentException("Current treatment not finalised yet.");
        //System.out.println("Start of Edition");

    }

    public void searchForProducts(String keyWord) throws AnyKeyWordMedicineException, ConnectException{

        if(HNS.getProductsByKW(keyWord).isEmpty()) throw new AnyKeyWordMedicineException("No Products with that keyword");
        productSpec_List=HNS.getProductsByKW(keyWord);

        // si falla la conexió ja ho fara una classe delegada
        //if(false)throw new ConnectException("Not valid");

    }
    public void selectProduct(int option) throws AnyMedicineSearchException, ConnectException{

        if(productSpec_List==null)throw new AnyMedicineSearchException("No medicine searched");
        ps = HNS.getProductSpecific(option);

        // si falla la conexió ja ho fara una classe delegada
        //if(false)throw new ConnectException("Not valid");

    }
    public void enterMedicineGuidelines(String[] instruc) throws AnySelectedMedicineException, IncorrectTakingGuidelinesException{

        if (ps==null)throw new AnySelectedMedicineException("No product selected");
        //cuando el formato de la pauta o la posología son incorrectos, o bien la información es incompleta
        if(instruc.length!=6)throw new IncorrectTakingGuidelinesException("Not valid guideline values");
        MP.addLine(ps.getUPCcode(),instruc);

    }
    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException{
        if(date.before(new Date()))throw new IncorrectEndingDateException("Not valid end date");
        MP.setPrescDate(new Date());
        MP.setEndDate(date);
    }

    public void sendePrescription() throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription{

        if(this.eSignature.getSignatureCode().length==0)throw new eSignatureException("Not valid");
        MP.seteSign(eSignature);


        if(MP.getHcID()==null)throw new NotValidePrescription("The ePrescriprion is not valid");

        if(MP.getEndDate().equals(null) || MP.getPrescDate().equals(null) || MP.getHcID().equals(null) || MP.geteSign().equals(null))
            throw new NotCompletedMedicalPrescription("Not completed medical prescription failure");
        MP=HNS.sendePrescription(MP);


        //if(false)throw new ConnectException("Not valid");

    }



    //No hace falta contemplar la parte correspondiente a los servicios de impresión, a
    //fin de imprimir la hoja de tratamiento. Es por ello que no se pide la
    //implementación del método relacionado, ni de las excepciones relacionadas.
    public void printePresc() throws printingException{
        // Not suposed to make
        throw new NotImplementedException();
    }

 //??? // Other methods, apart from the input events

    public boolean compare(MedicalPrescription mp2){
        return this.MP.getHcID().getCIP()==mp2.getHcID().getCIP() &&
                this.MP.geteSign()==mp2.geteSign() &&
                this.MP.getPrescDate()==mp2.getPrescDate() &&
                this.MP.getEndDate()==mp2.getEndDate() &&
                this.MP.getPrescCode()==mp2.getPrescCode();
    }
    // need to make some tests

    public HealthNationalService getHNS() {
        return HNS;
    }

    public HealthCardID getCIP() {
        return CIP;
    }

    public MedicalPrescription getMP() {
        return MP;
    }

    public List<ProductSpecification> getProductSpec_List() {
        return productSpec_List;
    }

    public ProductSpecification getPs() {
        return ps;
    }

    public DigitalSignature geteSignature() {
        return eSignature;
    }

    public ScheduledVisitAgenda getVisitAgenda() {
        return visitAgenda;
    }
}
