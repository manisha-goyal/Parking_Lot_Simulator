package com.parking_lot.input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class InputCommandsTest {

    private HashMap<String, Integer> commandsMap;

    @BeforeEach
    public void setUp() {
        commandsMap = new HashMap<String, Integer>();
        commandsMap.put(InputConstants.CREATE_PARKING_LOT, 1);
        commandsMap.put(InputConstants.PARK, 2);
        commandsMap.put(InputConstants.LEAVE, 1);
        commandsMap.put(InputConstants.STATUS, 0);
        commandsMap.put(InputConstants.REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
        commandsMap.put(InputConstants.SLOT_NUMBERS_FOR_CARS_WITH_COLOR, 1);
        commandsMap.put(InputConstants.SLOT_NUMBER_FOR_CAR_WITH_REG_NUMBER, 1);
    }

    @Test
    public void constructorAndGettersTest() {
        InputCommands commands = new InputCommands();
        Assertions.assertEquals(commandsMap, commands.getCommandsMap());
    }

    @Test
    public void testAddCommand() {
        InputCommands commands = new InputCommands();
        commandsMap.put("test_add_command", 0);
        commands.addCommand("test_add_command", 0);
        Assertions.assertEquals(commandsMap, commands.getCommandsMap());
    }
}
