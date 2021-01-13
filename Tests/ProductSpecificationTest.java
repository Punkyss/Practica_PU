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
    String description;
    BigDecimal price;

    @BeforeEach
    void setUp() throws NotValidCodeException, EmptyIDException{
        product = new ProductID("789457624568");
        description = new String("a");
        price=new BigDecimal(1);
    }

    @Test
    void ProductConstructorTest() {
        productEsp = new ProductSpecification(product,description,price);
        assertEquals(product,productEsp.getUPCcode());
        assertEquals(description,productEsp.getDescription());
        assertEquals(price,productEsp.getPrice());

    }
}
