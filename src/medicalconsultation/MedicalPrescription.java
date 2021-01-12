package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.ProductNotInPrescription;

import java.net.ConnectException;
import java.util.Date;

/**
 * Package for the classes involved in the use case Suply next dispensing
 */
public class MedicalPrescription {// A class that represents medical prescription
    private int prescCode;
    private Date prescDate;
    private Date endDate;
    private HealthCardID hcID; // the healthcard ID of the patient
    private DigitalSignature eSign; // the eSignature of the doctor

    //??? // Its components, that is, the set of medical prescription lines
    private String[] instruc;



    //Constructor
    public MedicalPrescription () {
        //. . .
    }


    // Makes some inicialization
    public void addLine(ProductID prodID, String[] instruc) throws IncorrectTakingGuidelinesException {
        if(this==null)throw new IncorrectTakingGuidelinesException("Not valid");
        /*. . .*/
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

    // the getters and setters
    public int getPrescCode() {
        return prescCode;
    }

    public Date getPrescDate() {
        return prescDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public HealthCardID getHcID() {
        return hcID;
    }

    public DigitalSignature geteSign() {
        return eSign;
    }

    public void seteSign(DigitalSignature eSign) {
        this.eSign = eSign;
    }

    public String[] getInstruc() {
        return instruc;
    }

}
