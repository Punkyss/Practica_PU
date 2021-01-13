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
        digitalSignature= new DigitalSignature(new byte[]{(byte) 0xe0,0x4f});
        System.out.println("OK");
        healthCardID= new HealthCardID("BBBBBBBBQR648597807024000012");
        System.out.println("OK");
        productID= new ProductID("807024000012");
        System.out.println("OK");
    }

    @Test
    public void addIdTestNotValid() throws NotValidCodeException,EmptyIDException {
        digitalSignature= new DigitalSignature(new byte[]{});
        healthCardID= new HealthCardID("");
        productID= new ProductID("");

    }



}
