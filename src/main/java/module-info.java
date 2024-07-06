module com.soniyasharma.eventbooking.eventbookingsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.soniyasharma.eventbooking.eventbookingsystem to javafx.fxml;
    exports com.soniyasharma.eventbooking.eventbookingsystem;
}