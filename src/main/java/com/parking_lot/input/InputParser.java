package com.parking_lot.input;

import com.parking_lot.customer.Car;
import com.parking_lot.lot.ParkingLotManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class InputParser {

    private ParkingLotManager lotManager;
    private InputCommands commands;

    public InputParser() {
        lotManager = new ParkingLotManager();
        commands = new InputCommands();
    }

    public void execute(String input) {

        String[] inputs = input.split(" ");
        String key = inputs[0];
        switch (key)
        {
            case InputConstants.CREATE_PARKING_LOT:
                //create parking lot
                int lotSize = Integer.parseInt(inputs[1]);

                if(lotManager.getLot()==null) {
                    lotManager.createParkingLot(lotSize);
                    System.out.println("Created a parking lot with " + lotSize + " slots");
                }
                else {
                    System.out.println("Sorry, parking lot is already created");
                }
                break;

            case InputConstants.PARK:
                //park car
                Car car = new Car(inputs[1], inputs[2]);
                if(lotManager.getLot()!=null) {
                    //check if car is already parked
                    if (!lotManager.isCarInLot(car)) {
                        int slot = lotManager.issueTicket(car);
                        //check if lot has space
                        if (slot == 0)
                            System.out.println("Sorry, parking lot is full");
                        else
                            System.out.println("Allocated slot number: " + slot);
                    } else
                        System.out.println("Sorry, car is already parked");
                }
                else {
                     System.out.println("Sorry, parking lot does not exist");
                }
                break;

            case InputConstants.LEAVE:
                //exit car from parking lot
                int slot = Integer.parseInt(inputs[1]);
                if(lotManager.getLot()!=null) {
                    if(slot>lotManager.getLot().getLotSize())
                        System.out.println("Slot number "+slot+" does not exist");
                    else
                        if(lotManager.exitVehicle(slot)) {
                            System.out.println("Slot number "+slot+" is free");
                        }
                        else {
                            System.out.println("Slot number "+slot+" is already free");
                        }
                }
                else {
                    System.out.println("Sorry, parking lot does not exit");
                }
                break;

            case InputConstants.STATUS:
                //show status of parking lot
                if(lotManager.getLot()!=null) {
                    HashMap<Integer, Car> carsParked = lotManager.getStatus();
                    if(!carsParked.isEmpty()) {
                        System.out.printf("%-11s %-18s %s%n", "Slot No.","Registration No","Colour");
                        ArrayList<Integer> slots = new ArrayList<Integer>(carsParked.keySet());
                        Collections.sort(slots);
                        for (Integer usedSlot : slots) {
                            Car parkedCar = carsParked.get(usedSlot);
                            System.out.printf("%-11d %-18s %s%n", usedSlot, parkedCar.getRegistrationNo(), parkedCar.getColor());
                        }
                    }
                    else {
                        System.out.println("Sorry, parking lot is empty");
                    }
                }
                else {
                    System.out.println("Sorry, parking lot does not exist");
                }
                break;

            case InputConstants.REG_NUMBER_FOR_CARS_WITH_COLOR:
                //show registration numbers of cars with given color
                if(lotManager.getLot()!=null) {
                    String colorForRegNos = inputs[1];
                    ArrayList<String> regNos = lotManager.getRegistrationNumbersFromColor(colorForRegNos);
                    if(!regNos.isEmpty()) {
                        Collections.sort(regNos);
                        String regNosOutput = regNos.toString().replace("[", "").replace("]", "");
                        System.out.println(regNosOutput);
                    }
                    else {
                        System.out.println("Not found");
                    }
                }
                else {
                    System.out.println("Sorry, parking lot does not exist");
                }
                break;

            case InputConstants.SLOT_NUMBER_FOR_CAR_WITH_REG_NUMBER:
                //show shot number of car of given registration number
                if(lotManager.getLot()!=null) {
                    String regNo = inputs[1];
                    int slotNo = lotManager.getSlotNumberFromRegistrationNumber(regNo);
                    if(slotNo!=0)
                        System.out.println(slotNo);
                    else
                        System.out.println("Not found");
                }
                else {
                    System.out.println("Sorry, parking lot does not exist");
                }
                break;

            case InputConstants.SLOT_NUMBERS_FOR_CARS_WITH_COLOR:
                //show slot numbers of cars with given color
                if(lotManager.getLot()!=null) {
                    String colorForSlotNos = inputs[1];
                    ArrayList<Integer> slotNos = lotManager.getSlotNumbersFromColor(colorForSlotNos);
                    if(!slotNos.isEmpty()) {
                        Collections.sort(slotNos);
                        String slotNosOutput = slotNos.toString().replace("[", "").replace("]", "");
                        System.out.println(slotNosOutput);
                    }
                    else {
                        System.out.println("Not found");
                    }
                }
                else {
                    System.out.println("Sorry, parking lot does not exist");
                }
                break;

            default:
                break;
        }
    }

    public boolean validateInputString(String input) {
        boolean isValid = true;
        try {
            String[] inputs = input.split(" ");
            String key = inputs[0];

            //validate command
            if(!commands.getCommandsMap().containsKey(key))
                return false;

            int params = commands.getCommandsMap().get(inputs[0]);

            //validate number of parameters if command is correct
            switch (inputs.length) {
                case 1:
                    //for status or leave, params should be 0
                    if(params!=0)
                        return false;
                    break;
                case 2:
                    //for create parking lot etc, params should be 1
                    if(params!=1)
                        return false;
                    try {
                        int value;
                        if(key.equalsIgnoreCase(InputConstants.CREATE_PARKING_LOT) || key.equalsIgnoreCase(InputConstants.LEAVE))
                            value = Integer.parseInt(inputs[1]);
                        else {
                            if(inputs[1]==null || inputs[1].isEmpty())
                                return false;
                            if(!key.equalsIgnoreCase(InputConstants.SLOT_NUMBER_FOR_CAR_WITH_REG_NUMBER))
                                return inputs[1].chars().allMatch(Character::isLetter);
                        }
                    }
                    catch(NumberFormatException e) {
                        return false;
                    }
                    break;
                case 3:
                    //for park, params should be 2
                    if(params!=2)
                        return false;
                    if(inputs[1]==null || inputs[1].isEmpty())
                        return false;
                    if(inputs[2]==null || inputs[2].isEmpty() || !inputs[2].chars().allMatch(Character::isLetter))
                        return false;
                    break;
                default:
                    isValid = false;
            }
        }
        catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
}
