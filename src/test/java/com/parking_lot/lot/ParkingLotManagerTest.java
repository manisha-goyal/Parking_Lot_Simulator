package com.parking_lot.lot;

import com.parking_lot.customer.Car;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ParkingLotManagerTest {

    @Test
    public void testGetLotWhenLotIsCreated() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        ParkingLot lot = new ParkingLot(5);
        Assertions.assertEquals(lot.getLotSize(), lotManager.getLot().getLotSize());
    }

    @Test
    public void testGetLotWhenLotIsNotCreated() {
        ParkingLotManager lotManager = new ParkingLotManager();
        Assertions.assertNull(lotManager.getLot());
    }

    @Test
    public void testCreateParkingLotWhenLotDoesNotExist() {
        ParkingLotManager lotManager = new ParkingLotManager();
        Assertions.assertTrue(lotManager.createParkingLot(5));
    }

    @Test
    public void testCreateParkingLotWhenLotExists() {
        ParkingLotManager lotManager = new ParkingLotManager();
        Assertions.assertTrue(lotManager.createParkingLot(5));
        Assertions.assertFalse(lotManager.createParkingLot(5));
    }

    @Test
    public void testCarParkingWhenLotHasSpace() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car = new Car("KA-01-HH-1234", "White");
        Assertions.assertEquals(1, lotManager.issueTicket(car));
    }

    @Test
    public void testCarParkingWhenLotIsFull() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(1);
        Car car = new Car("KA-01-HH-1234", "White");
        lotManager.issueTicket(car);
        car = new Car("KA-01-MH-1234", "White");
        Assertions.assertEquals(0, lotManager.issueTicket(car));
    }

    @Test
    public void testIsCarInLotWhenCarIsNotInLot() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(2);
        Car car = new Car("KA-01-HH-1234", "White");
        lotManager.issueTicket(car);
        Assertions.assertTrue(lotManager.isCarInLot(car));
    }

    @Test
    public void testIsCarInLotWhenCarIsInLot() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(2);
        Car car = new Car("KA-01-HH-1234", "White");
        Assertions.assertFalse(lotManager.isCarInLot(car));
    }

    @Test
    public void testExitVehicleWhenSlotIsFull() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(2);
        Car car = new Car("KA-01-HH-1234", "White");
        lotManager.issueTicket(car);
        Assertions.assertTrue(lotManager.exitVehicle(1));
    }

    @Test
    public void testExitVehicleWhenSlotIsNotFull() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(2);
        Assertions.assertFalse(lotManager.exitVehicle(1));
    }

    @Test
    public void testShowingStatusWhenCarsAreParked() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "White");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        HashMap<Integer, Car> statusMap = new HashMap<>();
        statusMap.put(1,car1);
        statusMap.put(2,car2);
        statusMap.put(3,car3);
        Assertions.assertEquals(statusMap, lotManager.getStatus());
    }

    @Test
    public void testShowingStatusWhenNoCarsAreParked() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        HashMap<Integer, Car> statusMap = new HashMap<>();
        Assertions.assertEquals(statusMap, lotManager.getStatus());
    }

    @Test
    public void testUpdatingOfStatusWhenCarIsParked() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "White");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);

        HashMap<Integer, Car> statusMap = new HashMap<>();
        statusMap.put(1,car1);
        statusMap.put(2,car2);
        Assertions.assertEquals(statusMap, lotManager.getStatus());

        lotManager.issueTicket(car3);
        statusMap.put(3,car3);
        Assertions.assertEquals(statusMap, lotManager.getStatus());
    }

    @Test
    public void testUpdatingOfStatusWhenCarLeaves() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "White");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);

        HashMap<Integer, Car> statusMap = new HashMap<>();
        statusMap.put(1,car1);
        statusMap.put(2,car2);
        Assertions.assertEquals(statusMap, lotManager.getStatus());

        lotManager.exitVehicle(2);
        statusMap.remove(2);
        Assertions.assertEquals(statusMap, lotManager.getStatus());
    }

    @Test
    public void testGetRegistrationNumbersOfCarsOfGivenColorWhenFound() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "Blue");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        ArrayList<String> result = new ArrayList<>();
        result.add(car1.getRegistrationNo());
        result.add(car3.getRegistrationNo());
        Assertions.assertEquals(result, lotManager.getRegistrationNumbersFromColor("White"));
        Assertions.assertEquals(result, lotManager.getRegistrationNumbersFromColor("white"));
    }

    @Test
    public void testGetRegistrationNumbersOfCarsOfGivenColorWhenNotFound() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "White");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        ArrayList<String> result = new ArrayList<>();
        Assertions.assertEquals(result, lotManager.getRegistrationNumbersFromColor("Blue"));
        Assertions.assertEquals(result, lotManager.getRegistrationNumbersFromColor("red"));
    }

    @Test
    public void testGetRegistrationNumbersOfCarsOfGivenColorWhenColorIsNullOrInvalid() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "White");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        ArrayList<String> result = new ArrayList<>();
        Assertions.assertEquals(result, lotManager.getRegistrationNumbersFromColor(null));
        Assertions.assertEquals(result, lotManager.getRegistrationNumbersFromColor("tomato"));
    }

    @Test
    public void testGetSlotNumberFromRegistrationNumberWhenFound() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "Blue");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        Assertions.assertEquals(2, lotManager.getSlotNumberFromRegistrationNumber("KA-01-MH-1234"));
    }

    @Test
    public void testGetSlotNumberFromRegistrationNumberWhenNotFound() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "Blue");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        Assertions.assertEquals(0, lotManager.getSlotNumberFromRegistrationNumber("KA-01-MH-1434"));
    }

    @Test
    public void testGetSlotNumberFromRegistrationNumberWhenNumberIsNullOrInvalid() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "Blue");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        Assertions.assertEquals(0, lotManager.getSlotNumberFromRegistrationNumber(null));
        Assertions.assertEquals(0, lotManager.getSlotNumberFromRegistrationNumber("111111"));
    }

    @Test
    public void testGetSlotNumbersFromColorWhenFound() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "Blue");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        ArrayList<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(3);
        Assertions.assertEquals(result, lotManager.getSlotNumbersFromColor("White"));
    }

    @Test
    public void testGetSlotNumbersFromColorWhenNotFound() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "Blue");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        ArrayList<Integer> result = new ArrayList<>();
        Assertions.assertEquals(result, lotManager.getSlotNumbersFromColor("Red"));
    }

    @Test
    public void testGetSlotNumbersFromColorWhenColorIsNullOrInvalid() {
        ParkingLotManager lotManager = new ParkingLotManager();
        lotManager.createParkingLot(5);
        Car car1 = new Car("KA-01-HH-1234", "White");
        Car car2 = new Car("KA-01-MH-1234", "Blue");
        Car car3 = new Car("KA-01-LH-1234", "White");
        lotManager.issueTicket(car1);
        lotManager.issueTicket(car2);
        lotManager.issueTicket(car3);
        ArrayList<Integer> result = new ArrayList<>();
        Assertions.assertEquals(result, lotManager.getSlotNumbersFromColor(null));
        Assertions.assertEquals(result, lotManager.getSlotNumbersFromColor("Potato"));
    }
}
