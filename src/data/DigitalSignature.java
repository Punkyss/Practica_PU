package data;

import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

import java.util.Objects;

public class DigitalSignature {
    private final String signatureCode;
    // 2 brand new exceptions (NotValidCodeException for empty codes) (EmptyIDException for not valid ones)
    public DigitalSignature(String signatureCode) throws NotValidCodeException, EmptyIDException {
        if(signatureCode.equals("")) throw new NotValidCodeException("Signature is empty");
        if(signatureCode.equals("0")) throw new EmptyIDException("Signature is not valid");
        this.signatureCode = signatureCode;
    }

    public String getSignatureCode() {
        return signatureCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigitalSignature that = (DigitalSignature) o;
        return Objects.equals(signatureCode, that.signatureCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signatureCode);
    }

    @Override
    public String toString() {
        return "DigitalSignature{" +
                "signatureCode='" + signatureCode + '\'' +
                '}';
    }

}
