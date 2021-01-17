package medicalconsultation;

import data.ProductID;
public class MedicalPrescriptionLine {
    private ProductID product;
    private TakingGuideline instructions;
//donat que una linia de preescripcio nomes necessita les intruccions i el producte que s'ha de consumir,
// vam decidir que sol era necessari guardar el codi del producte i una instancia de TakingGuidelie amb les instruccions rebudes
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
