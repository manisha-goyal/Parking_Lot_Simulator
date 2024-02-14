package com.parking_lot.parking_service;

import com.parking_lot.input.InputConstants;
import com.parking_lot.input.InputParser;

import java.io.*;

public class ParkingApp {

    public static void main(String[] args) {

        InputParser parser = new InputParser();
        BufferedReader bufferReader = null;
        String input = null;

        switch (args.length) {
            case 0: //cmd input
                while(true) {
                    try {
                        bufferReader = new BufferedReader(new InputStreamReader(System.in));
                        input = bufferReader.readLine().trim();
                        if (input.equalsIgnoreCase("exit"))
                            break;
                        else
                        {
                            if (parser.validateInputString(input))
                                parser.execute(input.trim());
                            else
                            {
                                System.out.println("Sorry, invalid input");
                            }
                        }
                    }
                    catch(IOException e) {
                        System.out.println("Sorry, invalid request");
                    }
                }
                break;

            case 1: //file input
                File inputFile = new File(args[0]);

                try
                {
                    bufferReader = new BufferedReader(new FileReader(inputFile));
                    int lineNo = 1;
                    while ((input = bufferReader.readLine()) != null)
                    {
                        input = input.trim();
                        if(parser.validateInputString(input))
                            parser.execute(input);
                        else
                            System.out.println("Invalid input found at line " + lineNo + ": '" +input+ "'");
                        lineNo++;
                    }
                }
                catch(FileNotFoundException e) {
                    System.out.println("Sorry, file not found");
                }
                catch(IOException e) {
                    System.out.println("Sorry, invalid request");
                }
                break;

            default:
                System.out.println("Sorry, invalid input");
            break;
        }

        try
        {
            if (bufferReader != null)
                bufferReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
