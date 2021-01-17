package medicalconsultation;
import Interfaces.BasicTest;
import Interfaces.DataExceptionsTest;
import Interfaces.MPExcetionTest;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MedicalPrescriptionTest implements BasicTest, DataExceptionsTest, MPExcetionTest {
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
    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

    @Test
    @BeforeEach
    public void setUp()throws NotValidCodeException,EmptyIDException{
        prescCode=8;
        try {
            prescDate = ft.parse("1995-05-21");
            endDate = ft.parse("1996-05-28");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endDate = new Date(28/ 5 /1996);
        hcID = new HealthCardID("BBBBBBBBQR648597807024000012");
        eSign = new DigitalSignature(new byte[]{(byte) 0xe0,(byte)  0x4f});
        prescriptionLines = new ArrayList<>();
        medicalPrescription=new MedicalPrescription(prescCode,prescDate,endDate,hcID,eSign);
    }

    @Test
    public void getTest(){
        medicalPrescriptionTest=new MedicalPrescription(prescCode,prescDate,endDate,hcID,eSign);
        assertEquals(medicalPrescriptionTest.getPrescCode(),medicalPrescription.getPrescCode());
        assertEquals(medicalPrescriptionTest.getPrescDate(),medicalPrescription.getPrescDate());
        assertEquals(medicalPrescriptionTest.getEndDate(),medicalPrescription.getEndDate());
        assertEquals(medicalPrescriptionTest.getHcID(),medicalPrescription.getHcID());
        assertEquals(medicalPrescriptionTest.geteSign(),medicalPrescription.geteSign());
    }

    @Test
    public void AddLineTest()throws IncorrectTakingGuidelinesException,NotValidCodeException, EmptyIDException{

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
    public void productNotInPrescription_Test(){
        assertThrows(ProductNotInPrescription.class,()->medicalPrescription.removeLine(new ProductID("987654321951")));
        assertThrows(ProductNotInPrescription.class,()->medicalPrescription.modifyLine(new ProductID("987654321951"),instruc));

    }
    @Test
    public void incorrectTakingGuidelinesException_Test() throws NotValidCodeException, EmptyIDException, IncorrectTakingGuidelinesException {
        assertThrows(IncorrectTakingGuidelinesException.class,()->medicalPrescription.addLine(new ProductID("123456789951"),instrucTest));
        medicalPrescription.addLine(new ProductID("987654321951"),instruc);
        medicalPrescription.addLine(new ProductID("123456789951"),instruc);
        instruc[2]="b";
        assertThrows(IncorrectTakingGuidelinesException.class,()->medicalPrescription.modifyLine(new ProductID("987654321951"),instrucTest));
    }


    @Test
    void removeTest()throws ProductNotInPrescription, NotValidCodeException, EmptyIDException,IncorrectTakingGuidelinesException{
        medicalPrescription.addLine(new ProductID("987654321951"),instruc);
        assertEquals(1,medicalPrescription.getPrescriptionLines().size());
        medicalPrescription.removeLine(new ProductID("987654321951"));
        medicalPrescription.addLine(new ProductID("123456789456"),instruc);
        medicalPrescription.addLine(new ProductID("123456654987"),instruc);
        assertEquals(2,medicalPrescription.getPrescriptionLines().size());
    }

    @Test
    @Override
    public void NotValidCodeException() {
        Assertions.assertThrows(NotValidCodeException.class, () -> hcID= new HealthCardID("1BBBBBBBQR648597807024000012"));
    }

    @Test
    @Override
    public void EmptyIDExceptionTest() {
        Assertions.assertThrows(EmptyIDException.class, () -> eSign= new DigitalSignature(new byte[]{}));
        Assertions.assertThrows(EmptyIDException.class, () -> hcID= new HealthCardID(null));

    }
}
