package Interfaces;

import data.HealthCardID;
import exceptions.EmptyIDException;
import exceptions.HealthCardException;
import exceptions.NotValidCodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ScheduledVisitAgenda;

public interface BasicTest {

    @BeforeEach
    void setUp() throws NotValidCodeException, EmptyIDException;

    @Test
    void getTest() ;
}
