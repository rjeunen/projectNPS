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
        //aangeven dat de gebuiker wijwigingen mag maken
        contentTableView.setEditable(true);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        processingOrderColumn.setCellValueFactory(new PropertyValueFactory<>("processingOrder"));
        policySourceColumn.setCellValueFactory(new PropertyValueFactory<>("policySource"));
        conditionIdColumn.setCellValueFactory(new PropertyValueFactory<>("conditionId"));
        conditionDataColumn.setCellValueFactory(new PropertyValueFactory<>("conditionData"));
        profileIdColumn.setCellValueFactory(new PropertyValueFactory<>("profileId"));
        profileDataColumn.setCellValueFactory(new PropertyValueFactory<>("profileData"));

        //processingOrder kolom effectief bewerkbaar makent
        //TextFieldTableCell.forTableColumn() betekent, gebrui een textfield bij aanpassingen
        processingOrderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        processingOrderColumn.setOnEditCommit(event -> {
            DataRecord record = event.getRowValue();
            record.setProcessingOrder(event.getNewValue());
        });
    }

    @FXML
    protected void getData(){
        CSVImporter importer = new CSVImporter();
        List<DataRecord> importedData = importer.readFromFile("C:\\Users\\robje\\projectNpsCsvData\\nps.csv");

        contentTableView.getItems().setAll(importedData);
    }
}
