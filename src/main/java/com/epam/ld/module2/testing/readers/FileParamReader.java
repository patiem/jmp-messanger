package com.epam.ld.module2.testing.readers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class FileParamReader implements ParamReader {

    private static String inputFile = "input.txt";
    private static String outputFile = "output.txt";
    private BufferedWriter writer;
    private BufferedReader reader;

    public FileParamReader(BufferedWriter writer, BufferedReader reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public String readParams() {
        StringBuilder result = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (result.length() > 0) {
                    result.append(",");
                }
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public void writeMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}