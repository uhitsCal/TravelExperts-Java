/**
 * Author: Calvin C
 * Date: November 6, 2023
 */

package com.example.workshop6;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class BookingsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnBook"
    private Button btnBook; // Value injected by FXMLLoader
    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnDetails"
    private Button btnDetails; // Value injected by FXMLLoader

    @FXML // fx:id="cboCustomer"
    private ComboBox<Customer> cboCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="lbl1"
    private Label lbl1; // Value injected by FXMLLoader

    @FXML // fx:id="lbl2"
    private Label lbl2; // Value injected by FXMLLoader

    @FXML // fx:id="lbl3"
    private Label lbl3; // Value injected by FXMLLoader

    @FXML // fx:id="lbl4"
    private Label lbl4; // Value injected by FXMLLoader

    @FXML // fx:id="lbl5"
    private Label lbl5; // Value injected by FXMLLoader

    @FXML // fx:id="lbl6"
    private Label lbl6; // Value injected by FXMLLoader

    @FXML // fx:id="lvBookings"
    private ListView<Booking> lvBookings; // Value injected by FXMLLoader

    @FXML // fx:id="txtBookID"
    private TextField txtBookID; // Value injected by FXMLLoader

    @FXML // fx:id="txtbox1"
    private TextField txtbox1; // Value injected by FXMLLoader

    @FXML // fx:id="txtbox2"
    private TextField txtbox2; // Value injected by FXMLLoader

    @FXML // fx:id="txtbox3"
    private TextField txtbox3; // Value injected by FXMLLoader

    @FXML // fx:id="txtbox4"
    private TextField txtbox4; // Value injected by FXMLLoader

    @FXML // fx:id="txtbox5"
    private TextField txtbox5; // Value injected by FXMLLoader

    @FXML // fx:id="txtbox6"
    private TextField txtbox6; // Value injected by FXMLLoader

    //Adjusting the appearances
    private void disableModifyBooking() {
        btnBook.setVisible(false);
        btnDelete.setVisible(false);
    }
    private void disableEdit() {
        txtBookID.setEditable(false);
        txtbox1.setEditable(false);
        txtbox2.setEditable(false);
        txtbox3.setEditable(false);
        txtbox4.setEditable(false);
        txtbox5.setEditable(false);
        txtbox6.setEditable(false);
    }
    private void clearBox() {
        txtBookID.setText("");
        txtbox1.setText("");
        txtbox2.setText("");
        txtbox3.setText("");
        txtbox4.setText("");
        txtbox5.setText("");
        txtbox6.setText("");
    }
    private void setDetailview() {
        lbl1.setText("Trip Start: ");
        lbl2.setText("Trip End: ");
        lbl3.setText("Destination: ");
        lbl4.setText("Region: ");
        lbl5.setText("Base Price: ");
        lbl6.setText("Travel Class: ");
        lbl5.setVisible(true);
        lbl6.setVisible(true);
        txtbox5.setVisible(true);
        txtbox6.setVisible(true);
        btnCancel.setVisible(false);
        btnDetails.setVisible(false);
        txtBookID.setEditable(false);
    }
    private void setDefaultView() {
        lbl1.setText("Booking Date: ");
        lbl2.setText("Booking No: ");
        lbl3.setText("Travel Count: ");
        lbl4.setText("Trip Type: ");
        lbl5.setText("");
        lbl6.setText("");
        lbl5.setVisible(false);
        lbl6.setVisible(false);
        txtbox5.setVisible(false);
        txtbox6.setVisible(false);
        btnCancel.setVisible(false);
        btnDetails.setVisible(false);
        txtBookID.setEditable(false);
    }
    //Creating the data arraylist
    ObservableList<Customer> cusData = FXCollections.observableArrayList();
    ObservableList<Booking> bookData = FXCollections.observableArrayList();
    ObservableList<BookingDetails> bookDetailsData = FXCollections.observableArrayList();
    ObservableList<Classes> classData = FXCollections.observableArrayList();
    ObservableList<TripType> tripData = FXCollections.observableArrayList();

    //pulling the data from the db into the arraylist
    private void getTripTypes() {
        tripData.clear();
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from triptypes");
            while(rs.next()){
                tripData.add(new TripType(
                        rs.getString(1),
                        rs.getString(2)));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getBookings(int custId) {
        bookData.clear(); //clear old data
        //connect to db
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from bookings where CustomerId="+custId);
            while(rs.next()){
                bookData.add(
                        new Booking(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getInt(4),
                                rs.getInt(5),
                                rs.getString(6),
                                rs.getInt(7)
                        ));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getCustomers() {
        cusData.clear(); //clear old data
        //connection to DB
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from customers");
            while(rs.next()){
                cusData.add(
                        new Customer(
                                rs.getInt(1),
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
                                rs.getInt(12)
                        )
                );
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getBookingDetail(int bookId) {
        bookDetailsData.clear();
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from bookingdetails where BookingId="+bookId);
            while(rs.next()){
                bookDetailsData.add(
                        new BookingDetails(
                                rs.getInt(1),
                                rs.getInt(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getInt(7),
                                rs.getDouble(8),
                                rs.getInt(9),
                                rs.getString(10),
                                rs.getString(11),
                                rs.getString(12),
                                rs.getInt(13)
                        ));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getClasses() {
        classData.clear();
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from classes");
            while(rs.next()){
                classData.add(
                        new Classes(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3)
                        ));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //declaring the connection to db
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnBook != null : "fx:id=\"btnBook\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert btnDetails != null : "fx:id=\"btnDetails\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert cboCustomer != null : "fx:id=\"cboCustomer\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert lbl1 != null : "fx:id=\"lbl1\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert lbl2 != null : "fx:id=\"lbl2\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert lbl3 != null : "fx:id=\"lbl3\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert lbl4 != null : "fx:id=\"lbl4\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert lbl5 != null : "fx:id=\"lbl5\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert lbl6 != null : "fx:id=\"lbl6\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert lvBookings != null : "fx:id=\"lvBookings\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert txtBookID != null : "fx:id=\"txtBookID\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert txtbox1 != null : "fx:id=\"txtbox1\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert txtbox2 != null : "fx:id=\"txtbox2\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert txtbox3 != null : "fx:id=\"txtbox3\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert txtbox4 != null : "fx:id=\"txtbox4\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert txtbox5 != null : "fx:id=\"txtbox5\" was not injected: check your FXML file 'booking_view.fxml'.";
        assert txtbox6 != null : "fx:id=\"txtbox6\" was not injected: check your FXML file 'booking_view.fxml'.";
        setDefaultView(); //set the view up to default
        getCustomers();//get updated customers from db
        updateCboCustomer();//populate combo box
        disableEdit();//disable the edits
        disableModifyBooking();

        //event to display booking information based on customer selected
        cboCustomer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observableValue, Customer customer, Customer t1) {
                //if selection is not null (Customer Selected)
                if (cboCustomer.getSelectionModel().selectedItemProperty().isNotNull().getValue()) //populate the text box
                {
                    clearBox(); //clear text boxes
                    setDefaultView();
                    //get updated bookings from db based on customer id
                    getBookings(cboCustomer.getSelectionModel().selectedItemProperty().getValue().getCustomerId());
                    lvBookings.setItems(bookData); //populate the listview
                    btnBook.setVisible(true); //enable booking creation
                    btnDelete.setVisible(false); //disable deleting (until booking selected)
                }//else nothing happens
            }
        });

        //event to display values based on selected booking
        lvBookings.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Booking>() {
            @Override
            public void changed(ObservableValue<? extends Booking> observableValue, Booking booking, Booking t1) {
                if (lvBookings.getSelectionModel().selectedItemProperty().isNotNull().getValue()) {
                    clearBox(); //clear text boxes
                    setDefaultView();
                    //set text boxes
                    txtBookID.setText(t1.getBookingId()+"");
                    txtbox1.setText(t1.getBookingDate().replaceAll("\\s.*",""));
                    txtbox2.setText(t1.getBookingNo());
                    txtbox3.setText(t1.getTravelerCount()+"");
                    txtbox4.setText(findTripTypes(t1));
                    btnDetails.setVisible(true); //enable viewing details
                    btnDelete.setVisible(true); //enable deleting of selected booking
                }
            }
        });
        //event to display details of specific booking
        btnDetails.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //get data from db
                getBookingDetail(lvBookings.getSelectionModel().selectedItemProperty().getValue().getBookingId());
                try {
                    clearBox();
                    setDetailview();
                    //fill text box
                    txtBookID.setText(bookDetailsData.get(0).getBookId() + "");
                    txtbox1.setText(bookDetailsData.get(0).getTripStart().replaceAll("\\s.*", ""));
                    txtbox2.setText(bookDetailsData.get(0).getTripEnd().replaceAll("\\s.*", ""));
                    txtbox3.setText(bookDetailsData.get(0).getDestination());
                    txtbox4.setText(bookDetailsData.get(0).getRegionId());
                    txtbox5.setText("$"+(bookDetailsData.get(0).getBasePrice()+bookDetailsData.get(0).getAgencyCommission()));
                    txtbox6.setText(findClass(bookDetailsData.get(0).getClassId()));
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("There are no information related to this booking");
                    alert.showAndWait();
                    setDefaultView();
                }
            }
        });
        //event to delete selected booking id
        btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int selectedBookingId = lvBookings.getSelectionModel().selectedItemProperty().getValue().getBookingId();
                //Alert pop up to confirm deletion of specific BookingId
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete the Booking " + selectedBookingId + "?");
                alert.showAndWait();
                //if its not cancel button then delete
                if (!alert.resultProperty().getValue().getButtonData().isCancelButton()){
                    deleteBooking(selectedBookingId);
                    System.out.println(alert.resultProperty().getValue());
                    updateBookingListView();
                }

            }
        });
        //event to open a new creation window
        btnBook.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        openBookCreation();
                    }
                });

            }
        });
    }

    //Method to update the booking list view
    private void updateBookingListView() {
        getBookings(cboCustomer.getSelectionModel().selectedItemProperty().getValue().getCustomerId());
        lvBookings.setItems(bookData); //populate the listview
        setDefaultView();
        clearBox();
    }

    //method to delete selected bookingId in bookings and bookingdetails table
    private void deleteBooking(int bookingId) {
        //verify booking details exist
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT * FROM bookingdetails WHERE BookingId=?";
            PreparedStatement prepStatement = con.prepareStatement(sql);
            prepStatement.setInt(1, bookingId);
            rs = prepStatement.executeQuery();
            if (rs.next()) { //it exist
                //delete bookingdetails
                String sqlDelete = "DELETE FROM bookingdetails WHERE BookingId=?";
                PreparedStatement prepDeleteStatement = con.prepareStatement(sqlDelete);
                prepDeleteStatement.setInt(1, bookingId);
                int rowCount = prepDeleteStatement.executeUpdate();
                if (rowCount != 0) {
                    System.out.println("delete bookingdetails successful");
                } else {
                    System.out.println("delete failed");
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT * FROM bookings WHERE BookingId=?";
            PreparedStatement prepStatement = con.prepareStatement(sql);
            prepStatement.setInt(1, bookingId);
            rs = prepStatement.executeQuery();
            if (rs.next()) { //it exist
                //delete booking
                String sqlDelete = "DELETE FROM bookings WHERE BookingId=?";
                PreparedStatement prepDeleteStatement = con.prepareStatement(sqlDelete);
                prepDeleteStatement.setInt(1, bookingId);
                int rowCount = prepDeleteStatement.executeUpdate();
                if (rowCount != 0) {
                    System.out.println("delete booking successful");
                } else {
                    System.out.println("delete failed");
                }
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //method to take the selected customer id and open a new window for creating a new booking
    private void openBookCreation() {
        int id = cboCustomer.getSelectionModel().selectedItemProperty().getValue().getCustomerId();
        Stage stage = new Stage();
        BookingCreationController controller = new BookingCreationController();
        controller.setCustId(id, stage);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("booking_creation_view.fxml"));
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            // Show the "Booking Creation" window
            stage.showAndWait();

            updateBookingListView();
        } catch (IOException e) {
            // Handle any exceptions related to loading the FXML
            e.printStackTrace();
        }
    }
    //Method to find the class per booking selection and return the name
    private String findClass(String classId) {
        String className = "Not available"; //return message
        getClasses(); //get classes db
        //loop through the array list and match the selected id with the id in the database
        for(int i = 0; i<classData.toArray().length; i++)
        {
            if (classId.equals(classData.get(i).getClassId())){ //when it matches
                className = classData.get(i).getClassName();
                return className; //return the class name based on the id
            }
        }
        return className;
    }
    //Method to find the type of trip per booking selection and return the name
    private String findTripTypes(Booking t1) {
        String type = "Not available";
        getTripTypes(); //get updated triptypes from db
        for (int i = 0; i<tripData.toArray().length; i++) {
            if (t1.getTripTypeId().equals(tripData.get(i).getTriptypeId()))
            {
                type = tripData.get(i).getTriptypeName();
                return type;
            }
        }
        return type;
    }

    //Method to update the Customer combo box
    private void updateCboCustomer() {
        cboCustomer.getItems().setAll(cusData); //add cusData objects to combo box
        cboCustomer.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                if(customer != null) {
                    return customer.getCustFirstName() + " " + customer.getCustLastName();
                } else
                {
                    return "Select a customer";
                }
            }
            @Override
            public Customer fromString(String s) {
                return null;
            }
        });
    }




}
