package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.readers.ParamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Template engine.
 */
public class TemplateEngine {

    private static final String PATTERN_FOR_FILLING = "#{%s}";
    private static final String PATTERN_FOR_INPUT = "(\\w+)=([^\\s,|$]+)";
    private static final String PATTERN_FOR_TEMPLATE = "#\\{(.+?)\\}";


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
        List<String> templateParams = extractParamsFromTemplate(template);
        HashMap<String, String> inputParams = extractParamsFromUserInput(params);
        return fillTemplate(template, templateParams, inputParams);
    }

    private String fillTemplate(Template template, List<String> templateParams, HashMap<String, String> inputParams) {
        String stringTemplate = template.getTemplate();
        for (String param : templateParams) {
            if (inputParams.containsKey(param)) {
                String quote = Pattern.quote(String.format(PATTERN_FOR_FILLING, param));
                stringTemplate = stringTemplate.replaceAll(quote, inputParams.get(param));
            }
        }
        return stringTemplate;
    }

    private HashMap<String, String> extractParamsFromUserInput(String params) {
        HashMap<String, String> inputParams = new HashMap<>();
        Matcher matcher = Pattern.compile(PATTERN_FOR_INPUT).matcher(params);
        while (matcher.find()) {
            String group = matcher.group();
            String[] split = group.split("=");
            inputParams.put(split[0], split[1]);
        }
        return inputParams;
    }

    private List<String> extractParamsFromTemplate(Template template) {
        List<String> allMatches = new ArrayList<>();

        Matcher matcher = Pattern.compile(PATTERN_FOR_TEMPLATE).matcher(template.getTemplate());
        while (matcher.find()) {
            String group = matcher.group();
            allMatches.add(removeCharsFromHash(group));
        }
        return allMatches;
    }

    private static String removeCharsFromHash(String group) {
        return group.replaceAll("#", "")
                .replaceAll("\\{", "")
                .replaceAll("}", "");
    }
}
