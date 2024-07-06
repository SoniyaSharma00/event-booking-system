package com.soniyasharma.eventbooking.eventbookingsystem;

import com.soniyasharma.eventbooking.eventbookingsystem.classes.API;
import com.soniyasharma.eventbooking.eventbookingsystem.classes.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EventController {
    @FXML
    private TableView<Event> tableView;
    @FXML
    private TableColumn<Event, Integer> id;

    @FXML
    private TableColumn<Event, String> name;

    @FXML
    private TableColumn<Event, String> loc;

    @FXML
    private TableColumn<Event, String> date;
    @FXML
    private TextField eventName;

    @FXML
    private TextField eventLocation;

    @FXML
    private TextField eventDate;

    @FXML
    private Label responseLabel;


    private final ObservableList<Event> eventList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        id.setCellValueFactory(new PropertyValueFactory<Event,Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Event,String>("name"));
        loc.setCellValueFactory(new PropertyValueFactory<Event,String >("location"));
        date.setCellValueFactory(new PropertyValueFactory<Event,String>("date"));

        tableView.setItems(eventList);

        // Fetch products and populate the table
        try {
            String json = API.get("http://localhost:8080/eventbookingsystem/events");
            List<Event> events = parseEvents(json);
            eventList.addAll(events);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private List<Event> parseEvents(String json) {
        JSONArray jsonArray = new JSONArray(json);
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Event event = new Event(jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("location"),jsonObject.getString("date"));
            events.add(event);
        }

        return events;
    }
    @FXML
    private void addEvent() {
        String eventName = this.eventName.getText();
        String eventLocation = this.eventLocation.getText();
        String eventDate = this.eventDate.getText();
        try{
            String postData = "name=" + eventName+ "&location="+eventLocation + "&date="+ eventDate;

            String response = API.post("http://localhost:8080/eventbookingsystem/events",postData,"POST");
            if(response.equals("event_added_successfully")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Create Event");
                alert.setHeaderText(null);
                alert.setContentText("Event added successfully");
                alert.showAndWait();
                this.eventName.clear();
                this.eventLocation.clear();
                this.eventDate.clear();
                refreshTable();
            }else{
                responseLabel.setText(response);
                responseLabel.setStyle("-fx-text-fill: red");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseLabel.setText("Error while adding event");
        }

    }

    private void refreshTable() {
        try {
            String json = API.get("http://localhost:8080/eventbookingsystem/events");
            List<Event> events = parseEvents(json);

            // Clear old data and add new data
            eventList.clear();
            eventList.addAll(events);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void goToHome() throws IOException
    {
        App.setRoot("home");
    }

    @FXML
    private void goToBookings() throws IOException
    {
        App.setRoot("bookings");
    }
}
