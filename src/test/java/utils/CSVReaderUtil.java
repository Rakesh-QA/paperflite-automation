package utils;


import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.*;

public class CSVReaderUtil {

    public static Map<String, String> getData() {
        Map<String, String> map = new HashMap<>();

        try {
            CSVReader reader = new CSVReader(
                new FileReader("src/test/resources/data/testdata.csv")
            );

            List<String[]> data = reader.readAll();

            String[] header = data.get(0);
            String[] values = data.get(1);

            for (int i = 0; i < header.length; i++) {
                map.put(header[i], values[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}