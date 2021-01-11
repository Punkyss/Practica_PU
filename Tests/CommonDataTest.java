import Interfaces.DataInterfaceTest;
import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;
import org.junit.jupiter.api.BeforeEach;

public class CommonDataTest implements DataInterfaceTest {
    DigitalSignature digitalSignature;
    HealthCardID healthCardID;
    ProductID productID;

    @BeforeEach
    void setUp() {

    }

    @Override
    public void addIdTest() throws NotValidCodeException, EmptyIDException {

    }

    @Override
    public void getIdTest() {

    }


}
