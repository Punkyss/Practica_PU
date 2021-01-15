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
/**
 * Package for the classes involved in the use case Suply next dispensing
 */
public class MedicalPrescription {// A class that represents medical prescription
    private int prescCode;
    private Date prescDate;
    private Date endDate;
    private HealthCardID hcID; // the healthcard ID of the patient
    private DigitalSignature eSign; // the eSignature of the doctor
    private ArrayList<MedicalPrescriptionLine> prescriptionLines;
    //??? // Its components, that is, the set of medical prescription lines

    public MedicalPrescription (int prescCode, Date prescDate, Date endDate, HealthCardID hcID, DigitalSignature eSign) {
        prescriptionLines = new ArrayList<MedicalPrescriptionLine>();
        this.endDate=endDate;
        this.prescCode=prescCode;
        this.prescDate=prescDate;
        this.eSign=eSign;
        this.hcID=hcID;
    } // Makes some inicialization
    public void addLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException {
        if(instruc.length!=6)throw new IncorrectTakingGuidelinesException("Not valid");

        prescriptionLines.add(new MedicalPrescriptionLine(prodID,new TakingGuideline(dayMoment.valueOf(instruc[0]), Float.valueOf(instruc[1]),
                instruc[2], Float.valueOf(instruc[3]), Float.valueOf(instruc[4]), FqUnit.valueOf(instruc[5]))));

    }
    public void modifyLine(ProductID prodID, String[] instruc) throws ProductNotInPrescription, IncorrectTakingGuidelinesException {
        if(false)throw new ProductNotInPrescription("Not valid");
        if(false)throw new IncorrectTakingGuidelinesException("Not valid");
        //. . .
    }
    public void removeLine(ProductID prodID) throws ProductNotInPrescription {
        if(false)throw new ProductNotInPrescription("Not valid");
        //. . .
    }

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
}
