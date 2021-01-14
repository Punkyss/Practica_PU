package medicalconsultation;

import medicalconsultation.enumeration.FqUnit;

public class Posology { // A class that represents the posology of a medicine
    private float dose;
    private float freq;
    private FqUnit freqUnit;

    // Initializes attributes
    public Posology (float d, float f, FqUnit u) {
        this.dose=d;
        this.freq=f;
        this.freqUnit=u;
        //. . .
    }

    // the getters and setters
    public float getDose() {
        return this.dose;
    }

    public void setDose(float dose) {
        this.dose = dose;
    }

    public float getFreq() {
        return this.freq;
    }

    public void setFreq(float freq) {
        this.freq = freq;
    }

    public FqUnit getFreqUnit() {
        return this.freqUnit;
    }

    public void setFreqUnit(FqUnit freqUnit) {
        this.freqUnit = freqUnit;
    }
}