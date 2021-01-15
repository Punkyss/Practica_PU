import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommonDataTest  {
    DigitalSignature digitalSignature;
    HealthCardID healthCardID;
    ProductID productID;

    @BeforeEach
    void setUp() throws NotValidCodeException,EmptyIDException{

        digitalSignature = new DigitalSignature(new byte[]{(byte) 0xe0,(byte)  0x4f});
        healthCardID = new HealthCardID("BBBBBBBBQR648597807024000012");
        productID = new ProductID("807024000012");

    }

    @Test
    public void addIdTestNotValidCodeException() {
        Assertions.assertThrows(NotValidCodeException.class, () -> healthCardID= new HealthCardID("1BBBBBBBQR648597807024000012"));
        Assertions.assertThrows(NotValidCodeException.class, () -> productID= new ProductID("W07024000012"));
    }

    @Test
    public void addIdTestEmptyIDException() {
        Assertions.assertThrows(EmptyIDException.class, () -> digitalSignature= new DigitalSignature(new byte[]{}));
        Assertions.assertThrows(EmptyIDException.class, () -> healthCardID= new HealthCardID(""));
        Assertions.assertThrows(EmptyIDException.class, () -> productID= new ProductID(""));
    }

    @Test
    public void addIdTestValid() {
        byte[] tempByte = new byte[]{(byte) 0xe0,(byte)  0x4f};

        for (int i=0;i != 2;i++) {
            Assertions.assertEquals(digitalSignature.getSignatureCode()[i], tempByte[i]);
        }
        Assertions.assertEquals(healthCardID.getCIP(), "BBBBBBBBQR648597807024000012");
        Assertions.assertEquals(productID.getCode(), "807024000012");
    }



}
