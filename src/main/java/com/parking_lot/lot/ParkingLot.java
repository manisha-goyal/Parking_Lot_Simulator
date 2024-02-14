package com.parking_lot.lot;

import java.util.HashMap;
import java.util.TreeSet;
import com.parking_lot.customer.Car;

public class ParkingLot {

    private int lotSize = 0;
    private TreeSet<Integer> availableSlots;
    private HashMap<Integer, Car> ticketsIssued;

    public ParkingLot(int lotSize) {
        this.lotSize = lotSize;
        this.availableSlots = new TreeSet<Integer>();
        for(int i=1; i<=lotSize; i++) {
            availableSlots.add(i);
        }
        ticketsIssued = new HashMap<Integer, Car>();
    }

    public int getLotSize() {
        return lotSize;
    }

    public int getNextAvailableSlot() {
        if(!availableSlots.isEmpty())
            return availableSlots.first();
        else
            return 0;
    }

    public HashMap<Integer, Car> getTicketsIssued() {
        return ticketsIssued;
    }

    //update available slots and tickets when a car is parked
    public int fillSlot(Car car) {
        int slot = getNextAvailableSlot();
        if(slot!=0) {
            availableSlots.remove(slot);
            ticketsIssued.put(slot, car);
            return slot;
        }
        return 0;
    }

    //update available slots and tickets when a car leaves the lot
    public boolean emptySlot(int slotNo) {
        if(ticketsIssued.containsKey(slotNo)) {
            availableSlots.add(slotNo);
            ticketsIssued.remove(slotNo);
            return true;
        }
        return false;
    }
}
