package data;

import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

import java.util.Objects;

public class DigitalSignature {
    private final byte[] signatureCode;
    // 2 brand new exceptions (NotValidCodeException for empty codes) (EmptyIDException for not valid ones)
    public DigitalSignature(byte[] signatureCode) throws NotValidCodeException, EmptyIDException {
        for (byte b:signatureCode) {
            if(Byte.valueOf(b)!=null)throw new NotValidCodeException("A character is not a byte");
        }
        if(signatureCode.equals("")) throw new EmptyIDException("Code is not valid");
        this.signatureCode = signatureCode;
    }

    public byte[] getSignatureCode() {
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
