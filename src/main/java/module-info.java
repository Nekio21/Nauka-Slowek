module NaukaSlowek {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    exports umk.mat.jakuburb;
    opens umk.mat.jakuburb.controllers;
    opens umk.mat.jakuburb.encje;
    opens umk.mat.jakuburb.database;
    opens umk.mat.jakuburb.controllers.helpers;
}