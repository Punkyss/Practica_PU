package services;

import Interfaces.BasicTest;
import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.EmptyIDException;
import exceptions.HealthCardException;
import exceptions.NotValidCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScheduledVisitAgendaTest implements BasicTest {
    ScheduledVisitAgenda SV;
    ScheduledVisitAgenda SVwrong;


    @BeforeEach
    public void setUp() throws NotValidCodeException, EmptyIDException {

        SV = new ScheduledVisitAgenda(new HealthCardID("BBBBBBBBQR648597807024000012"));
        SVwrong = new ScheduledVisitAgenda(null);

    }

    @Test
    public void HealthCardExceptionTest() {

        Assertions.assertThrows(HealthCardException.class, () -> SVwrong.getHealthCardID());
    }

    @Test
    public void getTest() {
        try {
            Assertions.assertEquals(SV.getHealthCardID().getCIP(), "BBBBBBBBQR648597807024000012");
        } catch (HealthCardException e) {
            e.printStackTrace();
        }
    }
}
