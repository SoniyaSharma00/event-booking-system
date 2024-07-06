package com.soniyasharma.eventbooking.eventbookingsystem.servlets;

import com.soniyasharma.eventbooking.eventbookingsystem.classes.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        try{
            connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM events");
            ResultSet resultSet = statement.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                JSONObject events = new JSONObject();
                events.put("id", resultSet.getInt("id"));
                events.put("name", resultSet.getString("name"));
                events.put("location", resultSet.getString("location"));
                events.put("date", resultSet.getString("date"));
                jsonArray.put(events);
            }

            out.print(jsonArray.toString());
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){

            e.printStackTrace();
        }finally {
            DB.closeConnection(connection);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        String  date = request.getParameter("date");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        try{
            connection = DB.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO events(name,location,date) VALUES(?,?,?)");
            statement.setString(1, name);
            statement.setString(2, location);
            statement.setString(3, date);

            int insertData = statement.executeUpdate();
            if (insertData == 1) {
                out.println("event_added_successfully");
            } else {
                out.println("error_while_adding_event");
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
