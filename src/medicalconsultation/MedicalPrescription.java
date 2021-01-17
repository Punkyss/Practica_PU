package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.ProductNotInPrescription;
import medicalconsultation.enumeration.FqUnit;
import medicalconsultation.enumeration.dayMoment;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

/**
 * Package for the classes involved in the use case Suply next dispensing
 */
public class MedicalPrescription {
    private int prescCode;
    private Date prescDate;
    private Date endDate;
    private HealthCardID hcID; // the healthcard ID of the patient
    private DigitalSignature eSign; // the eSignature of the doctor
    private ArrayList<MedicalPrescriptionLine> prescriptionLines;
    private ArrayList<ProductID> productsPrescripted;
    private ProductID modifify;

    public MedicalPrescription (int prescCode, Date prescDate, Date endDate, HealthCardID hcID, DigitalSignature eSign) {
        prescriptionLines = new ArrayList<>();
        productsPrescripted = new ArrayList<>();
        this.endDate=endDate;
        this.prescCode=prescCode;
        this.prescDate=prescDate;
        this.eSign=eSign;
        this.hcID=hcID;
    } // Makes some inicialization
    public void addLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException {
        if(instruc.length!=6)throw new IncorrectTakingGuidelinesException("Not valid");
        productsPrescripted.add(prodID);
        prescriptionLines.add(new MedicalPrescriptionLine(prodID,new TakingGuideline(dayMoment.valueOf(instruc[0]), Float.parseFloat(instruc[1]), instruc[2], Float.parseFloat(instruc[3]), Float.parseFloat(instruc[4]), FqUnit.valueOf(instruc[5]))));
    }
    public void modifyLine(ProductID prodID, String[] instruc) throws ProductNotInPrescription, IncorrectTakingGuidelinesException,NotValidCodeException, EmptyIDException{
        if(!productsPrescripted.contains(prodID))throw new ProductNotInPrescription("Not valid");
        if(instruc.length!=6)throw new IncorrectTakingGuidelinesException("Not valid");
        for(int i =0;i<=prescriptionLines.size();i+=1){
            modifify=new ProductID(prescriptionLines.get(i).getProduct().getCode());

            if(prodID.equals(modifify)){
                prescriptionLines.get(i).setInstructions(new TakingGuideline(dayMoment.valueOf(instruc[0]), Float.parseFloat(instruc[1]), instruc[2], Float.parseFloat(instruc[3]), Float.parseFloat(instruc[4]), FqUnit.valueOf(instruc[5])));
                break;
            }
        }
    }
    public void removeLine(ProductID prodID) throws ProductNotInPrescription {
        if(!productsPrescripted.contains(prodID))throw new ProductNotInPrescription("Not valid");
        for(int i = 0;i<=prescriptionLines.size();i+=1){
            if(prodID.equals(prescriptionLines.get(i).getProduct())) {
                prescriptionLines.remove(i);
                productsPrescripted.remove(i);
                break;
            }
        }
    }

    // Setters & gettes
    public int getPrescCode() {
        return prescCode;
    }

    public Date getPrescDate() {
        return prescDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public HealthCardID getHcID() {
        return hcID;
    }

    public DigitalSignature geteSign() {
        return eSign;
    }

    public void setPrescCode(int prescCode) {
        this.prescCode = prescCode;
    }

    public void setPrescDate(Date prescDate) {
        this.prescDate = prescDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setHcID(HealthCardID hcID) {
        this.hcID = hcID;
    }

    public void seteSign(DigitalSignature eSign) {
        this.eSign = eSign;
    }

    public ArrayList<MedicalPrescriptionLine> getPrescriptionLines() {
        return prescriptionLines;
    }
}
