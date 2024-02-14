package com.parking_lot.lot;

import com.parking_lot.customer.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.TreeSet;

public class ParkingLotTest {

    @Test
    public void constructorAndGettersTest() {
        ParkingLot parkingLot = new ParkingLot(5);

        int lotSize = 5;
        TreeSet<Integer> availableSlots = new TreeSet<Integer>();
        for(int i=1; i<=lotSize; i++) {
            availableSlots.add(i);
        }
        HashMap<Integer, Car> ticketsIssued = new HashMap<Integer, Car>();

        Assertions.assertEquals(lotSize, parkingLot.getLotSize());
        Assertions.assertEquals(ticketsIssued, parkingLot.getTicketsIssued());
    }

    @Test
    public void testNextAvailableSlotWhenSlotIsAvailable() {
        ParkingLot parkingLot = new ParkingLot(5);
        Assertions.assertEquals(1, parkingLot.getNextAvailableSlot());

        parkingLot.fillSlot(new Car("KA-01-HH-1234", "White"));
        Assertions.assertEquals(2, parkingLot.getNextAvailableSlot());
    }

    @Test
    public void testNextAvailableSlotWhenSlotIsNotAvailable() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.fillSlot(new Car("KA-01-HH-1234", "White"));

        Assertions.assertEquals(0,parkingLot.getNextAvailableSlot());
    }

    @Test
    public void testGetTicketsIssuedWhenNoCarsAreParked() {
        ParkingLot parkingLot = new ParkingLot(5);

        Assertions.assertEquals(0, parkingLot.getTicketsIssued().size());
    }

    @Test
    public void testGetTicketsIssuedWhenCarsAreParked() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car("KA-01-HH-1234", "White");
        HashMap<Integer, Car> ticketsIssued = new HashMap<Integer, Car>();

        parkingLot.fillSlot(car);
        ticketsIssued.put(1, car);

        Assertions.assertEquals(car, parkingLot.getTicketsIssued().get(1));
    }

    @Test
    public void testFillingSlotWhenSlotIsAvailable() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car("KA-01-HH-1234", "White");

        Assertions.assertEquals(1 ,parkingLot.fillSlot(car));
        Assertions.assertEquals(2, parkingLot.getNextAvailableSlot());
        Assertions.assertEquals(car, parkingLot.getTicketsIssued().get(1));
    }

    @Test
    public void testFillingSlotWhenSlotIsNotAvailable() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-KH-1234", "White");
        Assertions.assertEquals(1,parkingLot.fillSlot(car1));
        Assertions.assertEquals(0, parkingLot.getNextAvailableSlot());
        Assertions.assertEquals(car1, parkingLot.getTicketsIssued().get(1));
        Assertions.assertEquals(0,parkingLot.fillSlot(car2));
    }

    @Test
    public void testEmptyingSlotWhenSlotIsUsed() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car("KA-01-HH-1234", "White");
        parkingLot.fillSlot(car);

        Assertions.assertTrue(parkingLot.emptySlot(1));
        Assertions.assertEquals(1, parkingLot.getNextAvailableSlot());
        Assertions.assertEquals(0, parkingLot.getTicketsIssued().size());
    }

    @Test
    public void testEmptyingSlotWhenSlotIsNotUsed() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car("KA-01-HH-1234", "White");
        parkingLot.fillSlot(car);

        Assertions.assertFalse(parkingLot.emptySlot(2));
        Assertions.assertEquals(2, parkingLot.getNextAvailableSlot());
        Assertions.assertEquals(1, parkingLot.getTicketsIssued().size());
    }

    @Test
    public void testNextAvailableSlotWhenSlotIsInTheMiddle() {
        ParkingLot parkingLot = new ParkingLot(5);
        Assertions.assertEquals(1, parkingLot.getNextAvailableSlot());

        parkingLot.fillSlot(new Car("KA-01-HH-1234", "White"));
        parkingLot.fillSlot(new Car("KA-01-JH-1234", "White"));
        parkingLot.fillSlot(new Car("KA-01-MH-1234", "White"));
        parkingLot.emptySlot(2);

        Assertions.assertEquals(2, parkingLot.getNextAvailableSlot());
    }
}
