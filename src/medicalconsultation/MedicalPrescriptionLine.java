package medicalconsultation;

import data.ProductID;
/*private dayMoment dMoment;
private float duration;
private String instructions;
    private float dose;
    private float freq;
    private FqUnit freqUnit;
    dayMoment dMoment,float duration, String instructions, float dose, float freq, FqUnit freqUnit
*/
public class MedicalPrescriptionLine {
    private ProductID product;
    private TakingGuideline instructions;

    public MedicalPrescriptionLine(ProductID product,TakingGuideline line){
        this.product=product;
        this.instructions= line;
    }

    public TakingGuideline getInstructions() {
        return instructions;
    }

    public void setInstructions(TakingGuideline instructions) {
        this.instructions = instructions;
    }

    public ProductID getProduct() {
        return product;
    }

    public void setProduct(ProductID product) {
        this.product = product;
    }
    // throws de cas que no estigui a (fa falta ferla nova)
}
