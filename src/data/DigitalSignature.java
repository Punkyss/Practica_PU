package data;

import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

import java.util.Arrays;
import java.util.Objects;

public class DigitalSignature {
    private final byte[] signatureCode;
    // 2 brand new exceptions (NotValidCodeException for empty codes) (EmptyIDException for not valid ones)

    public DigitalSignature(byte[] signatureCode) throws EmptyIDException {
        if(signatureCode.length==0) throw new EmptyIDException("Signature code is empty");
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
        return Arrays.equals(signatureCode, that.signatureCode);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(signatureCode);
    }

    @Override
    public String toString() {
        return "DigitalSignature{" +
                "signatureCode=" + Arrays.toString(signatureCode) +
                '}';
    }
}
