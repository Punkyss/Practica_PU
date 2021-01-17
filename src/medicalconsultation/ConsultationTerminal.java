package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import exceptions.*;
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
    private Date nowDate = new Date();

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

    }

    public void initPrescriptionEdition() throws AnyCurrentPrescriptionException, NotFinishedTreatmentException{
        if(MP==null)throw new AnyCurrentPrescriptionException("No prescription in running");
        if( new Date(System.currentTimeMillis()).before(MP.getEndDate())) throw new NotFinishedTreatmentException("Current treatment not finalised yet.");

    }

    public void searchForProducts(String keyWord) throws AnyKeyWordMedicineException, ConnectException{

        if(HNS.getProductsByKW(keyWord).isEmpty()) throw new AnyKeyWordMedicineException("No Products with that keyword");
        productSpec_List=HNS.getProductsByKW(keyWord);

    }
    public void selectProduct(int option) throws AnyMedicineSearchException, ConnectException{

        if(productSpec_List==null)throw new AnyMedicineSearchException("No medicine searched");
        ps = HNS.getProductSpecific(option);

    }
    public void enterMedicineGuidelines(String[] instruc) throws AnySelectedMedicineException, IncorrectTakingGuidelinesException{
        if (ps==null)throw new AnySelectedMedicineException("No product selected");

        if(instruc.length!=6)throw new IncorrectTakingGuidelinesException("Not valid guideline values");
        MP.addLine(ps.getUPCcode(),instruc);
    }

    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException{
        if(date.before(nowDate))throw new IncorrectEndingDateException("Not valid end date");
        MP.setPrescDate(nowDate);
        MP.setEndDate(date);
    }

    public void sendePrescription() throws ConnectException, eSignatureException, NotCompletedMedicalPrescription, NotValidePrescription {

        if(this.eSignature==null)throw new eSignatureException("Not valid");
        MP.seteSign(eSignature);

        if(MP.getEndDate()==null || MP.getPrescDate()==(null) || MP.getHcID()==(null) || MP.geteSign()==(null))
            throw new NotCompletedMedicalPrescription("Not completed medical prescription failure");
        MP=HNS.sendePrescription(MP);

    }

    public void printePresc() throws printingException{
        // Not suposed to make
        throw new NotImplementedException();
    }

    // NEED to make some tests

    // Compare
    public boolean compare(MedicalPrescription mp2){
        return this.MP.getHcID().getCIP().equals(mp2.getHcID().getCIP()) &&
                this.MP.geteSign()==mp2.geteSign() &&
                this.MP.getPrescDate()==mp2.getPrescDate() &&
                this.MP.getEndDate()==mp2.getEndDate() &&
                this.MP.getPrescCode()==mp2.getPrescCode();
    }

    // Getters
    public HealthNationalService getHNS() {
        return HNS;
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

    public Date getNowDate() {
        return nowDate;
    }
}
