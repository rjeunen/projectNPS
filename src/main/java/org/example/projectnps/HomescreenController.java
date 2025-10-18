package org.example.projectnps;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomescreenController {
    @FXML
    private AnchorPane homeScreenPane;

    @FXML
    private AnchorPane creditsPane;

    @FXML
    private AnchorPane navigationPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<DataRecord> contentTableView;

    @FXML
    private Label creditsLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox containerVbox;

    @FXML
    private Button importButton;

    @FXML
    private Button exportButton;

    @FXML
    protected void getData(){
        /*
         *Vraag na of de CSV wel juist is - geen juiste file?!
         * Deze method heeft nog werk nodig!
         * Volgende stap is om de data zichtbaar te krijgen in de view - tableView?
         * */

        List<String[]> csvValues = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\robje\\projectNpsCsvData\\nps.csv"));

            String line;
            while((line = br.readLine()) != null){
                String[] lineValues = line.split(" "); //regex is spatie
                csvValues.add(lineValues);
            }
            //data van 1 rij naar een array duwen - 1 volledige rij is dus [0] ... - betere oplossing voor?
            for(int i = 0; i < csvValues.size(); i++){
                String[] row = csvValues.get(i);
                System.out.println("Rij " + i + " :" + Arrays.toString(row));
            }
        }
        catch (IOException e){
            System.out.println("Something went wrong retrieving the CSV file");
        }
    }
}
