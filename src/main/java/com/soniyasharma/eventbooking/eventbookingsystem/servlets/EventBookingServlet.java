package com.soniyasharma.eventbooking.eventbookingsystem.servlets;

import com.soniyasharma.eventbooking.eventbookingsystem.classes.DB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EventBookingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Connection connection = null;

        try {
            connection = DB.getConnection();
            String query = "SELECT b.id, b.name, b.contact_no, b.no_of_reservations, b.event_id, b.status,e.name AS event_name, e.location AS event_location, e.date AS event_date " +
                    "FROM bookings b " +
                    "JOIN events e ON b.event_id = e.id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            JSONArray bookingsArray = new JSONArray();


            JSONObject booking = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String contactNo = resultSet.getString("contact_no");
                int noOfReservations = resultSet.getInt("no_of_reservations");
                int eventId = resultSet.getInt("event_id");
                String eventName = resultSet.getString("event_name");
                String eventLocation = resultSet.getString("event_location");
                String eventDate = resultSet.getString("event_date");
                int status = resultSet.getInt("status");
                booking = new JSONObject();
                booking.put("id", id);
                booking.put("name", name);
                booking.put("contact_no", contactNo);
                booking.put("no_of_reservations", noOfReservations);
                booking.put("event_id", eventId);
                booking.put("event_name", eventName);
                booking.put("event_location", eventLocation);
                booking.put("event_date", eventDate);
                booking.put("status", status);
                bookingsArray.put(booking);

            }


            out.print(bookingsArray.toString());

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\":\"" + e.getMessage() + "\"}");
        } finally {
            DB.closeConnection(connection);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String contactNo = request.getParameter("contact_no");
        String  reservations = request.getParameter("reservations");
        String  eventId = request.getParameter("eventId");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        try{
            connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO bookings(name,contact_no,no_of_reservations,event_id) VALUES(?,?,?,?)");
            statement.setString(1, name);
            statement.setString(2, contactNo);
            statement.setInt(3, Integer.valueOf(reservations));
            statement.setInt(4, Integer.valueOf(eventId));

            int insertData = statement.executeUpdate();
            if (insertData == 1) {
                out.println("event_booking_successful");
            } else {
                out.println("error_booking_event");
            }
        }catch (SQLException e){
            e.printStackTrace();

        }catch (Exception e){

            e.printStackTrace();

        }finally {
            DB.closeConnection(connection);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // Extract form data
        Map<String, String> parameters = new HashMap<>();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] pairs = line.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2) {
                        parameters.put(keyValue[0], java.net.URLDecoder.decode(keyValue[1], "UTF-8"));
                    }
                }
            }
        }

        String bookingId = parameters.get("bookingId");
        String status = parameters.get("status");
        try {

            String sql = "UPDATE bookings SET status = ? WHERE id = ?";
            try (Connection conn = DB.getConnection(); // Assuming you have a Database class to get connection
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.valueOf(status));
                stmt.setInt(2, Integer.valueOf(bookingId));
                int rowsAffected = stmt.executeUpdate();
                System.out.println(response);
                if(rowsAffected > 0)
                {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("booking_status_update_success");
                }else{
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Error updating booking status: ");
                }
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println(e.getMessage());

            response.getWriter().write("Error updating booking status: " + e.getMessage());

        }
    }
}
