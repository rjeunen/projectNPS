package org.example.projectnps;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
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
    private Button addRecordButton;

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecteer een CSV bestand om te importeren");

        //enkel CSV bestanden zijn zichtbaar
        FileChooser.ExtensionFilter csvFilter = new FileChooser.ExtensionFilter("CSV bestanden (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(csvFilter);

        //Toon venster en laat gebruiker bestand kiezen
        Stage stage = (Stage) contentTableView.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        //Controle of er een bestand gekozen is
        if(selectedFile == null){
            System.out.println("Geen bestaand geselecteerd");
            return;
        }

        //Controle op CSV
        if(!selectedFile.getName().toLowerCase().endsWith(".csv")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ongeldig bestand");
            alert.setHeaderText(null);
            alert.setContentText("Het gekozen bestand is geen CSV bestand. Selecteer een CSV bestand");
            alert.showAndWait();
            return;
        }

        //Alles in try catch om de app niet te doen crashen als er missende kolommen zijn in een bestand
        try{
            //Geldig bestand = inlezen data
            CSVImporter csvImporter = new CSVImporter();
            List<DataRecord> importedData = csvImporter.readFromFile(selectedFile.getAbsolutePath());

            //Toon de data in de TableView
            contentTableView.getItems().setAll(importedData);

            //Succes melding na inladen CSV bestand
            showAlert("Import succesvol", "Het bestand \"" + selectedFile.getName() + "\" werd succesvol geladen", Alert.AlertType.INFORMATION);
        }
        catch (IllegalArgumentException e){
            showAlert("CSV-fout", e.getMessage(), Alert.AlertType.ERROR);
        }
        catch (Exception e){
            showAlert("Fout", "Er is iets mis gegaan bij het importeren van het bestand", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    @FXML
    protected void addRecord(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addRecord.fxml"));
            DialogPane dialogPane = loader.load();

            AddRecordController dialogController = loader.getController();

            int nextOrder = contentTableView.getItems().size() + 1;
            dialogController.setNextProcessingOrder(nextOrder);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Nieuwe record toevoegen");

            dialog.showAndWait().ifPresent(repsonse -> {
                if(repsonse.getButtonData().isDefaultButton()){
                    if(dialogController.validateInput()){
                        DataRecord newRecord = dialogController.getRecord();
                        contentTableView.getItems().add(newRecord);
                    }
                }
            });
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
