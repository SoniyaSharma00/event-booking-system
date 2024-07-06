package com.soniyasharma.eventbooking.eventbookingsystem;

import com.soniyasharma.eventbooking.eventbookingsystem.classes.API;
import com.soniyasharma.eventbooking.eventbookingsystem.classes.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookingFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField contactField;

    @FXML
    private TextField reservationsField;

    @FXML
    private Label responseLabel;

    private Event selectedEvent;

    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
    }

    @FXML
    private void handleSubmitButtonAction() {
        String name = nameField.getText();
        String contact = contactField.getText();
        String reservations = reservationsField.getText();
        Integer eventId = selectedEvent.getId();

        try{
            String data = "name=" + name+ "&contact_no="+contact + "&reservations="+ reservations+ "&eventId="+ eventId;

            String response = API.post("http://localhost:8080/eventbookingsystem/events/booking",data);
            if(response.equals("event_booking_successful")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Book Event");
                alert.setHeaderText(null);
                alert.setContentText("Event booked successfully");
                alert.showAndWait();
                // Close the form
                Stage stage = (Stage) nameField.getScene().getWindow();
                stage.close();
            }else{
                responseLabel.setText(response);
                responseLabel.setStyle("-fx-text-fill: red");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseLabel.setText("Error while booking event");
        }



    }
}
