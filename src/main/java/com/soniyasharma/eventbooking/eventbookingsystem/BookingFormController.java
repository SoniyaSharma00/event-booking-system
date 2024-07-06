package com.soniyasharma.eventbooking.eventbookingsystem;

import com.soniyasharma.eventbooking.eventbookingsystem.classes.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookingFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField reservationsField;

    private Event selectedEvent;

    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
    }

    @FXML
    private void handleSubmitButtonAction() {
        String name = nameField.getText();
        String contact = contactField.getText();
        String reservations = reservationsField.getText();

        // Handle the submission logic here
        System.out.println("Event: " + selectedEvent.getName());
        System.out.println("Name: " + name);
        System.out.println("Contact: " + contact);
        System.out.println("Reservations: " + reservations);

        // Close the form
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}
