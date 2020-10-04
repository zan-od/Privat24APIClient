package zan.api.privat24.builder;

import zan.api.privat24.ApplicationConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateBuilder {
    private String template;
    private Map<String, String> parameters = new HashMap<>();

    public static String loadTemplateFromFile(String filename) throws IOException {
        try (InputStream is = ApplicationConfig.class.getClassLoader().getResourceAsStream(filename);
             BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
            return rd.lines().collect(Collectors.joining("\n"));
        }
    }

    public TemplateBuilder withTemplate(String template) {
        this.template = template;
        return this;
    }

    public TemplateBuilder withTemplateFromFile(String filename) throws IOException {
        this.template = loadTemplateFromFile(filename);
        return this;
    }

    public TemplateBuilder withParameter(String name, String value) {
        parameters.put(name, value);
        return this;
    }

    public String build() {
       String result = new String(template);
       for (Map.Entry<String, String> entry : parameters.entrySet()) {
           result = result.replace("["+entry.getKey()+"]", entry.getValue());
       }

       return result;
    }
}
