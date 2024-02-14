/**
 * Author: Calvin C
 * Date: November 6, 2023
 */

package com.example.workshop6;

import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class BookingCreationController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreateBook"
    private Button btnCreateBook; // Value injected by FXMLLoader

    @FXML // fx:id="cboPackage"
    private ComboBox<Package> cboPackage; // Value injected by FXMLLoader

    @FXML // fx:id="cboTravelClass"
    private ComboBox<Classes> cboTravelClass; // Value injected by FXMLLoader

    @FXML // fx:id="cboTripType"
    private ComboBox<TripType> cboTripType; // Value injected by FXMLLoader

    @FXML // fx:id="dateEnd"
    private DatePicker dateEnd; // Value injected by FXMLLoader

    @FXML // fx:id="dateStart"
    private DatePicker dateStart; // Value injected by FXMLLoader

    @FXML // fx:id="lblCustomer"
    private Label lblCustomer; // Value injected by FXMLLoader

    @FXML // fx:id="txtDesc"
    private TextArea txtDesc; // Value injected by FXMLLoader

    @FXML // fx:id="txtPrice"
    private TextField txtPrice; // Value injected by FXMLLoader

    @FXML // fx:id="txtRegion"
    private TextField txtRegion; // Value injected by FXMLLoader

    @FXML // fx:id="txtTravelCount"
    private TextField txtTravelCount; // Value injected by FXMLLoader

    //method to get the customer name with the customer id and update a label
    private void getCustomer() {
        cusData.clear(); //clear old data
        //connection to DB
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from customers WHERE customerId="+custId);
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
            lblCustomer.setText("Customer: "+cusData.get(0).getCustFirstName()+ " "+ cusData.get(0).getCustLastName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //method to get packages from the db
    private void getPackages() {
        packageData.clear();
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM packages");
            while(rs.next()){
                packageData.add(
                        new Package(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getDouble(6),
                                rs.getDouble(7)));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //method to get classes from the db
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //method to get trip types from the db
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //method to get an updated list of packages from the db
    private void getSelectedPackage(int packageId) {
        packageData.clear();
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM packages WHERE PackageId="+packageId);
            while(rs.next()){
                packageData.add(
                        new Package(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getDouble(6),
                                rs.getDouble(7)));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //declaring variables
    private Stage stage;
    int custId;
    public void setCustId(int selectedID, Stage stage) {
        custId = selectedID; //define customer id from parent stage
        this.stage = stage; //define stage variable as this stage
    }
    private Connection con = null;
    private Statement stmt = null;
    private ResultSet rs = null;

    //arraylist
    ObservableList<Customer> cusData = FXCollections.observableArrayList();
    ObservableList<Package> packageData = FXCollections.observableArrayList();
    ObservableList<TripType> tripData = FXCollections.observableArrayList();
    ObservableList<Classes> classData = FXCollections.observableArrayList();
    ObservableList<Booking> bookNoData = FXCollections.observableArrayList();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    BookingNumberGenerator bng = new BookingNumberGenerator();
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        Platform.runLater(() -> {
            assert btnCreateBook != null : "fx:id=\"btnCreateBook\" was not injected: check your FXML file 'booking_creation_view.fxml'.";assert cboPackage != null : "fx:id=\"cboPackage\" was not injected: check your FXML file 'booking_creation_view.fxml'.";
            assert cboTravelClass != null : "fx:id=\"cboTravelClass\" was not injected: check your FXML file 'booking_creation_view.fxml'.";
            assert cboTripType != null : "fx:id=\"cboTripType\" was not injected: check your FXML file 'booking_creation_view.fxml'.";
            assert dateEnd != null : "fx:id=\"dateEnd\" was not injected: check your FXML file 'booking_creation_view.fxml'.";
            assert dateStart != null : "fx:id=\"dateStart\" was not injected: check your FXML file 'booking_creation_view.fxml'.";
            assert txtDesc != null : "fx:id=\"txtDesc\" was not injected: check your FXML file 'booking_creation_view.fxml'.";
            assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'booking_creation_view.fxml'.";
            assert txtRegion != null : "fx:id=\"txtRegion\" was not injected: check your FXML file 'booking_creation_view.fxml'.";
            assert txtTravelCount != null : "fx:id=\"txtTravelCount\" was not injected: check your FXML file 'booking_creation_view.fxml'.";
            getCustomer(); //get the customerID that was selected
            this.stage = stage;
    });
        //update combo boxes
        getClasses();
        updateCBOClasses();
        getTripTypes();
        updateCBOTrips();
        getPackages();
        updateCBOPackages();
        //generate bookingNo
        String bookNo = bng.generateBookNo();

        //event listener for creating a booking
        btnCreateBook.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //checks validation before proceeding
                if(!validation()) {
                    return;
                } else //when validation is passed
                {
                    //update booking
                    createBooking(bookNo);
                    int newBookId = updateBookingId(bookNo);
                    //update booking details
                    createBookingDetails(newBookId);
                    //update booking listview
                    closeStage(stage); //close window
                }
            }
        });
        //package combo box event listener to populate boxes after selecting specific package
        cboPackage.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Package>() {
            @Override
            public void changed(ObservableValue<? extends Package> observableValue, Package aPackage, Package t1) {
                if (cboPackage.getSelectionModel().selectedItemProperty().isNotNull().getValue()) //populate boxes
                {
                    clearBox(); //clear text boxes
                    //get updated info for packaging
                    getSelectedPackage(cboPackage.getSelectionModel().selectedItemProperty().getValue().getPackageId());
                    txtDesc.setText(packageData.get(0).getPkgDesc());//populate description
                    txtPrice.setText("$"+(packageData.get(0).getPkgBasePrice()+packageData.get(0).getPkgAgencyCommission()));
                    txtRegion.setText(getRegions(cboPackage.getSelectionModel().selectedItemProperty().getValue().getPackageId()));
                }//else nothing happens
            }
        });


    }
    //method to close the stage
    private void closeStage(Stage stage) {
        stage.close();
    }
    //method to find and update the bookingId from the booking No
    private int updateBookingId(String bookNo) {
        bookNoData.clear();
        int bookId = 0;
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM bookings WHERE BookingNo="+"'"+bookNo+"'");
            while(rs.next()) {
                bookNoData.add(
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
            bookId = bookNoData.get(0).getBookingId();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookId;
    }
    //method to create and insert the bookingDetails
    private void createBookingDetails(int newBookId) {
        con = ConnectionManager.getConnection();
        int randomDigits = genRanDigit(3);
        try {
            String sql = "INSERT INTO `bookingdetails` (`ItineraryNo`, `TripStart`, `TripEnd`, `Description`, `Destination`, `BasePrice`, `AgencyCommission`, `BookingId`, `RegionId`, `ClassId`,`FeeId`,`ProductSupplierId`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prepStatement = con.prepareStatement(sql);
            for (int i = 0; i < Integer.parseInt(txtTravelCount.getText());i++) {
                    prepStatement.setDouble(1, randomDigits);
                    prepStatement.setDate(2, Date.valueOf(dateStart.getValue()));
                    prepStatement.setDate(3, Date.valueOf(dateEnd.getValue()));
                    prepStatement.setString(4, txtDesc.getText());
                    prepStatement.setString(5, cboPackage.getSelectionModel().selectedItemProperty().getValue().getPkgName());
                    prepStatement.setDouble(6, cboPackage.getSelectionModel().selectedItemProperty().getValue().getPkgBasePrice());
                    prepStatement.setDouble(7, cboPackage.getSelectionModel().selectedItemProperty().getValue().getPkgAgencyCommission());
                    prepStatement.setInt(8, newBookId);
                    prepStatement.setString(9, txtRegion.getText());
                    prepStatement.setString(10, cboTravelClass.getSelectionModel().selectedItemProperty().getValue().getClassId());
                    prepStatement.setString(11, null);
                    prepStatement.setInt(12, 0);
                int affectedRowsCount = prepStatement.executeUpdate();
                if (affectedRowsCount != 1) {
                    System.out.println("Something went wrong.");
                }
                else {
                    System.out.println("Insert into bookingdetails successful.");
                }
            }

            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //method to randomly generate a number
    private int genRanDigit(int num) {
        Random random = new Random();
        int min= (int) Math.pow(10, num-1);
        int max= (int) Math.pow(10,num-1);
        return random.nextInt(max-min+1)+min;
    }
    //method to create and insert a booking
    private void createBooking(String bookNo) {
        con = ConnectionManager.getConnection();
        LocalDate currentDate = LocalDate.now();

        try {
        String sql = "INSERT INTO `bookings` (`BookingDate`, `BookingNo`, `TravelerCount`, `CustomerId`, `TripTypeId`, `PackageId`) VALUES (?,?,?,?,?,?)";
        PreparedStatement prepStatement = con.prepareStatement(sql);
        prepStatement.setDate(1, Date.valueOf(currentDate));
        prepStatement.setString(2, bookNo);
        prepStatement.setDouble(3, Double.parseDouble(txtTravelCount.getText()));
        prepStatement.setInt(4, custId);
        prepStatement.setString(5, cboTripType.getSelectionModel().getSelectedItem().getTriptypeId());
        prepStatement.setInt(6, Integer.parseInt(cboPackage.getSelectionModel().getSelectedItem().getPackageId() + ""));
        int affectedRowsCount = prepStatement.executeUpdate();
        if (affectedRowsCount != 1) {
            System.out.println("Something went wrong.");
        }
        else {
            System.out.println("Insert into bookings successful.");
        }
        con.close();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
    //method to get packageid and assign it a region
    private String getRegions(int packageId) {
        String region = null;
        if (packageId == 1) {
            region = "NA";
        } else if (packageId == 2) {
            region = "NA";
        } else if (packageId == 3) {
            region = "ASIA";
        } else if (packageId == 4) {
            region = "EU";
        } else {
            region = "Not Available";
        }
        return region;
    }

    //method to clear all the text boxes
    private void clearBox() {
        txtDesc.setText("");
        txtPrice.setText("");
        txtRegion.setText("");
    }
    //method for validating all the inputs
    private Boolean validation() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = dateStart.getValue();
        LocalDate endDate = dateEnd.getValue();
        String text = txtTravelCount.getText();
        Pattern pattern = Pattern.compile(".*[a-zA-Z].*");

        if (startDate != null && startDate.isBefore(currentDate)) {
            dateStart.setValue(currentDate);
            dateStart.requestFocus();
            showErrorDialog("Start date cannot be in the past");
            return false;
        } else if (endDate != null && endDate.isBefore(currentDate)) {
            dateEnd.setValue(currentDate);
            dateEnd.requestFocus();
            showErrorDialog("End Date cannot be in the past.");
            return false;
        } else if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            dateStart.setValue(null);
            dateStart.requestFocus();
            showErrorDialog("Start date cannot be after end Date");
            return false;
        } else if (startDate == null || endDate == null){
            dateStart.requestFocus();
            showErrorDialog("Please pick a start and end date");
            return false;
        } else if (text == null || text.isEmpty()) {
            txtTravelCount.requestFocus();
            showErrorDialog("Please enter the amount of travelers");
            return false;
        } else if (pattern.matcher(text).find()){
            txtTravelCount.requestFocus();
            showErrorDialog("Please enter a valid number of travelers");
            return false;
        } else if (cboPackage.getSelectionModel().selectedItemProperty().getValue() == null || cboPackage.getSelectionModel().isEmpty()){
            cboPackage.requestFocus();
            showErrorDialog("Please select a Package");
            return false;
        } else if (cboTravelClass.getSelectionModel().selectedItemProperty().getValue() == null || cboTravelClass.getSelectionModel().isEmpty()){
            cboTravelClass.requestFocus();
            showErrorDialog("Please select a Travel Class");
            return false;
        } else if (cboTripType.getSelectionModel().selectedItemProperty().getValue() == null || cboTripType.getSelectionModel().isEmpty()) {
            cboTripType.requestFocus();
            showErrorDialog("Please select a type of trip");
            return false;
        }
        return true;
    }
    //method to show a error dialog
    private void showErrorDialog(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    //method to update classes combo box
    private void updateCBOClasses() {
        cboTravelClass.getItems().setAll(classData); //add cusData objects to combo box
        cboTravelClass.setConverter(new StringConverter<Classes>() {
            @Override
            public String toString(Classes classes) {
                if(classes != null) {
                    return classes.getClassName();
                } else
                {
                    return "Select a Travel Class";
                }
            }
            @Override
            public Classes fromString(String s) {
                return null;
            }
        });

    }
    //method to update the packages combo box
    private void updateCBOPackages() {
        cboPackage.getItems().setAll(packageData); //add cusData objects to combo box
        cboPackage.setConverter(new StringConverter<Package>() {
            @Override
            public String toString(Package packages) {
                if(packages != null) {
                    return packages.getPkgName();
                } else
                {
                    return "Select a package";
                }
            }
            @Override
            public Package fromString(String s) {
                return null;
            }
        });
    }
    //method to update trip types combo box
    private void updateCBOTrips() {
        cboTripType.getItems().setAll(tripData); //add cusData objects to combo box
        cboTripType.setConverter(new StringConverter<TripType>() {
            @Override
            public String toString(TripType tripType) {
                if(tripType != null) {
                    return tripType.getTriptypeName();
                } else
                {
                    return "Select the trip type";
                }
            }

            @Override
            public TripType fromString(String s) {
                return null;
            }
        });
    }



}
