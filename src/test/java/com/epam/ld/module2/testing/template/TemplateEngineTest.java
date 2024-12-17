package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateEngineTest {

    @Test
    void shouldReturnTemplateWithName(){
        TemplateEngine templateEngine = new TemplateEngine();
        String result = templateEngine.generateMessage(new Template("Name is #{name}"), new Client());

        assertEquals("Name is Bob", result);
    }
}
