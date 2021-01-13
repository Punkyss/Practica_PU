import data.HealthCardID;
import exceptions.*;
import medicalconsultation.*;
import medicalconsultation.enumeration.FqUnit;
import medicalconsultation.enumeration.dayMoment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.HealthNationalService;

import java.net.ConnectException;
import java.util.List;

import static medicalconsultation.enumeration.dayMoment.BEFOREBREAKFAST;
import static medicalconsultation.enumeration.dayMoment.DURINGBREAKFAST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsultationTerminalTest {
    ConsultationTerminal CT;

    @BeforeEach
    void setUp(){

        CT = new ConsultationTerminal();
        CT.setHNS(new HealthNationalService() {
            @Override
            public MedicalPrescription getePrescription(HealthCardID hcID) throws HealthCardException, NotValidePrescriptionException, ConnectException {
                return null;
            }

            @Override
            public List<ProductSpecification> getProductsByKW(String keyWord) throws AnyKeyWordMedicineException, ConnectException {

                return null;
            }

            @Override
            public ProductSpecification getProductSpecific(int opt) throws AnyMedicineSearchException, ConnectException {
                return CT.getProductSpec_List().get(opt);
            }

            @Override
            public MedicalPrescription sendePrescription(MedicalPrescription ePresc) throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription {
                ePresc.setPrescCode(123456789);
                return ePresc;
            }
        });

    }


    @Test
    void takingGuidelineGettersTest(){
        /*
        assertEquals(preTest.getdMoment(), dM);
        assertEquals(preTest.getDuration(), 6);
        assertEquals(preTest.getInstructions(), instructionTest);
        assertEquals(preTest.getPosology().getDose(), posologyTest.getDose());
        assertEquals(preTest.getPosology().getFreq(), posologyTest.getFreq());
        assertEquals(preTest.getPosology().getFreqUnit(), posologyTest.getFreqUnit());*/
    }

    @Test
    void takingGuidelineSettersTest(){
        /*
        preTest.setdMoment(DURINGBREAKFAST);
        preTest.setDuration(8);
        preTest.setInstructions("Prendre una vegada al mes");
        preTest.setPosology(new Posology(4,5,FqUnit.MONTH));

        assertEquals(preTest.getdMoment(),DURINGBREAKFAST);
        assertEquals(preTest.getDuration(), 8);
        assertEquals(preTest.getInstructions(), "Prendre una vegada al mes");
        assertEquals(preTest.getPosology().getDose(), 4);
        assertEquals(preTest.getPosology().getFreq(), 5);
        assertEquals(preTest.getPosology().getFreqUnit(), FqUnit.MONTH);*/
    }
}
