package org.example.projectnps;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

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
        try{
            //onderstaande moet nog verwijzen naar nps.csv MAAR eerst navragen of dit wel klopt?
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\robje\\projectNpsCsvData\\test.csv"));

            /*
            * Deze while loop gaat elke lijn in een array duwen.
            * Zou ik nu ook een 2D array moeten maken die vb [1000] 1000 lijnen kan bevatten en [aantal kolommen] (afhankelijk van CSV) moet bevatten
            * Dan in diezelfde while loop kan ik de data direct op de juiste plaats weergeven?
            * check JAVAFX PPT - DIA 27
            * Ik weet niet of de devices[][] klopt hieronder EN heb een vaste grootte van [1000][8] gemaakt wat ook niet goed is vermoed ik
            * */
            String line;
            String[][] devices = new String[1000][8];
            int deviceCounter = 0;
            while((line = br.readLine()) != null){
                String [] csvValues = line.split(";"); // regex nakijken in de CSV - moet dit op spatie?
                devices[deviceCounter][0] = csvValues[0];
                devices[deviceCounter][1] = csvValues[1];
                devices[deviceCounter][2] = csvValues[2];
                devices[deviceCounter][3] = csvValues[3];
                devices[deviceCounter][4] = csvValues[4];
                devices[deviceCounter][5] = csvValues[5];
                devices[deviceCounter][6] = csvValues[6];
                devices[deviceCounter][7] = csvValues[7];
                deviceCounter++;
            }
            //System.out.println(Arrays.toString(csvValues));
            System.out.println(Arrays.deepToString(devices));
            //System.out.println(devices[1][1]);
        }
        catch (IOException e){
            System.out.println("Something went wrong retrieving the CSV file");
        }
    }
}
