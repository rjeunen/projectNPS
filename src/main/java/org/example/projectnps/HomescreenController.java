package org.example.projectnps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
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
    private TableColumn<DataRecord, String> nameColumn;

    @FXML
    private TableColumn<DataRecord, String> stateColumn;

    @FXML
    private TableColumn<DataRecord, String> processingOrderColumn;

    @FXML
    private TableColumn<DataRecord, String> policySourceColumn;

    @FXML
    private TableColumn<DataRecord, String> conditionIdColumn;

    @FXML
    private TableColumn<DataRecord, String> conditionDataColumn;

    @FXML
    private TableColumn<DataRecord, String> profileIdColumn;

    @FXML
    private TableColumn<DataRecord, String> profileDataColumn;

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
    public void initialize(){
        setupColumns();
        setupProcessingOrderEditing();
    }

    private void setupColumns(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        processingOrderColumn.setCellValueFactory(new PropertyValueFactory<>("processingOrder"));
        policySourceColumn.setCellValueFactory(new PropertyValueFactory<>("policySource"));
        conditionIdColumn.setCellValueFactory(new PropertyValueFactory<>("conditionId"));
        conditionDataColumn.setCellValueFactory(new PropertyValueFactory<>("conditionData"));
        profileIdColumn.setCellValueFactory(new PropertyValueFactory<>("profileId"));
        profileDataColumn.setCellValueFactory(new PropertyValueFactory<>("profileData"));
    }

    private void setupProcessingOrderEditing(){
        //aangeven dat de gebuiker wijwigingen mag maken
        contentTableView.setEditable(true);

        //Aangeven dat processingOrder bewerkbaar is
        processingOrderColumn.setCellFactory(TextFieldTableCell.forTableColumn()); //aanpassen is in een standaard textveld

        processingOrderColumn.setOnEditCommit(event -> {
            DataRecord record = event.getRowValue();
            String newValue = event.getNewValue();

            int newIndex;
            try{
                newIndex = Integer.parseInt(newValue) - 1; //index is 0 based
            }
            catch (NumberFormatException e){
                System.out.println("Ongeldige waarde: " + newValue);
                return;
            }

            List<DataRecord> data = new ArrayList<>(contentTableView.getItems()); //kopie huidige dataSet
            data.remove(record); //wissen van de aangepaste record

            if(newIndex < 0){
                newIndex = 0;
            }
            if(newIndex > data.size()){
                newIndex = data.size();
            }

            data.add(newIndex, record); //data toevoegen op haar nieuze plaats (rij)

            for(int  i = 0; i < data.size(); i++){
                data.get(i).setProcessingOrder(String.valueOf(i + 1));
            }

            contentTableView.getItems().setAll(data);
            contentTableView.refresh(); //soort van forceren om de tabel opnieuw in te lezen = optioneel

            //record.setProcessingOrder(event.getNewValue());
        });
    }

    @FXML
    protected void getData(){
        CSVImporter importer = new CSVImporter();
        List<DataRecord> importedData = importer.readFromFile("C:\\Users\\robje\\projectNpsCsvData\\nps.csv");

        contentTableView.getItems().setAll(importedData);
    }

    @FXML
    protected void exportData(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporteer CSV-bestand");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV-bestanden", "*.csv"));

        File selectedFile = fileChooser.showSaveDialog(contentTableView.getScene().getWindow());

        if(selectedFile != null){
            CSVImporter exporter = new CSVImporter();
            List<DataRecord> currentData = new ArrayList<>(contentTableView.getItems());
            exporter.writeToFile(selectedFile.getAbsolutePath(), currentData);
        }
    }
}
