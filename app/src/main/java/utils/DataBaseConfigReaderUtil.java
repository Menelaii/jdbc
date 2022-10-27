package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataBaseConfigReaderUtil {

    public static String getValueOf(String propertyName) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("database.properties");

        try (InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(streamReader)) {

            String line;
            int regexIndex;
            while((line = bufferedReader.readLine()) != null){
                regexIndex = line.indexOf("=");
                if(line.substring(0, regexIndex).equals(propertyName)) {
                    return line.substring(regexIndex + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
