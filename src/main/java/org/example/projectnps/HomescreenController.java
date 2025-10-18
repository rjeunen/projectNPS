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
        CSVImporter importer = new CSVImporter();
        List<DataRecord> importedData = importer.readFromFile("C:\\Users\\robje\\projectNpsCsvData\\nps.csv");

        contentTableView.getItems().setAll(importedData);
    }
}
