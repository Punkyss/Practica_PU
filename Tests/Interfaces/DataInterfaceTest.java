package Interfaces;

import exceptions.EmptyIDException;
import exceptions.NotValidCodeException;
import org.junit.jupiter.api.Test;

public interface DataInterfaceTest {

    @Test
    void addIdTest() throws NotValidCodeException, EmptyIDException;

    @Test
    void getIdTest();

}
