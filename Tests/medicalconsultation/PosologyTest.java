package medicalconsultation;

import static org.junit.jupiter.api.Assertions.*;

import Interfaces.BasicTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import medicalconsultation.enumeration.FqUnit;

class PosologyTest implements BasicTest {
    Posology posTest;
    FqUnit fqUnitTest;

    @Override
    @BeforeEach
    public void setUp(){
        fqUnitTest = FqUnit.MONTH;
        posTest = new Posology(2.0f,3.0f, fqUnitTest);
    }


    @Test
    public void takingGuidelineGettersTest(){
        float z = posTest.getDose();

        assertEquals(2.0f , posTest.getDose());
        assertEquals(posTest.getFreq(), 3.0f);
        assertEquals(posTest.getFreqUnit(), FqUnit.MONTH);
    }


    @Test
    public void getTest(){
        posTest.setDose(100.0f);
        posTest.setFreq(10001.0f);
        posTest.setFreqUnit(FqUnit.HOUR);

        assertEquals(100.0f, posTest.getDose());
        assertEquals(10001.0f, posTest.getFreq());
        assertEquals(FqUnit.HOUR, posTest.getFreqUnit());
    }

}