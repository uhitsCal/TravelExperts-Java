/**
 * Sample Skeleton for 'products_view.fxml' Controller Class
 */

package com.example.workshop6;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProductsController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btn_add"
    private Button btn_add; // Value injected by FXMLLoader

    @FXML // fx:id="btn_delete"
    private Button btn_delete; // Value injected by FXMLLoader

    @FXML // fx:id="btn_edit"
    private Button btn_edit; // Value injected by FXMLLoader

    @FXML // fx:id="tc_productID"
    private TableColumn<Product, Integer> tc_productID; // Value injected by FXMLLoader

    @FXML // fx:id="tc_productName"
    private TableColumn<Product, String> tc_productName; // Value injected by FXMLLoader

    @FXML // fx:id="tv_main"
    private TableView<Product> tv_main; // Value injected by FXMLLoader

    @FXML // fx:id="txt_prodId"
    private TextField txt_prodId; // Value injected by FXMLLoader

    @FXML // fx:id="txt_prodName"
    private TextField txt_prodName; // Value injected by FXMLLoader

    private ObservableList<Product> prodList = FXCollections.observableArrayList();

    private boolean editMode = false;
    private String originalProductName;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btn_add != null : "fx:id=\"btn_add\" was not injected: check your FXML file 'products_view.fxml'.";
        assert btn_delete != null : "fx:id=\"btn_delete\" was not injected: check your FXML file 'products_view.fxml'.";
        assert btn_edit != null : "fx:id=\"btn_edit\" was not injected: check your FXML file 'products_view.fxml'.";
        assert tc_productID != null : "fx:id=\"tc_productID\" was not injected: check your FXML file 'products_view.fxml'.";
        assert tc_productName != null : "fx:id=\"tc_productName\" was not injected: check your FXML file 'products_view.fxml'.";
        assert tv_main != null : "fx:id=\"tv_main\" was not injected: check your FXML file 'products_view.fxml'.";
        assert txt_prodId != null : "fx:id=\"txt_prodId\" was not injected: check your FXML file 'products_view.fxml'.";
        assert txt_prodName != null : "fx:id=\"txt_prodName\" was not injected: check your FXML file 'products_view.fxml'.";

        // Set up column mappings
        tc_productID.setCellValueFactory(new PropertyValueFactory<>("prodID"));
        tc_productName.setCellValueFactory(new PropertyValueFactory<>("prodName"));

        // Event listener for cell clicks
        tv_main.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product none_selectedCell, Product selectedCell) {
                if (selectedCell != null) {
                    txt_prodId.setText(Integer.toString(selectedCell.getProdID()));
                    txt_prodName.setText(selectedCell.getProdName());
                } else {
                    // clears fields
                    txt_prodName.clear();
                    txt_prodId.clear();
                }
            }
        });

        // Allowing only alphanumeric characters
        txt_prodName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z0-9]*$")) {
                txt_prodName.setText(oldValue);
            }
        });

        // Handler for add button
        btn_add.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (editMode) {
                    String prodName = txt_prodName.getText().trim();
                    if (prodName.isEmpty()) {
                        // Alert the user that the product name is required
                        Alert selectAlert = new Alert(Alert.AlertType.WARNING);
                        selectAlert.setTitle("Product Name Required");
                        selectAlert.setContentText("No product name entered. Do you want to cancel or retry?");
                        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        ButtonType retry = new ButtonType("Retry", ButtonBar.ButtonData.OK_DONE);
                        selectAlert.getButtonTypes().setAll(cancel, retry);

                        Optional<ButtonType> selectResult = selectAlert.showAndWait();
                        if (selectResult.isPresent() && selectResult.get() == retry) {
                            editMode = true; // Retry the action
                            txt_prodName.setDisable(false);
                            txt_prodName.clear();
                            btn_add.setText("Save");
                        } else if (selectResult.isPresent() && selectResult.get() == cancel) {
                            txt_prodName.clear();
                            editMode = false;
                            txt_prodName.setDisable(true);
                            btn_add.setText("Add");
                        }
                    } else {
                        // Confirmation of save
                        Alert confirmSave = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmSave.setTitle("Confirm Save");
                        confirmSave.setContentText("Do you want to save the product?");

                        Optional<ButtonType> result = confirmSave.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            saveProductToDB(prodName);
                            Product newProduct = retrieveProductFromDB();
                            if (newProduct != null) {
                                prodList.add(newProduct);
                            }
                            txt_prodName.clear();
                            editMode = false;
                            txt_prodName.setDisable(true);
                            btn_add.setText("Add");
                        } else {
                            // User clicked Cancel on the confirmation
                            txt_prodName.clear();
                            editMode = false;
                            txt_prodName.setDisable(true);
                            btn_add.setText("Add");
                        }
                    }
                } else {
                    // Enter edit mode for adding a new product
                    editMode = true;
                    txt_prodName.setDisable(false);
                    txt_prodName.clear();
                    btn_add.setText("Save");
                }
            }
        });

        // Delete button handler
        btn_delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Product selectedProd = tv_main.getSelectionModel().getSelectedItem();
                if (selectedProd == null) {
                    // Alert the user to select a product for deletion
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("No Product Selected");
                    alert.setContentText("Please select a product to delete.");
                    alert.showAndWait();
                    return; // Do not proceed if no product is selected
                }

                // Check if the selected product is one of the original products (IDs 1-10)
                int selectedProdID = selectedProd.getProdID();
                if (selectedProdID <= 10) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Cannot Delete Original Product");
                    alert.setContentText("Please select a different product.");
                    alert.showAndWait();
                    return;
                }

                // Confirmation of deletion
                Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDelete.setTitle("Confirm Delete");
                confirmDelete.setHeaderText("Delete Product?");
                confirmDelete.setContentText("Do you want to delete the selected product?");

                Optional<ButtonType> result = confirmDelete.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // User confirmed delete, proceed
                    int prodID = selectedProd.getProdID();

                    // Remove the selected product from the list
                    prodList.remove(selectedProd);

                    // Delete the selected product from the database
                    deleteProductFromDB(prodID);
                }
            }
        });

        // Handler for edit button
        btn_edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!editMode) {
                    Product selectedProd = tv_main.getSelectionModel().getSelectedItem();
                    if (selectedProd == null) {
                        // Alert the user to select a product for editing
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("No Product Selected");
                        alert.setContentText("Please select a product to edit.");
                        alert.showAndWait();
                        return; // Do not proceed if no product is selected
                    }

                    // Check if the selected product is one of the original products
                    int selectedProdID = selectedProd.getProdID();
                    if (selectedProdID <= 10) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("Cannot Edit Original Product");
                        alert.setContentText("Please select a different product.");
                        alert.showAndWait();
                        return;
                    }

                    // Enter edit mode for editing the selected product
                    editMode = true;
                    txt_prodName.setDisable(false);
                    originalProductName = txt_prodName.getText();
                    btn_edit.setText("Save");
                } else {
                    Product selectedProd = tv_main.getSelectionModel().getSelectedItem();
                    if (selectedProd == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("No Product Selected");
                        alert.setContentText("Please select a product to edit.");
                        alert.showAndWait();
                        return; // Do not proceed if no product is selected
                    }

                    // Confirmation of save
                    Alert confirmSave = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmSave.setTitle("Confirm Save");
                    confirmSave.setContentText("Do you want to save the edited product?");

                    Optional<ButtonType> result = confirmSave.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // User confirmed save, proceed
                        int update_prodId = Integer.parseInt(txt_prodId.getText());
                        String update_prodName = txt_prodName.getText();

                        updateProductInDB(update_prodId, update_prodName);

                        // Clear and repopulate the list from the database
                        prodList.clear();
                        ProductsFromDB();

                        txt_prodId.clear();
                        txt_prodName.clear();
                        editMode = false;
                        txt_prodName.setDisable(true);
                        btn_edit.setText("Edit");
                    }
                }
            }
        });


        // Load products from the database
        ProductsFromDB();
        tv_main.setItems(prodList);
    }

    // Load products from the database into the list
    public void ProductsFromDB() {
        try {
            Connection connection = ConnectionManager.getConnection();
            String sql = "SELECT * FROM products";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int prodID = resultSet.getInt(1);
                String prodName = resultSet.getString(2);
                Product product = new Product(prodID, prodName);
                prodList.add(product); // Add the retrieved product to the list
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update a product in the database based on its ID
    private void updateProductInDB(int prodId, String prodName) {
        try {
            Connection connection = ConnectionManager.getConnection();
            String sql = "UPDATE products SET prodName = ? WHERE ProductID = ?"; // Use the correct column name (prodID)
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, prodName);
            preparedStatement.setInt(2, prodId);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save a new product to the database
    private void saveProductToDB(String prodName) {
        try {
            Connection connection = ConnectionManager.getConnection();
            String sql = "INSERT INTO products (ProdName) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, prodName);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve the last added product from the database
    private Product retrieveProductFromDB() {
        Product newProduct = null;
        try {
            Connection connection = ConnectionManager.getConnection();
            String sql = "SELECT * FROM products ORDER BY ProductId DESC LIMIT 1"; // Retrieve the last added product
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int prodID = resultSet.getInt("ProductId");
                String prodName = resultSet.getString("ProdName");
                newProduct = new Product(prodID, prodName); // Create a new Product
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newProduct;
    }

    // Delete a product from the database based on its ID
    private void deleteProductFromDB(int prodId) {
        try {
            Connection connection = ConnectionManager.getConnection();
            String sql = "DELETE FROM products WHERE ProductId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, prodId);
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class ConnectionManager {
        private static final String URL = "jdbc:mysql://localhost:3306/travelexperts";
        private static final String username = "Fred";
        private static final String passwd = "password";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, username, passwd);
        }
    }
}
