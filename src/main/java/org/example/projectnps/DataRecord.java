package org.example.projectnps;

public class DataRecord {
    private String name;
    private String state;
    private String processingOrder;
    private String policySource;
    private String conditionId;
    private String conditionData;
    private String profileId;
    private String profileData;

    //Lege constructor nodig voor JavaFX / FXML Binding
    public DataRecord(){

    }

    public DataRecord(String name, String state, String processingOrder, String policySource,
                      String conditionId, String conditionData, String profileId, String profileData){
        this.name = name;
        this.state = state;
        this.processingOrder = processingOrder;
        this.policySource = policySource;
        this. conditionId = conditionId;
        this.conditionData = conditionData;
        this.profileId = profileId;
        this.profileData = profileData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProcessingOrder() {
        return processingOrder;
    }

    public void setProcessingOrder(String processingOrder) {
        this.processingOrder = processingOrder;
    }

    public String getPolicySource() {
        return policySource;
    }

    public void setPolicySource(String policySource) {
        this.policySource = policySource;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionData() {
        return conditionData;
    }

    public void setConditionData(String conditionData) {
        this.conditionData = conditionData;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileData() {
        return profileData;
    }

    public void setProfileData(String profileData) {
        this.profileData = profileData;
    }
}

