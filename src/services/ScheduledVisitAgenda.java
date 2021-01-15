package services;

import data.HealthCardID;
import exceptions.HealthCardException;

import static java.sql.Types.NULL;

public class ScheduledVisitAgenda {
    HealthCardID card;

    public ScheduledVisitAgenda(HealthCardID card){
        this.card = card;
    }

    public HealthCardID getHealthCardID() throws HealthCardException {
        //check if the card given got no PersonalId
        if(card.getCIP().equals(""))throw new HealthCardException("Empty parameter is not valid as CIP");
        return card;
    }
}