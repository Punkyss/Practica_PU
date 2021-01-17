package medicalconsultation;

import Interfaces.DataExceptionsTest;
import Interfaces.MPExceptionsTest;
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
import java.util.List;


public class ConsultationTerminalTest implements DataExceptionsTest, MPExceptionsTest {
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
    void HealthCardException_Test(){
        Assertions.assertThrows(HealthCardException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(null);
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
        });
    }
    @Test
    void NotValidePrescriptionException_Test(){
        Assertions.assertThrows(NotValidePrescriptionException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CTwrong = new ConsultationTerminal(digitalSignature, HNSwrong ,visitAgenda);
            CTwrong.initRevision();
        });
    }
    @Test
    void iniRevisionTest() throws HealthCardException, NotValidePrescriptionException, ConnectException, NotValidCodeException, EmptyIDException {

        HealthCardException_Test();
        NotValidePrescriptionException_Test();

        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        Assertions.assertTrue(CT.compare(new MedicalPrescription(0,null,null,
                new HealthCardID("BBBBBBBBQR648597807024000012"),null)));

    }

    @Test
    void AnyCurrentPrescriptionException_Test(){
        Assertions.assertThrows(AnyCurrentPrescriptionException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initPrescriptionEdition();
        });
    }
    @Test
    void NotFinishedTreatmentException_Test(){
        Assertions.assertThrows(NotFinishedTreatmentException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.enterTreatmentEndingDate(ft.parse("2222-05-21"));
            CT.initPrescriptionEdition();
        });
    }
    @Test
    void initPrescriptionEditionTest(){

        AnyCurrentPrescriptionException_Test();
        NotFinishedTreatmentException_Test();

    }

    @Test
    void AnyKeyWordMedicineException_Test(){
        Assertions.assertThrows(AnyKeyWordMedicineException.class, () -> {
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.searchForProducts("medium");
        });
    }
    @Test
    void searchForProductsTest() throws NotValidCodeException, EmptyIDException, AnyKeyWordMedicineException, ConnectException,
            HealthCardException, NotValidePrescriptionException {

        AnyKeyWordMedicineException_Test();

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
    void AnyMedicineSearchException_Test(){
        Assertions.assertThrows(AnyMedicineSearchException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.selectProduct(1); // 2018
        });
    }
    @Test
    void selectProductTest() throws NotValidCodeException, EmptyIDException, NotValidePrescriptionException, HealthCardException,
            ConnectException, AnyKeyWordMedicineException, AnyMedicineSearchException {

        AnyMedicineSearchException_Test();

        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        CT.searchForProducts("big");
        CT.selectProduct(1); // 2018
        Assertions.assertSame("000000000002", CT.getPs().getUPCcode().getCode());

    }

    @Test
    void AnySelectedMedicineException_Test(){
        Assertions.assertThrows(AnySelectedMedicineException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.enterMedicineGuidelines(new String[]{String.valueOf( dayMoment.AFTERMEALS),"5","a","2","2",String.valueOf(FqUnit.DAY)});
        });
    }
    @Test
    void enterMedicineGuidelinesTest() throws AnySelectedMedicineException, IncorrectTakingGuidelinesException,
            NotValidCodeException, EmptyIDException, NotValidePrescriptionException, HealthCardException, ConnectException,
            AnyKeyWordMedicineException, AnyMedicineSearchException {

        AnySelectedMedicineException_Test();
        incorrectTakingGuidelinesException_Test();

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
    void IncorrectEndingDateException_Test(){
        Assertions.assertThrows(IncorrectEndingDateException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.enterTreatmentEndingDate(ft.parse("1900-05-21"));
        });
    }
    @Test
    void enterTreatmentEndingDateTest() throws IncorrectEndingDateException, NotValidCodeException, EmptyIDException, NotValidePrescriptionException, HealthCardException, ConnectException, ParseException {
        IncorrectEndingDateException_Test();

        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        CT.enterTreatmentEndingDate(ft.parse("2222-05-21"));
        Assertions.assertEquals(CT.getMP().getEndDate().compareTo(ft.parse("2222-05-21")), 0);
        Assertions.assertEquals(CT.getMP().getPrescDate().compareTo(CT.getNowDate()), 0);
    }

    @Test
    void eSignatureException_Test(){
        Assertions.assertThrows(eSignatureException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(null, HNS ,visitAgenda);

            CT.initRevision();
            CT.enterTreatmentEndingDate(ft.parse("2222-05-21"));
            CT.sendePrescription();
        });
    }
    @Test
    void NotCompletedMedicalPrescription_Test(){
        Assertions.assertThrows(NotCompletedMedicalPrescription.class, () -> {

            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS,visitAgenda);

            CT.initRevision();
            CT.searchForProducts("big chiringa");
            CT.selectProduct(2);
            CT.sendePrescription();
        });
    }
    @Test
    void sendePrescriptionTest() throws eSignatureException, NotValidePrescription, NotCompletedMedicalPrescription, ConnectException, AnyKeyWordMedicineException, HealthCardException, NotValidePrescriptionException, AnyMedicineSearchException, AnySelectedMedicineException, IncorrectTakingGuidelinesException, IncorrectEndingDateException, NotValidCodeException, EmptyIDException, ParseException {

        eSignatureException_Test();
        NotCompletedMedicalPrescription_Test();

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

    @Override
    @Test
    public void productNotInPrescription_Test() throws NotValidePrescriptionException, HealthCardException, ConnectException, NotValidCodeException, EmptyIDException {
        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();

        Assertions.assertThrows(ProductNotInPrescription.class,()->CT.getMP().removeLine(new ProductID("987654321951")));

    }
    @Override
    @Test
    public void incorrectTakingGuidelinesException_Test() throws NotValidCodeException, EmptyIDException, IncorrectTakingGuidelinesException, NotValidePrescriptionException, HealthCardException, ConnectException {
        visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
        CT.initRevision();
        Assertions.assertThrows(IncorrectTakingGuidelinesException.class,()->CT.getMP().addLine(new ProductID("123456789951"),new String[]{}));

        Assertions.assertThrows(IncorrectTakingGuidelinesException.class, () -> {
            visitAgenda= new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
            CT = new ConsultationTerminal(digitalSignature, HNS ,visitAgenda);
            CT.initRevision();
            CT.searchForProducts("big");
            CT.selectProduct(1);
            CT.enterMedicineGuidelines(new String[]{String.valueOf( dayMoment.AFTERMEALS),"5","a","2","2"});
        });
    }
}
