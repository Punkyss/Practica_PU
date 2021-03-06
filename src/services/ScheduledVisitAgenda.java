package services;

import data.HealthCardID;
import exceptions.HealthCardException;

public class ScheduledVisitAgenda {
    HealthCardID card;

    public ScheduledVisitAgenda(HealthCardID card){
        this.card = card;
    }

    public HealthCardID getHealthCardID() throws HealthCardException {
        // chek if the card given got no PersonalId
        if(card==null)throw new HealthCardException("Not valid card");
        return card;
    }
}
