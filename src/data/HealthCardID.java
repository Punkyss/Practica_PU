package data;

import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

/**
 * The personal identifying code in the National Health Service.
 */
final public class HealthCardID {
    private final String personalID;
    public HealthCardID(String code) throws NotValidCodeException, EmptyIDException {
        if(code.equals("")) throw new NotValidCodeException("Code is empty");
        if(code.equals("0")) throw new EmptyIDException("Code is not valid");
        this.personalID = code;
    }
    public String getPersonalID() {
        return personalID;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthCardID hcardID = (HealthCardID) o;
        return personalID.equals(hcardID.personalID);
    }
    @Override
    public int hashCode() { return personalID.hashCode(); }
    @Override
    public String toString() {
        return "HealthCardID{" + "personal code='" + personalID + '\'' + '}';
    }
}
