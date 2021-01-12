package medicalconsultation;

import medicalconsultation.enumeration.FqUnit;

public class Posology { // A class that represents the posology of a medicine
    private float dose;
    private float freq;
    private FqUnit freqUnit;

    public Posology (float d, float f, FqUnit u) {
        this.dose=d;
        this.freq=f;
        this.freqUnit=u;
        //. . .
    } // Initializes attributes

    //??? // the getters and setters
}