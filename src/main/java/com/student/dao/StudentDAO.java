package com.student.dao;

import com.student.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "leela@8688";

    public static ObservableList<Student> getAllStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            System.out.println("Attempting to connect to database...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully");
            
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✅ Database connection successful!");
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM students ORDER BY id");
            System.out.println("Executing query: SELECT * FROM students");
            
            int count = 0;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String course = resultSet.getString("course");
                int age = resultSet.getInt("age");
                students.add(new Student(id, name, email, course, age));
                count++;
                System.out.println("Loaded student: " + name);
            }
            System.out.println("Total students loaded: " + count);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Database error: " + e.getMessage());
            System.err.println("❌ Error code: " + e.getErrorCode());
            System.err.println("❌ SQL State: " + e.getSQLState());
            e.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) {}
            try { if (statement != null) statement.close(); } catch (SQLException e) {}
            try { if (connection != null) connection.close(); } catch (SQLException e) {}
        }
        return students;
    }

    public static void addStudent(Student student) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String query = "INSERT INTO students (name, email, course, age) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getCourse());
            statement.setInt(4, student.getAge());
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException e) {}
            try { if (connection != null) connection.close(); } catch (SQLException e) {}
        }
    }

    public static void updateStudent(Student student) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String query = "UPDATE students SET name = ?, email = ?, course = ?, age = ? WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getCourse());
            statement.setInt(4, student.getAge());
            statement.setInt(5, student.getId());
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException e) {}
            try { if (connection != null) connection.close(); } catch (SQLException e) {}
        }
    }

    public static void deleteStudent(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String query = "DELETE FROM students WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException e) {}
            try { if (connection != null) connection.close(); } catch (SQLException e) {}
        }
    }
}

