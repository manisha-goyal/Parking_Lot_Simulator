package com.parking_lot.input;

import java.util.HashMap;

public class InputCommands {

    private static HashMap<String, Integer> commandsMap;

    public InputCommands() {
        commandsMap = new HashMap<String, Integer>();
        commandsMap.put(InputConstants.CREATE_PARKING_LOT, 1);
        commandsMap.put(InputConstants.PARK, 2);
        commandsMap.put(InputConstants.LEAVE, 1);
        commandsMap.put(InputConstants.STATUS, 0);
        commandsMap.put(InputConstants.REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
        commandsMap.put(InputConstants.SLOT_NUMBERS_FOR_CARS_WITH_COLOR, 1);
        commandsMap.put(InputConstants.SLOT_NUMBER_FOR_CAR_WITH_REG_NUMBER, 1);
    }

    public static HashMap<String, Integer> getCommandsMap() {
        return commandsMap;
    }

    public static void addCommand(String command, int parameterCount) {
        getCommandsMap().put(command,parameterCount);
    }
}
