module org.example.socket_client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.socket_client to javafx.fxml;
    exports org.example.socket_client;
}