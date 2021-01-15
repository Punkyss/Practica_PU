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
        if(card==null)throw new HealthCardException("An empty string is not valid as CIP");
        //if(card.getCIP().equals(NULL))throw new HealthCardException("Not valid NULL as CIP");
        return card;
    }
}