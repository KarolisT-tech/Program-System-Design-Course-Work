module com.example.kursinisgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires junit;

    opens com.example.kursinisgame to javafx.fxml;
    exports com.example.kursinisgame;
    opens com.example.kursinisgame.controllers to javafx.fxml;

    exports com.example.kursinisgame.models;
    opens com.example.kursinisgame.models;

}