package medicalconsultation;

import Interfaces.BasicTest;
import Interfaces.DataExceptionsTest;
import data.ProductID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

public class ProductSpecificationTest implements BasicTest, DataExceptionsTest {
    ProductSpecification productEsp;
    ProductID product;
    String description;
    BigDecimal price;

    @BeforeEach
    public void setUp() throws NotValidCodeException, EmptyIDException{
        product = new ProductID("789457624568");
        description = "a";
        price=new BigDecimal(1);
    }

    @Test
    public void getTest() {
        productEsp = new ProductSpecification(product,description,price);
        assertEquals(product,productEsp.getUPCcode());
        assertEquals(description,productEsp.getDescription());
        assertEquals(price,productEsp.getPrice());

    }

    @Test
    @Override
    public void NotValidCodeException() {
        Assertions.assertThrows(NotValidCodeException.class, () -> product= new ProductID("W89457624568"));
    }
    @Test
    @Override
    public void EmptyIDExceptionTest() {
        Assertions.assertThrows(EmptyIDException.class, () -> product= new ProductID(null));
    }
}
