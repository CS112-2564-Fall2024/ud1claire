module org.example.ud1claire {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.ud1claire to javafx.fxml;
    exports org.example.ud1claire;
}