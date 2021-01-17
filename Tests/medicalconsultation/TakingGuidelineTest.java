package medicalconsultation;

import Interfaces.BasicTest;
import medicalconsultation.enumeration.dayMoment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static medicalconsultation.enumeration.dayMoment.*;
import medicalconsultation.enumeration.FqUnit;
import static org.junit.jupiter.api.Assertions.*;

class TakingGuidelineTest implements BasicTest {
    TakingGuideline preTest;
    dayMoment dM;
    FqUnit fqUnitTest;
    String instructionTest;
    Posology posologyTest;

    @Override
    @BeforeEach
    public void setUp(){
        dM = BEFOREBREAKFAST;
        fqUnitTest = FqUnit.MONTH;
        instructionTest = "Prendre cada dia";
        posologyTest = new Posology(2,3, fqUnitTest);
        preTest = new TakingGuideline(dM, 6, instructionTest, 2, 3, fqUnitTest);
    }

    @Test
    public void takingGuidelineGettersTest(){
        assertEquals(preTest.getdMoment(), dM);
        assertEquals(preTest.getDuration(), 6);
        assertEquals(preTest.getInstructions(), instructionTest);
        assertEquals(preTest.getPosology().getDose(), posologyTest.getDose());
        assertEquals(preTest.getPosology().getFreq(), posologyTest.getFreq());
        assertEquals(preTest.getPosology().getFreqUnit(), posologyTest.getFreqUnit());
    }

    @Test
    @Override
    public void getTest() {
        preTest.setdMoment(DURINGBREAKFAST);
        preTest.setDuration(8);
        preTest.setInstructions("Prendre una vegada al mes");
        preTest.setPosology(new Posology(4,5,FqUnit.MONTH));

        assertEquals(preTest.getdMoment(),DURINGBREAKFAST);
        assertEquals(preTest.getDuration(), 8);
        assertEquals(preTest.getInstructions(), "Prendre una vegada al mes");
        assertEquals(preTest.getPosology().getDose(), 4);
        assertEquals(preTest.getPosology().getFreq(), 5);
        assertEquals(preTest.getPosology().getFreqUnit(), FqUnit.MONTH);
    }

}