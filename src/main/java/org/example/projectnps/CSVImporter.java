package org.example.projectnps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVImporter implements IDataImporter, IDataExporter{

    @Override
    public List<DataRecord> readFromFile(String filePath) {
        List<DataRecord> data = new ArrayList<>();

        //regex om key-value paren te vinden
        Pattern pattern = Pattern.compile("(\\w+)=\"([^\"]*)\"");

        //Verwachte kolommen in CSV
        List<String> requiredColumns = List.of(
                "name", "state", "processingorder", "policysource", "conditionid", "conditiondata",
                "profileid", "profiledata"
        );

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            boolean columsValidated = false;

            while ((line = br.readLine()) != null){
                //skip lege lijnen in de CSV
                if(line.trim().isEmpty() || !line.contains("name=")){
                    continue;
                }

                Matcher matcher = pattern.matcher(line);
                Map<String, String> fields = new HashMap<>();

                //Alle key-value paren opslaan in een lijn
                while(matcher.find()){
                    String key = matcher.group(1); //name, state, proccessingOrder, ...
                    String value = matcher.group(2); //de values van de keys = waarde tussen "" in de CSV
                    fields.put(key.toLowerCase(), value);
                }

                //Controle op lijn 1 of de nodige kolommen aanwezig zijn
                if(!columsValidated){
                    columsValidated = true;

                    List<String> missingColums = new ArrayList<>();
                    for(String required : requiredColumns){
                        if(!fields.containsKey(required)){
                            missingColums.add(required);
                        }
                    }

                    if(!missingColums.isEmpty()){
                        throw  new IllegalArgumentException(
                                "De CSV mist de volgende kolommen: " + String.join(", ", missingColums)
                        );
                    }
                }

                //Nieuw DataRecord aanmaken met de waarden uit de map
                //Maken gebruik van getOrDefault om geen NullPointerException te krijgen
                DataRecord dataRecord = new DataRecord(
                        fields.getOrDefault("name", ""),
                        fields.getOrDefault("state", ""),
                        fields.getOrDefault("processingorder", ""),
                        fields.getOrDefault("policysource", ""),
                        fields.getOrDefault("conditionid", ""),
                        fields.getOrDefault("conditiondata", ""),
                        fields.getOrDefault("profileid", ""),
                        fields.getOrDefault("profiledata", "")
                );

                data.add(dataRecord);
            }

        }
        catch (IllegalArgumentException e){
            throw  e;
        }
        catch (Exception e) {
            throw new RuntimeException("Fout bij het lezen van het bestand " + filePath, e);
        }
        return data;
    }

    @Override
    public void writeToFile(String filePath, List<DataRecord> data) {
        try(FileWriter fileWriter = new FileWriter(filePath)){
            //schrijf eerst de header - eerste lijn in de CSV
            fileWriter.write("name, state, processingorder, policysource, conditionid, conditiondata, profileid, profiledata\n");

            //Elk dataRecord schrijven op een aparte rij
            for(DataRecord record : data){
                String line = String.join(";",
                        record.getName(),
                        record.getState(),
                        record.getProcessingOrder(),
                        record.getPolicySource(),
                        record.getConditionId(),
                        record.getConditionData(),
                        record.getProfileId(),
                        record.getProfileData()
                        );
                fileWriter.write(line + System.lineSeparator());
            }
            //testing
            System.out.println("Export voltooid: " + filePath);
        }
        catch (Exception e){
            throw  new RuntimeException("Fout bij het exporteren van de file: " + filePath, e);
        }
    }

    /* ============================================================
   ALTERNATIEVE EXPORTMETHODE – UITGESCHAKELD (COMMENTED OUT)
   ------------------------------------------------------------
   Deze methode toont hoe dezelfde data kan worden geëxporteerd
   in het originele formaat waarin de CSV werd geïmporteerd:
   nl. met het "add np" prefix en alle velden tussen aanhalingstekens.

   Deze implementatie is niet actief, enkel ter illustratie.
   ============================================================
*/
    /*
    public void writeToFile(String filePath, List<DataRecord> data) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            // Elke record in "add np" formaat schrijven
            for (DataRecord record : data) {
                String line = String.format(
                    "add np name=\"%s\" state=\"%s\" processingorder=\"%s\" " +
                    "policysource=\"%s\" conditionid=\"%s\" conditiondata=\"%s\" " +
                    "profileid=\"%s\" profiledata=\"%s\"",
                    record.getName(),
                    record.getState(),
                    record.getProcessingOrder(),
                    record.getPolicySource(),
                    record.getConditionId(),
                    record.getConditionData(),
                    record.getProfileId(),
                    record.getProfileData()
                );
                fileWriter.write(line + System.lineSeparator());
            }
            System.out.println("Export voltooid in 'add np' formaat: " + filePath);
        } catch (Exception e) {
            throw new RuntimeException("Fout bij het exporteren van de file in add np formaat: " + filePath, e);
        }
    }
    */
}
