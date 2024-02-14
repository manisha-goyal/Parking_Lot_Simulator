package com.parking_lot.input;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InputParserTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testStringValidationWithValidInput() {
        InputParser parser = new InputParser();
        Assertions.assertTrue(parser.validateInputString("create_parking_lot 6"));
        Assertions.assertTrue(parser.validateInputString("status"));
        Assertions.assertTrue(parser.validateInputString("park KA-01-HH-1234 White"));
        Assertions.assertTrue(parser.validateInputString("slot_number_for_registration_number KA-01-HH-3141"));
    }

    @Test
    public void testStringValidationWithInvalidInput() {
        InputParser parser = new InputParser();
        Assertions.assertFalse(parser.validateInputString("create_parking 6"));
        Assertions.assertFalse(parser.validateInputString("status 2"));
        Assertions.assertFalse(parser.validateInputString("park KA-01-HH-1234 White 2"));
        Assertions.assertFalse(parser.validateInputString("slot_number_for_registration_number"));
        Assertions.assertFalse(parser.validateInputString("please_park_car KA-01-HH-1234 White"));
        Assertions.assertFalse(parser.validateInputString("create_parking White"));
        Assertions.assertFalse(parser.validateInputString("park KA-01-HH-1234 2"));
        Assertions.assertFalse(parser.validateInputString("slot_numbers_for_cars_with_colour 2"));
    }

    @Test
    public void testCreateParkingLot() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        Assertions.assertTrue("createdaparkinglotwith5slots".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testCreateParkingLotWhenLotAlreadyCreated() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        parser.execute(createLot);
        Assertions.assertTrue("createdaparkinglotwith5slots\nsorry,parkinglotisalreadycreated".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testParkingWhenLotNotCreated() {
        InputParser parser = new InputParser();
        String park = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park);
        Assertions.assertTrue("sorry,parkinglotdoesnotexist".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testParking() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String park = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testParkingWhenLotIsFull() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 1";
        parser.execute(createLot);
        String park1 = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park1);
        String park2 = InputConstants.PARK+ " KA-01-HH-1334 Blue";
        parser.execute(park2);
        Assertions.assertTrue("createdaparkinglotwith1slots\nAllocatedslotnumber:1\nsorry,parkinglotisfull".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testParkingWhenCarIsAlreadyParked() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String park = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park);
        parser.execute(park);
        Assertions.assertTrue("createdaparkinglotwith5slots\nAllocatedslotnumber:1\nsorry,carisalreadyparked".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testCarLeaving() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String park = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park);
        String leave = InputConstants.LEAVE+ " 1";
        parser.execute(leave);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1\nslotnumber1isfree".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testCarLeavingWhenSlotIsAlreadyEmpty() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String leave = InputConstants.LEAVE+ " 1";
        parser.execute(leave);
        Assertions.assertTrue("createdaparkinglotwith5slots\nslotnumber1isalreadyfree".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testCarLeavingWhenSlotDoesNotExist() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String leave = InputConstants.LEAVE+ " 10";
        parser.execute(leave);
        Assertions.assertTrue("createdaparkinglotwith5slots\nslotnumber10doesnotexist".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testParkingWithNearestSlotAllotment() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String park1 = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park1);
        String park2 = InputConstants.PARK+ " KA-02-HH-1234 White";
        parser.execute(park2);
        String park3 = InputConstants.PARK+ " KA-03-HH-1234 White";
        parser.execute(park3);
        String leave = InputConstants.LEAVE+ " 2";
        parser.execute(leave);
        String park4 = InputConstants.PARK+ " KA-04-HH-1234 White";
        parser.execute(park4);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1\nallocatedslotnumber:2\nallocatedslotnumber:3\nslotnumber2isfree\nallocatedslotnumber:2".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testGetStatus() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String park1 = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park1);
        String park2 = InputConstants.PARK+ " KA-02-HH-1234 Blue";
        parser.execute(park2);
        parser.execute(InputConstants.STATUS);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1\nallocatedslotnumber:2\nslotno.registrationnocolour\n1KA-01-HH-1234white\n2KA-02-HH-1234blue".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testGetStatusWhenLotIsEmpty() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        parser.execute(InputConstants.STATUS);
        Assertions.assertTrue("createdaparkinglotwith5slots\nsorry,parkinglotisempty".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testGetRegistrationNumbersGivenColor() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String park1 = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park1);
        String park2 = InputConstants.PARK+ " KA-02-HH-1234 White";
        parser.execute(park2);
        String park3 = InputConstants.PARK+ " KA-03-HH-1234 Blue";
        parser.execute(park3);
        String regNoFromColor1 = InputConstants.REG_NUMBER_FOR_CARS_WITH_COLOR+" White";
        parser.execute(regNoFromColor1);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1\nallocatedslotnumber:2\nallocatedslotnumber:3\nKA-01-HH-1234,KA-02-HH-1234".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        String regNoFromColor2 = InputConstants.REG_NUMBER_FOR_CARS_WITH_COLOR+" Red";
        parser.execute(regNoFromColor2);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1\nallocatedslotnumber:2\nallocatedslotnumber:3\nKA-01-HH-1234,KA-02-HH-1234\nNotFound".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testGetSlotNumberGivenRegistrationNumber() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String park1 = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park1);
        String park2 = InputConstants.PARK+ " KA-02-HH-1234 White";
        parser.execute(park2);
        String slotNoFromRegNo1 = InputConstants.SLOT_NUMBER_FOR_CAR_WITH_REG_NUMBER +" KA-02-HH-1234";
        parser.execute(slotNoFromRegNo1);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1\nallocatedslotnumber:2\n2".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        String slotNoFromRegNo2 = InputConstants.SLOT_NUMBER_FOR_CAR_WITH_REG_NUMBER +" KA-02-HH-1222";
        parser.execute(slotNoFromRegNo2);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1\nallocatedslotnumber:2\n2\nnotfound".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testGetSlotNumbersGivenColor() {
        InputParser parser = new InputParser();
        String createLot = InputConstants.CREATE_PARKING_LOT+ " 5";
        parser.execute(createLot);
        String park1 = InputConstants.PARK+ " KA-01-HH-1234 White";
        parser.execute(park1);
        String park2 = InputConstants.PARK+ " KA-02-HH-1234 White";
        parser.execute(park2);
        String park3 = InputConstants.PARK+ " KA-03-HH-1234 Blue";
        parser.execute(park3);
        String slotsNoFromColor1 = InputConstants.SLOT_NUMBERS_FOR_CARS_WITH_COLOR +" White";
        parser.execute(slotsNoFromColor1);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1\nallocatedslotnumber:2\nallocatedslotnumber:3\n1,2".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        String slotsNoFromColor2 = InputConstants.SLOT_NUMBERS_FOR_CARS_WITH_COLOR +" Red";
        parser.execute(slotsNoFromColor2);
        Assertions.assertTrue("createdaparkinglotwith5slots\nallocatedslotnumber:1\nallocatedslotnumber:2\nallocatedslotnumber:3\n1,2\nnotfound".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
    }
}
