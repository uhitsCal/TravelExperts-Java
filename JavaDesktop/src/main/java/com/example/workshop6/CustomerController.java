package com.example.workshop6;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

//import java.net.URL;
import java.sql.*;
import java.util.*;

// This class controls all things Customer
public class CustomerController {
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnEdit;
    @FXML
    private ListView<Customer> lvCustomers;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfCity;
    @FXML
    private ComboBox<String> cbProvince;
    @FXML
    private TextField tfPostal;
    @FXML
    private TextField tfCountry;
    @FXML
    private TextField tfHomePhone;
    @FXML
    private TextField tfBusPhone;
    @FXML
    private TextField tfEmail;
    @FXML
    private ComboBox<String> cbAgentId;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    // List to store all customers
    ObservableList<Customer> customersList = FXCollections.observableArrayList();
    // List to store all agents
    ObservableList<String> agentIdList = FXCollections.observableArrayList();

    Customer selectedCustomer;

    String mode; // checking whether a customer is being added or edited. (add/edit)

    private Connection con = ConnectionManager.getConnection();
    private Statement stmt = null;
    private ResultSet rs = null;

    // Method for adding all existing customers into list
    private void getCustomers() {
        customersList.clear();
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Customers");
            while (rs.next()) {
                customersList.add(new Customer(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getInt(12)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Method for adding all existing agent id's into list
    private void displayAvailableAgentIds() {
        cbAgentId.getItems().clear();
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT AgentId FROM Agents");
            while (rs.next()) {
                agentIdList.add(String.valueOf(rs.getInt(1)));
            }
            cbAgentId.setItems(agentIdList);
            cbAgentId.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Display all Canadian province codes (sorted)
    private void displayProvinces() {
        cbProvince.getItems().clear();
        ObservableList<String> provincesList = FXCollections.observableArrayList("AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT");

        cbProvince.setItems(provincesList);
        cbProvince.getSelectionModel().selectFirst();
    }

    // Display a customer information onto form fields
    private void displayCustomerInfo(Customer customer) {
        tfFirstName.setText(customer.getCustFirstName());
        tfLastName.setText(customer.getCustLastName());
        tfAddress.setText(customer.getCustAddress());
        tfCity.setText(customer.getCustCity());
        cbProvince.setValue(customer.getCustProv());
        tfPostal.setText(customer.getCustPostal());
        tfCountry.setText(customer.getCustCountry());
        tfHomePhone.setText(customer.getCustHomePhone());
        tfBusPhone.setText(customer.getCustBusPhone());
        tfEmail.setText(customer.getCustEmail());
        cbAgentId.setValue(String.valueOf(customer.getAgentId()));
    }

    private void clearAllTextfields() {
        tfFirstName.setText("");
        tfLastName.setText("");
        tfAddress.setText("");
        tfCity.setText("");
        tfPostal.setText("");
        tfCountry.setText("");
        tfHomePhone.setText("");
        tfBusPhone.setText("");
        tfEmail.setText("");
    }

    private void disableAllFormFields(boolean b) {
        tfFirstName.setDisable(b);
        tfLastName.setDisable(b);
        tfAddress.setDisable(b);
        tfCity.setDisable(b);
        cbProvince.setDisable(b);
        tfPostal.setDisable(b);
        tfCountry.setDisable(b);
        tfHomePhone.setDisable(b);
        tfBusPhone.setDisable(b);
        tfEmail.setDisable(b);
        cbAgentId.setDisable(b);
    }

    private void resetForm() {
        lvCustomers.getSelectionModel().clearSelection();
        selectedCustomer = null;
        clearAllTextfields();
        disableAllFormFields(true);
        btnSave.setVisible(false);
        btnCancel.setVisible(false);
        btnDelete.setDisable(true);
        getCustomers();
    }

    // Method for updating customers
    // Shows dialog for whether update was successful or not
    private void updateCustomer() {
        try {
            con = ConnectionManager.getConnection();
            String sql = "UPDATE `customers` SET `CustFirstName`=?,`CustLastName`=?,`CustAddress`=?,`CustCity`=?,`CustProv`=?,`CustPostal`=?,`CustCountry`=?,`CustHomePhone`=?,`CustBusPhone`=?,`CustEmail`=?,`AgentId`=? WHERE CustomerId=?";
            PreparedStatement prepStatement = con.prepareStatement(sql);
            String[] validationCheck = validateFields();
            if (Objects.equals(validationCheck[0], "invalid")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("The " + validationCheck[1] + " field is invalid or too long.");
                alert.show();
                return;
            }
            prepStatement.setString(1, tfFirstName.getText());
            prepStatement.setString(2, tfLastName.getText());
            prepStatement.setString(3, tfAddress.getText());
            prepStatement.setString(4, tfCity.getText());
            prepStatement.setString(5, cbProvince.getSelectionModel().getSelectedItem());
            prepStatement.setString(6, tfPostal.getText());
            prepStatement.setString(7, tfCountry.getText());
            prepStatement.setString(8, tfHomePhone.getText());
            prepStatement.setString(9, tfBusPhone.getText());
            prepStatement.setString(10, tfEmail.getText());
            prepStatement.setInt(11, Integer.parseInt(cbAgentId.getSelectionModel().getSelectedItem()));
            prepStatement.setInt(12, selectedCustomer.getCustomerId());
            int affectedRowsCount = prepStatement.executeUpdate();
            if (affectedRowsCount != 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Something went wrong, please try again.");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Save successful.");
                alert.show();
            }
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method for adding customers
    // Shows dialog for whether adding was successful or not
    private void addCustomer() {
        try {
            con = ConnectionManager.getConnection();
            String sql = "INSERT INTO `customers` (`CustFirstName`, `CustLastName`, `CustAddress`, `CustCity`, `CustProv`, `CustPostal`, `CustCountry`, `CustHomePhone`, `CustBusPhone`, `CustEmail`, `AgentId`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prepStatement = con.prepareStatement(sql);

            prepStatement.setString(1, tfFirstName.getText());
            prepStatement.setString(2, tfLastName.getText());
            prepStatement.setString(3, tfAddress.getText());
            prepStatement.setString(4, tfCity.getText());
            prepStatement.setString(5, cbProvince.getSelectionModel().getSelectedItem());
            prepStatement.setString(6, tfPostal.getText());
            prepStatement.setString(7, tfCountry.getText());
            prepStatement.setString(8, tfHomePhone.getText());
            prepStatement.setString(9, tfBusPhone.getText());
            prepStatement.setString(10, tfEmail.getText());
            prepStatement.setInt(11, Integer.parseInt(cbAgentId.getSelectionModel().getSelectedItem()));
            int affectedRowsCount = prepStatement.executeUpdate();
            if (affectedRowsCount != 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Something went wrong, please try again.");
                alert.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Insert successful.");
                alert.show();
            }
            con.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method for deleting all bookings and booking details associated with
    // a customer to be deleted
    private void deleteBookingsWithDetails() {
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT * FROM `bookings` WHERE CustomerId=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, selectedCustomer.getCustomerId());
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Integer> bookingIds = new ArrayList<>();
            if (resultSet.next()) {
                bookingIds.add(resultSet.getInt(1));
            }
            for (int bookingId : bookingIds) {
                String sqlDeleteDetails = "DELETE FROM bookingdetails WHERE BookingId=?";
                PreparedStatement preparedStatement1 = con.prepareStatement(sqlDeleteDetails);
                preparedStatement1.setInt(1, bookingId);
                preparedStatement1.executeUpdate();
            }
            for (int bookingId : bookingIds) {
                String sqlDeleteBookings = "DELETE FROM bookings WHERE BookingId=?";
                PreparedStatement preparedStatement1 = con.prepareStatement(sqlDeleteBookings);
                preparedStatement1.setInt(1, bookingId);
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method for deleting customers
    // Customer's booking/booking details are deleted before customer deletion
    private void deleteCustomer() {
        try {
            con = ConnectionManager.getConnection();
            deleteBookingsWithDetails();
            String sql = "DELETE FROM `customers` WHERE CustomerId=?";
            PreparedStatement prepStatement = con.prepareStatement(sql);
            prepStatement.setInt(1, selectedCustomer.getCustomerId());
            prepStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Basic validation for all fields that check for emptiness and max allowed length
    // if field is found invalid, a length 2 list is returned, containing the "invalid" tag
    // and the name of the invalid field
    private String[] validateFields() {
        String[] invalidField = {"", ""};
        if (tfFirstName.getText().isEmpty() || tfFirstName.getText().length() > 25) {
            invalidField[0] = "invalid";
            invalidField[1] = "First Name";
            return invalidField;
        }
        if (tfLastName.getText().isEmpty() || tfLastName.getText().length() > 25) {
            invalidField[0] = "invalid";
            invalidField[1] = "Last Name";
        }
        if (tfAddress.getText().isEmpty() || tfAddress.getText().length() > 75) {
            invalidField[0] = "invalid";
            invalidField[1] = "Address";
            return invalidField;
        }
        if (tfCity.getText().isEmpty() || tfCity.getText().length() > 50) {
            invalidField[0] = "invalid";
            invalidField[1] = "Address";
            return invalidField;
        }
        if (tfPostal.getText().isEmpty() ||
                !tfPostal.getText().matches("^[a-zA-Z][0-9][a-zA-Z]([- ]?[0-9][a-zA-Z][0-9])?$")) {
            invalidField[0] = "invalid";
            invalidField[1] = "Postal Code";
            return invalidField;
        }
        if (tfCountry.getText().isEmpty() || tfCountry.getText().length() > 25) {
            invalidField[0] = "invalid";
            invalidField[1] = "Country";
            return invalidField;
        }
        if (tfHomePhone.getText().isEmpty() || tfHomePhone.getText().length() > 20) {
            invalidField[0] = "invalid";
            invalidField[1] = "Home Phone";
            return invalidField;
        }
        if (tfBusPhone.getText().isEmpty() || tfBusPhone.getText().length() > 20) {
            invalidField[0] = "invalid";
            invalidField[1] = "Business Phone";
            return invalidField;
        }
        if (tfEmail.getText().isEmpty() || tfEmail.getText().length() > 50) {
            invalidField[0] = "invalid";
            invalidField[1] = "Email";
            return invalidField;
        }
        return invalidField;
    }

    @FXML
    void initialize(){
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'customer-view.fxml'.";
        assert lvCustomers != null : "fx:id=\"lvCustomers\" was not injected: check your FXML file 'customer-view.fxml'.";

        getCustomers();
        lvCustomers.setItems(customersList);
        lvCustomers.setStyle("-fx-font-size: 1.5em ;");

        // Handles events upon customer selection
        // "Delete" button becomes enabled.
        // Selected customer info is displayed
        lvCustomers.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedCustomer = lvCustomers.getSelectionModel().getSelectedItem();
                btnDelete.setDisable(false);
                btnEdit.setDisable(false);
                displayAvailableAgentIds();
                displayProvinces();
                displayCustomerInfo(selectedCustomer);
            }
        });

        // Handles the event for when a new customer is to be added
        // Mode is set to "add" and an empty form field is now editable
        // All necessary dropdown items are inserted
        // Necessary buttons are enabled and unnecessary buttons are disabled to prevent unwanted actions
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mode = "add";
                lvCustomers.getSelectionModel().clearSelection();
                disableAllFormFields(false);
                clearAllTextfields();
                displayProvinces();
                displayAvailableAgentIds();
                btnEdit.setDisable(true);
                btnDelete.setDisable(true);
                btnSave.setVisible(true);
                btnCancel.setVisible(true);
            }
        });

        // Handles the event for when a customer is to be edited
        // Editing is enabled on the autofilled form field of selected customer
        // Necessary buttons are enabled, vice versa
        btnEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mode = "edit";
                disableAllFormFields(false);
                btnSave.setVisible(true);
                btnCancel.setVisible(true);
            }
        });

        // The button for this handler is only visible once Add or Edit button is clicked
        // Saves entered customer data to database after validation. Handles both adding and editing.
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String[] validationCheck = validateFields();
                if (Objects.equals(validationCheck[0], "invalid")) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("The " + validationCheck[1] + " field is invalid or too long.");
                    alert.show();
                    return;
                }
                if (mode.equals("add")) {
                    addCustomer();
                }
                else if (mode.equals("edit")) {
                    updateCustomer();
                }
                resetForm();
            }
        });

        // Handler for Cancel button. Reset window
        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resetForm();
            }
        });

        // Handles deleting of customer. Shows alert window asking for confirmation
        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ButtonType accept = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", accept, cancel);
                alert.setHeaderText("You are attempting to delete the below customer. Do you want to continue?");
                alert.setContentText(selectedCustomer.getCustomerId() + ": " +
                        selectedCustomer.getCustFirstName() + " " + selectedCustomer.getCustLastName());
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get().toString().equals(ButtonType.YES.toString())) {
                    deleteCustomer();
                    System.out.println("Delete successful.");
                    getCustomers();
                    resetForm();
                }
            }
        });
    }
}