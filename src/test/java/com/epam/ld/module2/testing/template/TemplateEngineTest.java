package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
}
