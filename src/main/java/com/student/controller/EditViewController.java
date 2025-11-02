package com.student.controller;

import com.student.dao.StudentDAO;
import com.student.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditViewController {
    @FXML private Label titleLabel;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField courseField;
    @FXML private TextField ageField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private Student student;

    public void setStudent(Student student) {
        this.student = student;
        if (student == null) {
            titleLabel.setText("Add New Student");
            clearFields();
        } else {
            titleLabel.setText("Edit Student");
            populateFields();
        }
    }

    private void populateFields() {
        if (student != null) {
            nameField.setText(student.getName());
            emailField.setText(student.getEmail());
            courseField.setText(student.getCourse());
            ageField.setText(String.valueOf(student.getAge()));
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        courseField.clear();
        ageField.clear();
    }

    @FXML
    private void handleSave() {
        if (!validateInput()) {
            return;
        }
        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String course = courseField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            
            if (student == null) {
                Student newStudent = new Student(name, email, course, age);
                StudentDAO.addStudent(newStudent);
                showAlert("Success", "Student added successfully!");
            } else {
                student.setName(name);
                student.setEmail(email);
                student.setCourse(course);
                student.setAge(age);
                StudentDAO.updateStudent(student);
                showAlert("Success", "Student updated successfully!");
            }
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid age (numeric value).");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save student: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a name.");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter an email.");
            return false;
        }
        if (courseField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter a course.");
            return false;
        }
        if (ageField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Please enter an age.");
            return false;
        }
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            if (age <= 0 || age > 150) {
                showAlert("Validation Error", "Please enter a valid age (1-150).");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid age (numeric value).");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

