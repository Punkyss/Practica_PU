package services;

import data.HealthCardID;
import exceptions.HealthCardException;

public class ScheduledVisitAgenda {
    HealthCardID card;
    private HealthCardID getHealthCardID() throws HealthCardException {
        // chek if the card given got no PersonalId
        if(card.getPersonalID().equals(""))throw new HealthCardException("Not valid");
        return card;
    }
}
