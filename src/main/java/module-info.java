module org.example.pong {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.unsupported.desktop;
    requires jdk.xml.dom;


    opens org.example.pong to javafx.fxml;
    exports org.example.pong;
}