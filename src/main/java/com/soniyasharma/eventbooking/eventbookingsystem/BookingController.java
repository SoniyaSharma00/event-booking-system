package com.soniyasharma.eventbooking.eventbookingsystem;

import com.soniyasharma.eventbooking.eventbookingsystem.classes.API;
import com.soniyasharma.eventbooking.eventbookingsystem.classes.Booking;
import com.soniyasharma.eventbooking.eventbookingsystem.classes.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class BookingController {

    @FXML
    private TableView<Booking> bookingTable;

    @FXML
    private TableColumn<Booking, String> nameColumn;

    @FXML
    private TableColumn<Booking, String> contactNoColumn;


    @FXML
    private TableColumn<Booking, Integer> noOfReservationsColumn;

    @FXML
    private TableColumn<Booking, String> eventNameColumn;

    @FXML
    private TableColumn<Booking, String> eventLocationColumn;

    @FXML
    private TableColumn<Booking, String > eventDateColumn;
    @FXML
    private TableColumn<Booking, String > statusColumn;

    @FXML
    private ComboBox<String> statusComboBox;
    private final ObservableList<Booking> allBookings = FXCollections.observableArrayList();

    private Booking selectedBooking;
    @FXML
    public void initialize() {
        // Initialize the columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactNoColumn.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        noOfReservationsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfReservations"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("eventLocation"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        getAllBookings();
        // Set the data to the table
        bookingTable.setItems(allBookings);

        bookingTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedBooking = newSelection;
                statusComboBox.getSelectionModel().select(selectedBooking.getStatus());
            }
        });
    }

    private void getAllBookings()
    {
        allBookings.clear();
        try {
            String getBookingsFromApi = API.get("http://localhost:8080/eventbookingsystem/events/booking");
            JSONArray jsonArray = new JSONArray(getBookingsFromApi);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Booking booking = new Booking(
                        jsonObject.getInt("id"),
                        jsonObject.getString("name"),
                        jsonObject.getString("contact_no"),
                        jsonObject.getInt("no_of_reservations"),
                        jsonObject.getInt("event_id"),
                        jsonObject.getString("event_name"),
                        jsonObject.getString("event_location"),
                        jsonObject.getString("event_date"),
                        jsonObject.getInt("status")
                );
                allBookings.add(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleUpdateStatus() {
        if (selectedBooking != null) {
            String selectedStatus = statusComboBox.getValue();

            updateOrderStatus(selectedBooking.getId(), selectedStatus);
        }
    }

    private void updateOrderStatus(int bookingId, String status) {
        try {
            int statusId = 0;
            switch (status) {
                case "pending":
                    statusId = 0;
                    break;
                case "approved":
                    statusId = 1;
                    break;
                default:
                    statusId = 0;
                    break;
            }

            String formData = "bookingId=" + bookingId +
                    "&status=" + statusId;
            String response = API.post("http://localhost:8080/eventbookingsystem/events/booking", formData,"PUT");
            System.out.println(response);
            if ("booking_status_update_success".equals(response)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Booking status changed successfully!", ButtonType.OK);
                alert.showAndWait();
                getAllBookings();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to update booking status ", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void goToHome() throws IOException {
            App.setRoot("home");
        }

}
