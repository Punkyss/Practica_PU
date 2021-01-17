package medicalconsultation;

import Interfaces.DataExceptionsTest;
import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.*;
import medicalconsultation.enumeration.FqUnit;
import medicalconsultation.enumeration.dayMoment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.HealthNationalService;
import services.ScheduledVisitAgenda;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ConsultationTerminalTest implements DataExceptionsTest {
    ConsultationTerminal CT;
    ConsultationTerminal CTwrong;
    DigitalSignature digitalSignature;
    HealthNationalService HNS;
    HealthNationalService HNSwrong;
    ScheduledVisitAgenda visitAgenda;
    List<ProductSpecification> llistaProdSpec;
    List<ProductSpecification> llistaProdSpecBig;
    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

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
        Assertions.assertTrue(CT.compare(new MedicalPrescription(0,null,null,
                new HealthCardID("BBBBBBBBQR648597807024000012"),null)));

        // si falla la conexió ja ho fara una classe delegada
        //throw new ConnectException("Not valid");

    }


    @Test
    void initPrescriptionEditionTest(){

        Assertions.assertThrows(AnyCurrentPrescriptionException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initPrescriptionEdition();
        });

        Assertions.assertThrows(NotFinishedTreatmentException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.enterTreatmentEndingDate(ft.parse("2222-05-21"));
            CT.initPrescriptionEdition();
        });

    }

    @Test
    void searchForProductsTest() throws NotValidCodeException, EmptyIDException, AnyKeyWordMedicineException, ConnectException,
            HealthCardException, NotValidePrescriptionException {

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
    void selectProductTest() throws NotValidCodeException, EmptyIDException, NotValidePrescriptionException, HealthCardException,
            ConnectException, AnyKeyWordMedicineException, AnyMedicineSearchException {
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
        Assertions.assertSame("000000000002", CT.getPs().getUPCcode().getCode());

    }
    @Test
    void enterMedicineGuidelinesTest() throws AnySelectedMedicineException, IncorrectTakingGuidelinesException,
            NotValidCodeException, EmptyIDException, NotValidePrescriptionException, HealthCardException, ConnectException,
            AnyKeyWordMedicineException, AnyMedicineSearchException {

        Assertions.assertThrows(AnySelectedMedicineException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.enterMedicineGuidelines(new String[]{String.valueOf( dayMoment.AFTERMEALS),"5","a","2","2",String.valueOf(FqUnit.DAY)});
        });
        Assertions.assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.searchForProducts("big");
            CT.selectProduct(1);
            CT.enterMedicineGuidelines(new String[]{String.valueOf( dayMoment.AFTERMEALS),"5","a","2","2"});
        });

        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        CT.searchForProducts("big");
        CT.selectProduct(1);
        CT.enterMedicineGuidelines(new String[]{String.valueOf(dayMoment.AFTERMEALS),"5","a","2","2",String.valueOf(FqUnit.DAY)});

        // si es el producte afegit anteriorment
        Assertions.assertEquals(CT.getMP().getPrescriptionLines().get(CT.getMP().getPrescriptionLines().size()-1).getProduct().getCode(),"000000000002");
        Assertions.assertEquals(CT.getMP().getPrescriptionLines().get(CT.getMP().getPrescriptionLines().size()-1).getInstructions().getInstructions(),"a");
        Assertions.assertEquals(CT.getMP().getPrescriptionLines().get(CT.getMP().getPrescriptionLines().size()-1).getInstructions().getDuration(),5);

        Assertions.assertEquals(CT.getMP().getPrescriptionLines().get(CT.getMP().getPrescriptionLines().size()-1).getInstructions().getPosology().getFreq(),2);
        Assertions.assertEquals(CT.getMP().getPrescriptionLines().get(CT.getMP().getPrescriptionLines().size()-1).getInstructions().getPosology().getDose(),2);
        Assertions.assertEquals(CT.getMP().getPrescriptionLines().get(CT.getMP().getPrescriptionLines().size()-1).getInstructions().getPosology().getFreqUnit(),FqUnit.DAY);


    }

    @Test
    void enterTreatmentEndingDateTest() throws IncorrectEndingDateException, NotValidCodeException, EmptyIDException, NotValidePrescriptionException, HealthCardException, ConnectException, ParseException {
        Assertions.assertThrows(IncorrectEndingDateException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.enterTreatmentEndingDate(ft.parse("1900-05-21"));
        });

        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        CT.enterTreatmentEndingDate(ft.parse("2222-05-21"));
        Assertions.assertEquals(CT.getMP().getEndDate().compareTo(ft.parse("2222-05-21")), 0);
        Assertions.assertEquals(CT.getMP().getPrescDate().compareTo(CT.getNowDate()), 0);
    }

    @Test
    void sendePrescriptionTest() throws eSignatureException, NotValidePrescription, NotCompletedMedicalPrescription, ConnectException, AnyKeyWordMedicineException, HealthCardException, NotValidePrescriptionException, AnyMedicineSearchException, AnySelectedMedicineException, IncorrectTakingGuidelinesException, IncorrectEndingDateException, NotValidCodeException, EmptyIDException, ParseException {
        Assertions.assertThrows(eSignatureException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(null, HNS ,visitAgenda);

            CT.initRevision();
            CT.enterTreatmentEndingDate(ft.parse("2222-05-21"));
            CT.sendePrescription();
        });


        Assertions.assertThrows(NotCompletedMedicalPrescription.class, () -> {

            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS,visitAgenda);

            CT.initRevision();
            CT.searchForProducts("big chiringa");
            CT.selectProduct(2);
            CT.sendePrescription();
        });



        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS,visitAgenda);

        CT.initRevision();
        CT.searchForProducts("big chiringa");
        CT.selectProduct(2);
        CT.enterMedicineGuidelines(new String[]{String.valueOf(dayMoment.AFTERMEALS),"5","a","2","2",String.valueOf(FqUnit.DAY)});
        CT.enterTreatmentEndingDate(ft.parse("2222-05-21"));
        CT.sendePrescription();

        Assertions.assertEquals(CT.getMP().geteSign(), digitalSignature);
        Assertions.assertEquals(CT.getMP().getPrescCode(), 123456789);
    }

    @Override
    @Test
    public void NotValidCodeException() {
        Assertions.assertThrows(NotValidCodeException.class, () -> new HealthCardID("1BBBBBBBQR648597807024000012"));
        Assertions.assertThrows(NotValidCodeException.class, () -> new ProductID("W07024000012"));
    }

    @Override
    @Test
    public void EmptyIDExceptionTest() {
        Assertions.assertThrows(EmptyIDException.class, () -> digitalSignature= new DigitalSignature(new byte[]{}));
        Assertions.assertThrows(EmptyIDException.class, () -> new HealthCardID(null));
        Assertions.assertThrows(EmptyIDException.class, () -> new ProductID(null));
    }
}
