module com.soniyasharma.eventbooking.eventbookingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires jakarta.servlet;

    opens com.soniyasharma.eventbooking.eventbookingsystem to javafx.fxml;
    exports com.soniyasharma.eventbooking.eventbookingsystem;
    opens com.soniyasharma.eventbooking.eventbookingsystem.classes to javafx.base;
    exports com.soniyasharma.eventbooking.eventbookingsystem.classes;

}
