package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.readers.ParamReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TemplateEngineTest {

    @Mock
    private ParamReader paramReader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldReturnTemplateWithName(){
        when(paramReader.readParams()).thenReturn("name=Bob");

        TemplateEngine templateEngine = new TemplateEngine(paramReader);
        String result = templateEngine.generateMessage(new Template("Name is #{name}"), new Client());

        assertEquals("Name is Bob", result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Mike", "Example", "Test123"})
    void shouldReturnTemplateForOtherNames(String name){
        when(paramReader.readParams()).thenReturn(String.format("name=%s", name));

        TemplateEngine templateEngine = new TemplateEngine(paramReader);
        String result = templateEngine.generateMessage(new Template("Name is #{name}"), new Client());
        String answer = String.format("Name is %s", name);
        assertEquals(answer, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"tag", "name", "color"})
    void shouldReturnTemplateForDifferentHashVales(String hashValue){
        when(paramReader.readParams()).thenReturn(String.format("%s=Test", hashValue));
        String template = String.format("%s is #{%s}", hashValue.toUpperCase(), hashValue);

        TemplateEngine templateEngine = new TemplateEngine(paramReader);
        String result = templateEngine.generateMessage(new Template(template), new Client());
        String answer = String.format("%s is Test", hashValue.toUpperCase());
        assertEquals(answer, result);
    }

    @Test
    void shouldWorkForMoreThanOneHashValue(){
        when(paramReader.readParams()).thenReturn("name=Bob, tag=Test");

        TemplateEngine templateEngine = new TemplateEngine(paramReader);
        String result = templateEngine.generateMessage(new Template("Name is #{name}, Tag is #{tag}"), new Client());

        assertEquals("Name is Bob, Tag is Test", result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"name=Bob tag=Test", "name=Bob\ttag=Test", "name=Bob\ntag=Test"})
    void shouldWorkForMoreThanOneHashValueWhenNotComaSeparated(String input){
        when(paramReader.readParams()).thenReturn(input);

        TemplateEngine templateEngine = new TemplateEngine(paramReader);
        String result = templateEngine.generateMessage(new Template("Name is #{name}, Tag is #{tag}"), new Client());

        assertEquals("Name is Bob, Tag is Test", result);
    }
}
