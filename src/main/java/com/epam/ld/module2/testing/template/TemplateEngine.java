package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The type Template engine.
 */
public class TemplateEngine {

    private ParamReader reader;

    public TemplateEngine(ParamReader reader) {
        this.reader = reader;
    }

    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        String params = reader.readParams();
        List<String> templateParams = extractParamsFromTemplate();
        HashMap<String, String> inputParams = extractParamsFromUserInput();
        return fillTemplate(template, templateParams, inputParams);
    }

    private String fillTemplate(Template template, List<String> templateParams, HashMap<String, String> inputParams) {
        String stringTemplate = template.getTemplate();
        for (String param : templateParams) {
            if (inputParams.containsKey(param)) {
                String quote = Pattern.quote("#{" + param + '}');
                stringTemplate = stringTemplate.replaceAll(quote, inputParams.get(param));
            }
        }
        return stringTemplate;
    }

    private HashMap<String, String> extractParamsFromUserInput() {
        HashMap<String, String> inputParams = new HashMap<>();
        inputParams.put("name", "Bob");
        return inputParams;
    }

    private List<String> extractParamsFromTemplate() {
        return Collections.singletonList("name");
    }
}
