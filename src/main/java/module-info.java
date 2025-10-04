module org.example.projectnps {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.projectnps to javafx.fxml;
    exports org.example.projectnps;
}