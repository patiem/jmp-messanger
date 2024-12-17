package com.epam.ld.module2.testing.readers;

import java.io.BufferedReader;

public class ConsoleReader implements ParamReader {

    private BufferedReader reader;


    public ConsoleReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public String readParams() {
            return "";
    }

}
