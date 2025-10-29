package org.example.projectnps;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    public boolean validateInput(){
        if(nameField.getText().isEmpty()||
        stateField.getText().isEmpty()||
        processingOrderField.getText().isEmpty()||
        policySourceField.getText().isEmpty()||
        conditionIdField.getText().isEmpty()||
        conditionDataField.getText().isEmpty()||
        profileIdField.getText().isEmpty()||
        profileDataField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ongeldige invoer");
            alert.setHeaderText("Niet alle velden zijn ingevuld");
            alert.setContentText("Gelieve alle velden in te vullen voor u verder gaat");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
