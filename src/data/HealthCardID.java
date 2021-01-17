package data;

import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

/**
 * The personal identifying code in the National Health Service.
 */
final public class HealthCardID {
    private final String CIP;
    // 2 brand new exceptions (NotValidCodeException for empty codes) (EmptyIDException for not valid ones)
    public HealthCardID(String code) throws NotValidCodeException, EmptyIDException {
        if(code==null) throw new EmptyIDException("Code is empty");
        if(!code.matches("\\p{Upper}{8}\\p{Upper}{2}\\d{6}\\d{12}")) throw new NotValidCodeException("Code is not valid");
        this.CIP = code;
    }
    public String getCIP() {
        return CIP;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthCardID hcardID = (HealthCardID) o;
        return CIP.equals(hcardID.CIP);
    }
    @Override
    public int hashCode() { return CIP.hashCode(); }
    @Override
    public String toString() {
        return "HealthCardID{" + "personal code='" + CIP + '\'' + '}';
    }
}
