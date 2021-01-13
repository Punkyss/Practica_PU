import data.ProductID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import medicalconsultation.ProductSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;

public class ProductSpecificationTest {
    ProductSpecification productEsp;
    ProductID product;
    String descr;
    BigDecimal price;

    @BeforeEach
    void setUp() throws NotValidCodeException, EmptyIDException{
        product = new ProductID("5");
        descr = new String("a");
        price=new BigDecimal(1);
    }

    @Test
    void ProductConstructorTest() {
        productEsp = new ProductSpecification(product,descr,price);
        assertEquals(product,productEsp.getUPCcode());
        assertEquals(descr,productEsp.getDescription());
        assertEquals(price,productEsp.getPrice());

    }
}
