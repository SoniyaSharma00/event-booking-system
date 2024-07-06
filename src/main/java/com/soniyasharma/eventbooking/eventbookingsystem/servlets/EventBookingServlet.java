package com.soniyasharma.eventbooking.eventbookingsystem.servlets;

import com.soniyasharma.eventbooking.eventbookingsystem.classes.DB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventBookingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
}
