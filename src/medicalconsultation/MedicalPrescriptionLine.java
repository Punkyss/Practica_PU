package medicalconsultation;

import data.ProductID;
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
}
