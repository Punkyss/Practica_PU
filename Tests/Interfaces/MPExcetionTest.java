package Interfaces;

import data.ProductID;
import exceptions.*;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface MPExcetionTest {

    @Test
    void productNotInPrescription_Test() throws NotValidePrescriptionException, HealthCardException, ConnectException, NotValidCodeException, EmptyIDException;
    @Test
    void incorrectTakingGuidelinesException_Test() throws NotValidCodeException, EmptyIDException, IncorrectTakingGuidelinesException, NotValidePrescriptionException, HealthCardException, ConnectException;
}
