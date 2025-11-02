package com.student.controller;

import com.student.dao.StudentDAO;
import com.student.model.Student;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewController {
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> emailColumn;
    @FXML private TableColumn<Student, String> courseColumn;
    @FXML private TableColumn<Student, Integer> ageColumn;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        refreshTable();
    }

    private void refreshTable() {
        try {
            System.out.println("Starting to refresh table...");
            ObservableList<Student> students = StudentDAO.getAllStudents();
            System.out.println("Loaded " + students.size() + " students from database");
            
            javafx.application.Platform.runLater(() -> {
                try {
                    studentTableView.getItems().clear();
                    studentTableView.setItems(students);
                    studentTableView.refresh();
                    System.out.println("Table updated with " + students.size() + " students");
                    
                    if (students.isEmpty()) {
                        System.out.println("WARNING: No students found in database!");
                        showAlert("Info", "No students found. Please add a student or check database connection.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to update table: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            javafx.application.Platform.runLater(() -> {
                showAlert("Database Error", "Cannot connect to database: " + e.getMessage() + 
                    "\n\nPlease check:\n1. MySQL server is running\n2. Database 'studentdb' exists\n3. Password is correct");
            });
        }
    }

    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/student/view/EditView.fxml"));
            Parent root = loader.load();
            EditViewController controller = loader.getController();
            controller.setStudent(null);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Student");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            // Refresh after dialog closes
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open Add window: " + e.getMessage());
        }
    }

    @FXML
    private void handleEdit() {
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert("No Selection", "Please select a student to edit.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/student/view/EditView.fxml"));
            Parent root = loader.load();
            EditViewController controller = loader.getController();
            controller.setStudent(selectedStudent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            // Refresh after dialog closes
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open Edit window: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Student selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert("No Selection", "Please select a student to delete.");
            return;
        }
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Student");
        confirmAlert.setContentText("Are you sure you want to delete " + selectedStudent.getName() + "?");
        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            StudentDAO.deleteStudent(selectedStudent.getId());
            showAlert("Success", "Student deleted successfully!");
            refreshTable();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

