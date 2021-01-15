package services;

import data.HealthCardID;
import exceptions.EmptyIDException;
import exceptions.HealthCardException;
import exceptions.NotValidCodeException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ScheduledVisitAgendaTest {

    @Test
    public void GetHealthCardIDTest() throws NotValidCodeException, EmptyIDException, HealthCardException {
        HealthCardID card;
        card = new HealthCardID("BZCDFGBNQR648597807024000012");
        ScheduledVisitAgenda visita = new ScheduledVisitAgenda(card);
        assertEquals(card, visita.getHealthCardID());
    }

}