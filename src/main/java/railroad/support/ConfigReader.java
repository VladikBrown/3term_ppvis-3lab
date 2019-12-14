package railroad.support;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigReader {
    private ConfigReader(){}

    public static Config read(String pathOfConfig) {
        Constructor constructor = new Constructor(Config.class);
        TypeDescription configDescription = new TypeDescription(Config.class);
        configDescription.putListPropertyType("stations", Config.RawStation.class);
        constructor.addTypeDescription(configDescription);
        Yaml yaml = new Yaml(constructor);

        InputStream input = null;
        try {
            input = new FileInputStream(new File(pathOfConfig));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return yaml.loadAs(input, Config.class);
    }
}
