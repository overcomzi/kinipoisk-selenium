package org.example.config;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfProperties {
    protected static InputStream fileInputStream;
    protected static Properties PROPERTIES;

    static {
        try {
            fileInputStream = ConfProperties.class.getResourceAsStream("../conf.properties");
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * метод для возврата строки со значением из файла с настройками
     */
    public static <T> T getProperty(String key) {
        String property  = PROPERTIES.getProperty(key);
        if (NumberUtils.isCreatable(property)) {
            return (T) NumberUtils.createDouble(property);
        }
        return (T) property;
    }
}