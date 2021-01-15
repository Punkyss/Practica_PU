package medicalconsultation;

import data.ProductID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import medicalconsultation.enumeration.FqUnit;
import medicalconsultation.enumeration.dayMoment;
import data.ProductID;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;


public class MedicalPrecriptionLineTest {
    private dayMoment dMoment;
    private float duration;
    private String instructions;
    private float dose;
    private float freq;
    private FqUnit freqUnit;
    private ProductID product;
    private TakingGuideline lineTest;
    private MedicalPrescriptionLine medicalLineTest;

    @BeforeEach
    void setUp()throws NotValidCodeException, EmptyIDException{
        dMoment=dayMoment.AFTERMEALS;
        duration=5;
        instructions="a";
        dose=2;
        freq=2;
        freqUnit = FqUnit.DAY;
        product=new ProductID("897789456123");
        lineTest= new TakingGuideline(dMoment,duration,instructions,dose,freq,freqUnit);
    }

    @Test
    void MedicalPrescriptionLineTest(){
        medicalLineTest=new MedicalPrescriptionLine(product,lineTest);
        assertEquals(lineTest,medicalLineTest.getInstructions());
        assertEquals(product,medicalLineTest.getProduct());
    }


}
