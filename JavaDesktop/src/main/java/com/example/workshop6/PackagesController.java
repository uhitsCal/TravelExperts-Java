/**
 * Sample Skeleton for 'package_view.fxml' Controller Class
 */

package com.example.workshop6;

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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PackagesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btn1"
    private Button btn1; // Value injected by FXMLLoader

    @FXML // fx:id="btn2"
    private Button btn2; // Value injected by FXMLLoader

    @FXML // fx:id="colPackageId"
    private TableColumn<Package, Integer> colPackageId; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgAgencyCommission"
    private TableColumn<Package, Double> colPkgAgencyCommission; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgBasePrice"
    private TableColumn<Package, Double> colPkgBasePrice; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgDesc"
    private TableColumn<Package, String> colPkgDesc; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgEndDate"
    private TableColumn<Package, String> colPkgEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgName"
    private TableColumn<Package, String> colPkgName; // Value injected by FXMLLoader

    @FXML // fx:id="colPkgStartDate"
    private TableColumn<Package, String> colPkgStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="tfPackageId"
    private TextField tfPackageId; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgAgencyCommission"
    private TextField tfPkgAgencyCommission; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgBasePrice"
    private TextField tfPkgBasePrice; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgDesc"
    private TextArea tfPkgDesc; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgEndDate"
    private TextField tfPkgEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgName"
    private TextField tfPkgName; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgStartDate"
    private TextField tfPkgStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="tvPackage"
    private TableView<Package> tvPackage; // Value injected by FXMLLoader

    private String mode = "tv";

    private Connection con = ConnectionManager.getConnection();
    private Statement stmt = null;
    private ResultSet rs = null;
    ObservableList<Package> data = FXCollections.observableArrayList();



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btn1 != null : "fx:id=\"btn1\" was not injected: check your FXML file 'package_view.fxml'.";
        assert btn2 != null : "fx:id=\"btn2\" was not injected: check your FXML file 'package_view.fxml'.";
        assert colPackageId != null : "fx:id=\"colPackageId\" was not injected: check your FXML file 'package_view.fxml'.";
        assert colPkgAgencyCommission != null : "fx:id=\"colPkgAgencyCommission\" was not injected: check your FXML file 'package_view.fxml'.";
        assert colPkgBasePrice != null : "fx:id=\"colPkgBasePrice\" was not injected: check your FXML file 'package_view.fxml'.";
        assert colPkgDesc != null : "fx:id=\"colPkgDesc\" was not injected: check your FXML file 'package_view.fxml'.";
        assert colPkgEndDate != null : "fx:id=\"colPkgEndDate\" was not injected: check your FXML file 'package_view.fxml'.";
        assert colPkgName != null : "fx:id=\"colPkgName\" was not injected: check your FXML file 'package_view.fxml'.";
        assert colPkgStartDate != null : "fx:id=\"colPkgStartDate\" was not injected: check your FXML file 'package_view.fxml'.";
        assert tfPackageId != null : "fx:id=\"tfPackageId\" was not injected: check your FXML file 'package_view.fxml'.";
        assert tfPkgAgencyCommission != null : "fx:id=\"tfPkgAgencyCommission\" was not injected: check your FXML file 'package_view.fxml'.";
        assert tfPkgBasePrice != null : "fx:id=\"tfPkgBasePrice\" was not injected: check your FXML file 'package_view.fxml'.";
        assert tfPkgDesc != null : "fx:id=\"tfPkgDesc\" was not injected: check your FXML file 'package_view.fxml'.";
        assert tfPkgEndDate != null : "fx:id=\"tfPkgEndDate\" was not injected: check your FXML file 'package_view.fxml'.";
        assert tfPkgName != null : "fx:id=\"tfPkgName\" was not injected: check your FXML file 'package_view.fxml'.";
        assert tfPkgStartDate != null : "fx:id=\"tfPkgStartDate\" was not injected: check your FXML file 'package_view.fxml'.";
        assert tvPackage != null : "fx:id=\"tvPackage\" was not injected: check your FXML file 'package_view.fxml'.";

        colPackageId.setCellValueFactory(new PropertyValueFactory<Package, Integer>("packageId"));
        colPkgName.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgName"));
        colPkgStartDate.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgStartDate"));
        colPkgEndDate.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgEndDate"));
        colPkgDesc.setCellValueFactory(new PropertyValueFactory<Package, String>("pkgDesc"));
        colPkgBasePrice.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgBasePrice"));
        colPkgAgencyCommission.setCellValueFactory(new PropertyValueFactory<Package, Double>("pkgAgencyCommission"));

        tvPackage.setItems(data);
        getPackages();

        tvPackage.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Package>() {
            @Override
            public void changed(ObservableValue<? extends Package> observableValue, Package aPackage, Package t1) {
                if(tvPackage.getSelectionModel().isSelected(tvPackage.getSelectionModel().getSelectedIndex())) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(mode.equals("tv")){
                                showDetails(t1);
                                btn1.setText("Edit");
                                btn2.setText("Delete");
                                btn2.setDisable(false);
                                mode = "tv";
                            }
                            if (mode.equals("void")){
                                tvPackage.setMouseTransparent(true);
                                tvPackage.setFocusTraversable(false);
                            }
                            if (mode.equals("change")){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to SAVE the change",new ButtonType("Yes"),new ButtonType("No"));
                                alert.showAndWait();
                                if(alert.getButtonTypes().equals("Yes")){
                                    saveEditData();
                                    showDetails(t1);
                                }else {
                                    showDetails(t1);
                                }
                            }
                        }
                    });
                }
            }
        });

        btn1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mode.equals("tv")){
                    btn1.setText("Save");
                    btn2.setText("Cancel");
                    btn2.setDisable(false);
                    btn1.setDisable(true);
                    mode = "void";
                    setEdibility(true);
                    detectChange();
                }
                if (mode.equals("change")){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to SAVE the change",new ButtonType("Yes"),new ButtonType("No"));
                    alert.showAndWait();
                    if(alert.getButtonTypes().equals("Yes")){
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION,"Change saved");
                        alert1.show();
                        saveEditData();
                        setEdibility(false);
                    }
                }
            }
        });

        btn2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mode.equals("tv")){
                    deleteData();
                } else if (mode.equals("void")){
                    resetText();
                    mode = "tv";
                } else if (mode.equals("change")){
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to CANCEL the change",new ButtonType("Yes"),new ButtonType("No"));
                    alert2.showAndWait();
                    if(alert2.getResult() == ButtonType.YES){
                        resetText();
                        mode = "tv";
                    }
                }
            }
        });

    }

    private void deleteData() {
        try {
            con = ConnectionManager.getConnection();
            String sql;
            if (mode.equals("tv")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to delete this package?",new ButtonType("YES"),new ButtonType("NO"));
                alert.showAndWait();
                if(alert.getButtonTypes().equals("YES") ) {
                    sql = "DELETE FROM `packages` WHERE packageId=?";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, tfPackageId.getText());
                    resetText();
                }
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void saveEditData() {
        try {
            con = ConnectionManager.getConnection();
            String sql;
            if(mode.equals("change")){
                sql = "UPDATE `packages` SET `pkgName`=?,`pkgStartDate`=?,`pkgEndDate`=?," +
                        "`pkgBasePrice`=?,`pkgAgencyCommission`=?,`pkgDesc`=? WHERE packageId=?";
            }
            else{
                sql = "INSERT INTO `packages`(`pkgName`, `pkgStartDate`, `pkgEndDate`, " +
                        "`pkgBasePrice`, `pkgAgencyCommission`, `pkgDesc`) VALUES (?,?,?,?,?,?)";

            }
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,tfPkgName.getText());
            stmt.setString(2,tfPkgStartDate.getText());
            stmt.setString(3,tfPkgEndDate.getText());
            stmt.setDouble(4,Double.parseDouble(tfPkgBasePrice.getText()));
            stmt.setDouble(5,Double.parseDouble(tfPkgAgencyCommission.getText()));
            stmt.setString(6,tfPkgDesc.getText());
            if(btn1.getText().equals("Save")){
                stmt.setInt(7,Integer.parseInt(tfPackageId.getText()));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showDetails(Package t1) {
        tfPackageId.setText(t1.getPackageId() + "");
        tfPkgName.setText(t1.getPkgName());
        tfPkgStartDate.setText(t1.getPkgStartDate());
        tfPkgEndDate.setText(t1.getPkgEndDate());
        tfPkgBasePrice.setText(t1.getPkgBasePrice()+"");
        tfPkgAgencyCommission.setText(t1.getPkgAgencyCommission()+"");
        tfPkgDesc.setText(t1.getPkgDesc());
    }

    private void getPackages() {
        data.clear();
        try {
            con = ConnectionManager.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM packages");
            while(rs.next()){
                data.add(new Package(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4), rs.getString(5),rs.getDouble(6),
                        rs.getDouble(7)));
            }
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEdibility(Boolean b) {
        tfPkgName.setEditable(b);
        tfPkgStartDate.setEditable(b);
        tfPkgEndDate.setEditable(b);
        tfPkgBasePrice.setEditable(b);
        tfPkgAgencyCommission.setEditable(b);
        tfPkgDesc.setEditable(b);
    }

    public void resetText() {
        tfPackageId.setText("");
        tfPkgName.setText("");
        tfPkgStartDate.setText("");
        tfPkgEndDate.setText("");
        tfPkgBasePrice.setText("");
        tfPkgAgencyCommission.setText("");
        tfPkgDesc.setText("");
        setEdibility(false);
        btn1.setText("Add");
        btn1.setDisable(false);
        btn2.setText("Delete");
        btn2.setDisable(true);
    }

    private void detectChange(){
        tfPkgName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                changeBtnif();
            }
        });
        tfPkgStartDate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                changeBtnif();
            }
        });
        tfPkgEndDate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                changeBtnif();
            }
        });
        tfPkgBasePrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                changeBtnif();
            }
        });
        tfPkgAgencyCommission.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                changeBtnif();
            }
        });
        tfPkgDesc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                changeBtnif();
            }
        });
    }

    private void changeBtnif() {
        btn1.setDisable(false);
        mode = "change";
        tvPackage.getSelectionModel().clearSelection();
    }
}
