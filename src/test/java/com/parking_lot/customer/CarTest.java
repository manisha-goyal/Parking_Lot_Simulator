package com.parking_lot.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CarTest {

    @Test
    public void constructorAndGettersTest() {
        String registrationNo = "KA-01-HH-1234";
        String color = "White";
        Car car = new Car("KA-01-HH-1234", "White");
        Assertions.assertEquals(registrationNo, car.getRegistrationNo());
        Assertions.assertEquals(color, car.getColor());
    }

}
