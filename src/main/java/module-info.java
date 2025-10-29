module org.example.projectnps {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.projectnps to javafx.fxml;
    exports org.example.projectnps;
}