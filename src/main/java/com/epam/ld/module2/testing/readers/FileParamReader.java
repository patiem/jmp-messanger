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
        return "";
    }
}