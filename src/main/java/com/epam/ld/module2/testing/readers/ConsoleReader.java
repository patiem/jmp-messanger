package com.epam.ld.module2.testing.readers;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleReader implements ParamReader {

    private BufferedReader reader;


    public ConsoleReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public String readParams() {

        try {
            String userInput = reader.readLine();
            System.out.println("You entered: " + userInput);
            return userInput;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void writeMessage(String message) {
        System.out.println(message);
    }

}
