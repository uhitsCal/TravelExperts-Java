package com.example.workshop6;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.IOException;

public class MainViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button btnCustManager;
    @FXML
    private Button btnBookManager;
    @FXML
    private Button btnProdManager;
    @FXML
    private Button btnPackManager;

    private void openCustomerManager() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewApplication.class.getResource("customer_view.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Window!");
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void openBookManager() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewApplication.class.getResource("booking_view.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Booking Manager");
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void openPackagesManager() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewApplication.class.getResource("package_view.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Package Manager");
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void openProductsManager() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainViewApplication.class.getResource("products_view.fxml"));
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Products Manager");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void initialize(){
        assert btnCustManager != null;
        btnCustManager.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        openCustomerManager();
                    }
                });
            }
        });


        btnBookManager.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        openBookManager();
                    }
                });
            }
        });


        btnPackManager.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        openPackagesManager();
                    }
                });
            }
        });

        btnProdManager.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        openProductsManager();
                    }
                });
            }
        });
    }



}
