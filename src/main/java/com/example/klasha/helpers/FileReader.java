package com.example.klasha.helpers;


import org.bouncycastle.crypto.digests.HarakaBase;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileReader {


    String fileUrl = "https://drive.google.com/uc?export=download&id=1paeSSkqcbry7zg7LD7TNeLPQ0w_rW65E";


    @Cacheable(value = "ratesTable")
    public Map<String, Double> readFile() throws MalformedURLException, IOException {
        Map<String, Double> ratesTable = new HashMap<>();
        try {
            URL url = new URL(this.fileUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String line;
            boolean isFirstLine = true;


            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    // Skip the header line
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");
                DecimalFormat decimalFormat = new DecimalFormat("#0.000000");

                String sourceCurr = parts[0].trim();
                String targetCurr = parts[1].trim();
                double rate = Double.parseDouble(parts[2].trim());

                String key = sourceCurr + "-" + targetCurr;
                ratesTable.put(key, Double.parseDouble(decimalFormat.format(rate)));

                String reverseKey = targetCurr + "-" + sourceCurr;

                Double reverseRate = 1.0 / rate;
                ratesTable.put(reverseKey, Double.parseDouble(decimalFormat.format(reverseRate)));

            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ratesTable;
    }


    @Cacheable(key = "#exchange", value = "rate")
    public Double getRate(String exchange){
        Map<String, Double> stringDoubleMap = null;
        try {
            stringDoubleMap = this.readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringDoubleMap.getOrDefault(exchange, null);
    }
}
