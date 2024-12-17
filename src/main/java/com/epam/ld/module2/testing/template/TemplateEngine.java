package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
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
        List<String> templateParams = extractParamsFromTemplate(template);
        HashMap<String, String> inputParams = extractParamsFromUserInput(params);
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

    private HashMap<String, String> extractParamsFromUserInput(String params) {
        HashMap<String, String> inputParams = new HashMap<>();
        Matcher matcher = Pattern.compile("(\\w+)=([^\\s,|$]+)").matcher(params);
        while (matcher.find()) {
            String group = matcher.group();
            String[] split = group.split("=");
            inputParams.put(split[0], split[1]);
        }
        return inputParams;
    }

    private List<String> extractParamsFromTemplate(Template template) {
        List<String> allMatches = new ArrayList<>();

        Matcher matcher = Pattern.compile("#\\{(.+?)\\}").matcher(template.getTemplate());
        while (matcher.find()) {
            String group = matcher.group();
            String pureHash = group.replaceAll("#", "")
                    .replaceAll("\\{", "")
                    .replaceAll("}", "");
            allMatches.add(pureHash);
        }
        return allMatches;
    }
}
