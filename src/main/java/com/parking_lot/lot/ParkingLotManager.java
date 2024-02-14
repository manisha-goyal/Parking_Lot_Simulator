package com.parking_lot.lot;

import java.util.ArrayList;
import java.util.HashMap;

import com.parking_lot.customer.Car;

public class ParkingLotManager {

    private ParkingLot lot;

    public ParkingLotManager() {
    }

    public ParkingLot getLot() {
        return lot;
    }

    public boolean createParkingLot(int lotSize) {
        if(getLot()==null) {
            lot = new ParkingLot(lotSize);
            return true;
        }
        return false;
    }

    public boolean isCarInLot(Car car) {
        HashMap<Integer, Car> ticketsIssued = lot.getTicketsIssued();
        for(Car parkedCar : ticketsIssued.values()) {
            if(parkedCar.getRegistrationNo().equalsIgnoreCase(car.getRegistrationNo()))
                return true;
        }
        return false;
    }

    public int issueTicket(Car car) {
        return lot.fillSlot(car);
    }

    public boolean exitVehicle(int slotNo) {
        return lot.emptySlot(slotNo);
    }

    public ArrayList<String> getRegistrationNumbersFromColor(String color) {
        HashMap<Integer, Car> ticketsIssued = lot.getTicketsIssued();
        ArrayList<String> carsOfColor = new ArrayList<>();
        for(Car parkedCar : ticketsIssued.values()) {
            if(parkedCar.getColor().equalsIgnoreCase(color)) {
                carsOfColor.add(parkedCar.getRegistrationNo());
            }
        }
        return carsOfColor;
    }

    public int getSlotNumberFromRegistrationNumber(String registrationNumber) {
        HashMap<Integer, Car> ticketsIssued = lot.getTicketsIssued();
        for(Integer slot : ticketsIssued.keySet()) {
            Car parkedCar = ticketsIssued.get(slot);
            if(parkedCar.getRegistrationNo().equalsIgnoreCase(registrationNumber)) {
                return slot;
            }
        }
        return 0;
    }

    public ArrayList<Integer> getSlotNumbersFromColor(String color) {
        HashMap<Integer, Car> ticketsIssued = lot.getTicketsIssued();
        ArrayList<Integer> slotsOfColor = new ArrayList<>();
        for(Integer slot : ticketsIssued.keySet()) {
            Car parkedCar = ticketsIssued.get(slot);
            if(parkedCar.getColor().equalsIgnoreCase(color)) {
                slotsOfColor.add(slot);
            }
        }
        return slotsOfColor;
    }

    public HashMap<Integer, Car> getStatus() {
        return lot.getTicketsIssued();
    }
}
