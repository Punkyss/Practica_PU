package data;

import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

import java.util.Objects;

public class ProductID {
    private final String code;

    public ProductID(String code) throws NotValidCodeException, EmptyIDException {
        if(code.equals("")) throw new NotValidCodeException("Code is empty");
        if(code.equals("0")) throw new EmptyIDException("Code is not valid");
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID productID = (ProductID) o;
        return Objects.equals(code, productID.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "ProductID{" +
                "code='" + code + '\'' +
                '}';
    }
}
