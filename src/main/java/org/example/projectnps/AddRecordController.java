package org.example.projectnps;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddRecordController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField processingOrderField;
    @FXML
    private TextField policySourceField;
    @FXML
    private TextField conditionIdField;
    @FXML
    private TextField conditionDataField;
    @FXML
    private TextField profileIdField;
    @FXML
    private TextField profileDataField;

    // het automatisch invullen van het veld processingOrder
    public void setNextProcessingOrder(int nextOrder) {
        processingOrderField.setText(String.valueOf(nextOrder));
        processingOrderField.setDisable(true); // niet bewerkbaar maken
    }

    public DataRecord getRecord() {
        return new DataRecord(
                nameField.getText(),
                stateField.getText(),
                processingOrderField.getText(),
                policySourceField.getText(),
                conditionIdField.getText(),
                conditionDataField.getText(),
                profileIdField.getText(),
                profileDataField.getText()
        );
    }
}
