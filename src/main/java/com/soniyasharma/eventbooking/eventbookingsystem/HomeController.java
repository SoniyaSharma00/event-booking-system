package com.soniyasharma.eventbooking.eventbookingsystem;

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

import java.io.IOException;
import java.time.LocalDateTime;

public class HomeController {

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, String> nameColumn;

    @FXML
    private TableColumn<Event, String> locationColumn;

    @FXML
    private TableColumn<Event, LocalDateTime> dateTimeColumn;

    private ObservableList<Event> eventList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

        // Add sample data
        eventList.add(new Event("Concert", "City Hall", LocalDateTime.of(2024, 7, 10, 19, 30)));
        eventList.add(new Event("Conference", "Tech Center", LocalDateTime.of(2024, 8, 20, 9, 0)));
        eventList.add(new Event("Workshop", "Community Center", LocalDateTime.of(2024, 9, 15, 14, 0)));

        // Set the data to the table
        eventTable.setItems(eventList);
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
}
