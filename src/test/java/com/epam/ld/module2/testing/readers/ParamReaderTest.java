package com.epam.ld.module2.testing.readers;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParamReaderTest {

    @TestFactory
    Collection<DynamicTest> dynamicTestsForParamReaders() throws IOException {
        // Setup mocks
        BufferedWriter mockWriter = mock(BufferedWriter.class);
        BufferedReader mockReader = mock(BufferedReader.class);
        BufferedReader mockConsoleReader = mock(BufferedReader.class);

        // Create instances of classes under test
        FileParamReader fileParamReader = new FileParamReader(mockWriter, mockReader);
        ConsoleReader consoleReader = new ConsoleReader(mockConsoleReader);

        // Define expected inputs and outputs
        String fileInput = "line1,line2";
        String consoleInput = "user input";

        try {
            // Mock behavior of BufferedReader
            when(mockReader.readLine())
                    .thenReturn("line1")
                    .thenReturn("line2")
                    .thenReturn(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        when(mockConsoleReader.readLine()).thenReturn(consoleInput);

        return Arrays.asList(
                DynamicTest.dynamicTest("Test FileParamReader", () -> {
                    // Execute the method under test
                    String result = fileParamReader.readParams();

                    // Verify that readLine() was called the expected number of times
                    verify(mockReader, times(3)).readLine();
                    assertEquals(fileInput, result, "File input should match expected output.");
                }),
                DynamicTest.dynamicTest("Test ConsoleReader", () -> {
                    // Execute the method under test
                    String result = consoleReader.readParams();

                    // Verify that nextLine() was called once
                    verify(mockConsoleReader, times(1)).readLine();
                    assertEquals(consoleInput, result, "Console input should match expected output.");
                })
        );
    }
}