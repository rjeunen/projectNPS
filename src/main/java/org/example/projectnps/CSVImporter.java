package org.example.projectnps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVImporter implements IDataImporter{

    @Override
    public List<DataRecord> readFromFile(String filePath) {
        List<DataRecord> data = new ArrayList<>();

        //regex om key-value paren te vinden
        Pattern pattern = Pattern.compile("(\\w+)=\"([^\"]*)\"");

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;

            while ((line = br.readLine()) != null){
                //skip lege lijnen in de CSV
                if(line.trim().isEmpty() || !line.contains("name=")){
                    continue;
                }

                Matcher matcher = pattern.matcher(line);
                Map<String, String> fields = new HashMap<>();

                //alle key-value paren opslaan in een lijn
                while(matcher.find()){
                    String key = matcher.group(1); //name, state, proccessingOrder, ...
                    String value = matcher.group(2); //de values van de keys = waarde tussen "" in de CSV
                    fields.put(key.toLowerCase(), value);
                }

                //Nieuw DataRecord aanmaken met de waarden uit de map
                //Maken gebruik van getOrDefault om geen NullPointerException te krijgen
                DataRecord dataRecord = new DataRecord(
                        fields.getOrDefault("name", ""),
                        fields.getOrDefault("state", ""),
                        fields.getOrDefault("processingOrder", ""),
                        fields.getOrDefault("policyOrder", ""),
                        fields.getOrDefault("conditionId", ""),
                        fields.getOrDefault("conditionData", ""),
                        fields.getOrDefault("profileId", ""),
                        fields.getOrDefault("profileData", "")
                );

                data.add(new DataRecord());
            }

        } catch (Exception e) {
            //filePath hieronder moet deze niet ergens meegegeven worden?
            throw new RuntimeException("Fout bij het lezen van het bestand " + filePath, e);
        }
        return data;
    }
}
