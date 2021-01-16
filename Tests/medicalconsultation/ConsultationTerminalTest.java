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
    List<ProductSpecification> llistaProdSpecBig;

    @BeforeEach
    void setUp() throws EmptyIDException, NotValidCodeException {
        // ProdListSpecs
        llistaProdSpec= new ArrayList<>();
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000001"), "big chiringa", BigDecimal.valueOf(100)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000002"), "big tirita", BigDecimal.valueOf(10)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000003"), "big pin", BigDecimal.valueOf(1)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000011"), "little chiringa", BigDecimal.valueOf(100)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000012"), "little tirita", BigDecimal.valueOf(10)));
        llistaProdSpec.add(new ProductSpecification(new ProductID("000000000013"), "little pin", BigDecimal.valueOf(1)));
        llistaProdSpecBig= new ArrayList<>();
        llistaProdSpecBig.add(new ProductSpecification(new ProductID("000000000001"), "big chiringa", BigDecimal.valueOf(100)));
        llistaProdSpecBig.add(new ProductSpecification(new ProductID("000000000002"), "big tirita", BigDecimal.valueOf(10)));
        llistaProdSpecBig.add(new ProductSpecification(new ProductID("000000000003"), "big pin", BigDecimal.valueOf(1)));

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
    void searchForProductsTest() throws NotValidCodeException, EmptyIDException, AnyKeyWordMedicineException, ConnectException, HealthCardException, NotValidePrescriptionException {

        Assertions.assertThrows(AnyKeyWordMedicineException.class, () -> {
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.searchForProducts("medium");
        });

        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        CT.searchForProducts("big");
        for (int i=0;i<CT.getProductSpec_List().size();i++) {
            Assertions.assertEquals(CT.getProductSpec_List().get(i).getDescription(),this.llistaProdSpecBig.get(i).getDescription());
            Assertions.assertEquals(CT.getProductSpec_List().get(i).getPrice(),this.llistaProdSpecBig.get(i).getPrice());
            Assertions.assertEquals(CT.getProductSpec_List().get(i).getUPCcode(),this.llistaProdSpecBig.get(i).getUPCcode());
        }

    }

    @Test
    void enterTreatmentEndingDateTest() throws IncorrectEndingDateException, NotValidCodeException, EmptyIDException, NotValidePrescriptionException, HealthCardException, ConnectException {
        Assertions.assertThrows(IncorrectEndingDateException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.enterTreatmentEndingDate(new Date(18, 11, 24)); // 2018
        });

        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        CT.enterTreatmentEndingDate(new Date(2022, 3, 5, 0, 0));

        Assertions.assertTrue(CT.getMP().getEndDate().compareTo(new Date(2022, 3, 5, 0, 0)) == 0);
        Assertions.assertTrue(CT.getMP().getPrescDate().compareTo(new Date())==0);
    }

    @Test
    void selectProductTest() throws NotValidCodeException, EmptyIDException, NotValidePrescriptionException, HealthCardException, ConnectException, AnyKeyWordMedicineException, AnyMedicineSearchException {
        Assertions.assertThrows(AnyMedicineSearchException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.selectProduct(1); // 2018
        });

        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        CT.searchForProducts("big");
        CT.selectProduct(1); // 2018
        Assertions.assertTrue(CT.getPs().getUPCcode().getCode()=="000000000002");


    }
}
