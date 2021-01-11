import Interfaces.DataInterfaceTest;
import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

public class EmptyDataTest implements DataInterfaceTest {
    DigitalSignature digitalSignature;
    HealthCardID healthCardID;
    ProductID productID;

    @Override
    public void addIdTest() throws NotValidCodeException, EmptyIDException {

    }

    @Override
    public void getIdTest() {

    }
}
