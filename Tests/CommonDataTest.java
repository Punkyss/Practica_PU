import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommonDataTest  {
    DigitalSignature digitalSignature;
    HealthCardID healthCardID;
    ProductID productID;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void addIdTestEmpty() throws NotValidCodeException,EmptyIDException {
        digitalSignature= new DigitalSignature(null);
        healthCardID= new HealthCardID(null);
        productID= new ProductID(null);

    }

    @Test
    public void addIdTestNotValid() throws NotValidCodeException,EmptyIDException {
        digitalSignature= new DigitalSignature("0");
        healthCardID= new HealthCardID("0");
        productID= new ProductID("0");

    }



}
