package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ConsultationTerminalTest {
    ConsultationTerminal CT;
    ConsultationTerminal CTwrong;
    DigitalSignature digitalSignature;
    HealthNationalService HNS;
    HealthNationalService HNSwrong;
    ScheduledVisitAgenda visitAgenda;
    List<ProductSpecification> llistaProdSpec;

    @BeforeEach
    void setUp() throws EmptyIDException, NotValidCodeException {
        // ProdListSpecs
        llistaProdSpec= new ArrayList<ProductSpecification>();
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000001"), "big chiringa", BigDecimal.valueOf(100)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000002"), "big tirita", BigDecimal.valueOf(10)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000003"), "big pin", BigDecimal.valueOf(1)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000011"), "little chiringa", BigDecimal.valueOf(100)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000012"), "little tirita", BigDecimal.valueOf(10)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000013"), "little pin", BigDecimal.valueOf(1)));

        // testValues
        digitalSignature = new DigitalSignature(new byte[]{(byte) 0xe0,(byte)  0x4f});
        initHNS();
    }

    private void initHNS(){
        HNS = new HealthNationalService() {
            @Override
            public MedicalPrescription getePrescription(HealthCardID hcID)
                    throws HealthCardException, NotValidePrescriptionException, ConnectException {
                return new MedicalPrescription(0,null,null , hcID,null);
            }

            @Override
            public List<ProductSpecification> getProductsByKW(String keyWord) throws AnyKeyWordMedicineException, ConnectException {
                List<ProductSpecification> listTemp= new ArrayList<ProductSpecification>();
                for (ProductSpecification p: llistaProdSpec) {
                    if(p.getDescription().contains(keyWord)) listTemp.add(p);
                }
                return listTemp;
            }

            @Override
            public ProductSpecification getProductSpecific(int opt) throws AnyMedicineSearchException, ConnectException {
                return llistaProdSpec.get(opt);
            }

            @Override
            public MedicalPrescription sendePrescription(MedicalPrescription ePresc)
                    throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription {
                ePresc.setPrescCode(123456789);
                return ePresc;
            }
        };
        HNSwrong = new HealthNationalService() {
            @Override
            public MedicalPrescription getePrescription(HealthCardID hcID)
                    throws HealthCardException, NotValidePrescriptionException, ConnectException {
                return null;
            }

            @Override
            public List<ProductSpecification> getProductsByKW(String keyWord) throws AnyKeyWordMedicineException, ConnectException {
                List<ProductSpecification> listTemp= new ArrayList<ProductSpecification>();
                for (ProductSpecification p: llistaProdSpec) {
                    if(p.getDescription().contains(keyWord)) listTemp.add(p);
                }
                return listTemp;
            }

            @Override
            public ProductSpecification getProductSpecific(int opt) throws AnyMedicineSearchException, ConnectException {
                return llistaProdSpec.get(opt);
            }

            @Override
            public MedicalPrescription sendePrescription(MedicalPrescription ePresc)
                    throws ConnectException, NotValidePrescription, eSignatureException, NotCompletedMedicalPrescription {
                ePresc.setPrescCode(123456789);
                return ePresc;
            }
        };
    }

    @Test
    void iniRevisionTest() throws HealthCardException, NotValidePrescriptionException, ConnectException, NotValidCodeException, EmptyIDException {

        Assertions.assertThrows(HealthCardException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(null);
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
        });

        Assertions.assertThrows(NotValidePrescriptionException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CTwrong = new ConsultationTerminal(digitalSignature, HNSwrong ,visitAgenda);
            CTwrong.initRevision();
        });

        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        Assertions.assertTrue(CT.compare(new MedicalPrescription(0,null,null,new HealthCardID("BBBBBBBBQR648597807024000012"),null)));
        // si falla la conexió ja ho fara una classe delegada
        //throw new ConnectException("Not valid");

    }

    @Test
    void initPrescriptionEditionTest()throws AnyCurrentPrescriptionException, NotFinishedTreatmentException{

        Assertions.assertThrows(AnyCurrentPrescriptionException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            //CT.initRevision();
            CT.initPrescriptionEdition();
        });

        Assertions.assertThrows(NotFinishedTreatmentException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.enterTreatmentEndingDate(new Date(2022, 3, 5, 0, 0));
            CT.initPrescriptionEdition();
        });

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
