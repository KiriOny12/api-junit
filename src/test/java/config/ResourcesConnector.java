package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ResourcesConnector {

    public String getProperty(String fileName, String key) {
        Properties property = new Properties();
        try {
            String PROPERTIES_FILE_PATH = "src/test/resources/" + fileName + ".properties";
            FileInputStream fis = new FileInputStream(PROPERTIES_FILE_PATH);
            property.load(fis);
        }catch (IOException exception){
            exception.printStackTrace();
        }
        return property.getProperty(key);
    }
}
