package medicalconsultation;

import Interfaces.BasicTest;
import Interfaces.DataExceptionsTest;
import data.ProductID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import medicalconsultation.enumeration.FqUnit;
import medicalconsultation.enumeration.dayMoment;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;


public class MedicalPrecriptionLineTest implements BasicTest, DataExceptionsTest {
    private dayMoment dMoment;
    private float duration;
    private String instructions;
    private float dose;
    private float freq;
    private FqUnit freqUnit;
    private ProductID product;
    private TakingGuideline lineTest;
    private MedicalPrescriptionLine medicalLine;

    @Override
    @BeforeEach
    public void setUp()throws NotValidCodeException, EmptyIDException{
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
    @Override
    public void getTest() {
        medicalLine=new MedicalPrescriptionLine(product,lineTest);
        assertEquals(lineTest,medicalLine.getInstructions());
        assertEquals(product,medicalLine.getProduct());

    }
    @Test
    @Override
    public void NotValidCodeException() {
        Assertions.assertThrows(NotValidCodeException.class, () -> product= new ProductID("W07024000012"));
    }
    @Test
    @Override
    public void EmptyIDExceptionTest() {
        Assertions.assertThrows(EmptyIDException.class, () -> product= new ProductID(null));
    }
}
