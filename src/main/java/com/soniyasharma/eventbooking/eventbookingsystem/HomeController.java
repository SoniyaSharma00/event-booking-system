package com.soniyasharma.eventbooking.eventbookingsystem;

import com.soniyasharma.eventbooking.eventbookingsystem.BookingFormController;
import com.soniyasharma.eventbooking.eventbookingsystem.classes.API;
import com.soniyasharma.eventbooking.eventbookingsystem.classes.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeController {

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> nameColumn;

    @FXML
    private TableColumn<Event, String> locationColumn;

    @FXML
    private TableColumn<Event, String> dateColumn;

    private final ObservableList<Event> allEvents = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        getAllEvents();
        // Set the data to the table
        eventTable.setItems(allEvents);
    }

    private void getAllEvents() {
        try {
            String getEventFromApi = API.get("http://localhost:8080/eventbookingsystem/events");
            JSONArray jsonArray = new JSONArray(getEventFromApi);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Event event = new Event(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("location"),
                        jsonObject.getString("date")
                );
                allEvents.add(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBookButtonAction() throws IOException {
        Event selectedEvent = eventTable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("booking_form.fxml"));
            Parent root = loader.load();

            BookingFormController controller = loader.getController();
            controller.setSelectedEvent(selectedEvent);

            Stage stage = new Stage();
            stage.setTitle("Book Event");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    private void goToBookings() throws IOException {
       App.setRoot("bookings");
    }

}
