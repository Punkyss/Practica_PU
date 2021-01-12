package medicalconsultation;

import medicalconsultation.enumeration.FqUnit;
import medicalconsultation.enumeration.dayMoment;

public class TakingGuideline {
    private dayMoment dMoment;
    private float duration;
    private String instructions;
    private Posology posology;

    public TakingGuideline(dayMoment dM, float du, String i, float d, float f, FqUnit u){
        this.dMoment=dM;
        this.duration=du;
        this.instructions=i;
        this.posology=new Posology(d,f,u);
    } // Initializes attributes

    // the getters and setters
    public void setdMoment(dayMoment dM){
        this.dMoment = dM;
    }

    public dayMoment getdMoment() {
        return dMoment;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Posology getPosology() {
        return posology;
    }

    public void setPosology(Posology posology) {
        this.posology = posology;
    }
}
