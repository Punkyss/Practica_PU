package medicalconsultation;
import data.DigitalSignature;
import data.HealthCardID;
import exceptions.IncorrectTakingGuidelinesException;
import exceptions.ProductNotInPrescription;
import medicalconsultation.enumeration.FqUnit;
import medicalconsultation.enumeration.dayMoment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import data.ProductID;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Date;

public class MedicalPrescriptionTest {
    private int prescCode;
    private Date prescDate;
    private Date endDate;
    private HealthCardID hcID;
    private DigitalSignature eSign;
    private ArrayList<MedicalPrescriptionLine> prescriptionLines;
    private MedicalPrescription medicalPrescription;
    private MedicalPrescription medicalPrescriptionTest;
    private dayMoment dMoment;
    private float duration;
    private String instructions;
    private float dose;
    private float freq;
    private FqUnit freqUnit;
    private String[] instruc={"AFTERMEALS","5","a","2","2","DAY"};
    private String[] instrucTest={"a"};
    private String[] inTest;
    @BeforeEach
    void setUp()throws NotValidCodeException,EmptyIDException{
        prescCode=8;
        prescDate = new Date(21/05/1995);
        endDate = new Date(28/05/1996);
        hcID = new HealthCardID("BBBBBBBBQR648597807024000012");
        eSign = new DigitalSignature(new byte[]{(byte) 0xe0,(byte)  0x4f});
        prescriptionLines = new ArrayList<>();
        medicalPrescription=new MedicalPrescription(prescCode,prescDate,endDate,hcID,eSign);
    }

    @Test
    void medicalPrescriptionConstructorTest(){
        medicalPrescriptionTest=new MedicalPrescription(prescCode,prescDate,endDate,hcID,eSign);
        assertEquals(medicalPrescriptionTest.getPrescCode(),medicalPrescription.getPrescCode());
        assertEquals(medicalPrescriptionTest.getPrescDate(),medicalPrescription.getPrescDate());
        assertEquals(medicalPrescriptionTest.getEndDate(),medicalPrescription.getEndDate());
        assertEquals(medicalPrescriptionTest.getHcID(),medicalPrescription.getHcID());
        assertEquals(medicalPrescriptionTest.geteSign(),medicalPrescription.geteSign());
    }

    @Test
    void medicalPrescriptionAddLineTest()throws IncorrectTakingGuidelinesException,NotValidCodeException, EmptyIDException{
        medicalPrescription.addLine(new ProductID("123456789951"),instruc);
        medicalPrescription.addLine(new ProductID("987654321123"),instruc);
        dMoment=dayMoment.AFTERMEALS;
       duration=5;
       instructions="a";
       dose=2;
      freq=2;
       freqUnit = FqUnit.DAY;
       medicalPrescription.addLine(new ProductID("123456789951"),instruc);
       medicalPrescription.addLine(new ProductID("987564321123"),instruc);
       assertEquals(dMoment,medicalPrescription.getPrescriptionLines().get(0).getInstructions().getdMoment());
       assertEquals(duration, medicalPrescription.getPrescriptionLines().get(0).getInstructions().getDuration());
       assertEquals(instructions, medicalPrescription.getPrescriptionLines().get(0).getInstructions().getInstructions());
       assertEquals(dose,medicalPrescription.getPrescriptionLines().get(0).getInstructions().getPosology().getDose());
       assertEquals(freq,medicalPrescription.getPrescriptionLines().get(0).getInstructions().getPosology().getFreq());
       assertEquals(freqUnit,medicalPrescription.getPrescriptionLines().get(0).getInstructions().getPosology().getFreqUnit());



    }
    @Test
    void medicalPrescriptionAddLineThrowIncorrectTest()throws IncorrectTakingGuidelinesException,NotValidCodeException, EmptyIDException{
        assertThrows(IncorrectTakingGuidelinesException.class,()->medicalPrescription.addLine(new ProductID("123456789951"),instrucTest));
    }
    @Test
    void medicalPrescriptionModifyLineThrowsNotPrescriptedTest()throws ProductNotInPrescription,IncorrectTakingGuidelinesException,NotValidCodeException, EmptyIDException{
        assertThrows(ProductNotInPrescription.class,()->medicalPrescription.modifyLine(new ProductID("987654321951"),instruc));
    }

    @Test
    void medicalPrescriptionModifyThrowsIncorrectLineTest()throws ProductNotInPrescription,IncorrectTakingGuidelinesException,NotValidCodeException, EmptyIDException{
        medicalPrescription.addLine(new ProductID("987654321951"),instruc);
        medicalPrescription.addLine(new ProductID("123456789951"),instruc);
        instruc[2]="b";
        assertThrows(IncorrectTakingGuidelinesException.class,()->medicalPrescription.modifyLine(new ProductID("987654321951"),instrucTest));
    }
    @Test
    void medicalPrescriptionRemoveTest()throws ProductNotInPrescription, NotValidCodeException, EmptyIDException,IncorrectTakingGuidelinesException{
        medicalPrescription.addLine(new ProductID("987654321951"),instruc);
        assertEquals(1,medicalPrescription.getPrescriptionLines().size());
        medicalPrescription.removeLine(new ProductID("987654321951"));
        medicalPrescription.addLine(new ProductID("123456789456"),instruc);
        medicalPrescription.addLine(new ProductID("123456654987"),instruc);
        assertEquals(2,medicalPrescription.getPrescriptionLines().size());
    }
    @Test
    void medicalPrescriptionRemoveTestThrow()throws ProductNotInPrescription, NotValidCodeException, EmptyIDException,IncorrectTakingGuidelinesException{
        assertThrows(ProductNotInPrescription.class,()->medicalPrescription.removeLine(new ProductID("987654321951")));

    }

//productID 12 numeros
// guideline string 6 =
// /*        dMoment=dayMoment.AFTERMEALS;
//        duration=5;
//        instructions="a";
//        dose=2;
//        freq=2;
//        freqUnit = FqUnit.DAY;
}
