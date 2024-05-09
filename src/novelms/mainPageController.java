/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package novelms;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author USER
 */
public class mainPageController implements Initializable {

    @FXML
    private Hyperlink dashboard;

    @FXML
    private Hyperlink employee;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Hyperlink order;

    @FXML
    private AnchorPane orderForm;

    @FXML
    private Hyperlink product;

    @FXML
    private AnchorPane productForm;

    @FXML
    private Button product_addBtn;

    @FXML
    private Button product_clearBtn;

    @FXML
    private TableColumn<productData, String> product_col_ProductID;

    @FXML
    private TableColumn<productData, String> product_col_ProductName;

    @FXML
    private TableColumn<productData, String> product_col_date;

    @FXML
    private TableColumn<productData, String> product_col_price;

    @FXML
    private TableColumn<productData, String> product_col_status;

    @FXML
    private TableColumn<productData, String> product_col_stock;

    @FXML
    private TableColumn<productData, String> product_col_type;

    @FXML
    private Button product_deleteBtn;

    @FXML
    private ImageView product_imageView;

    @FXML
    private Button product_importBtn;

    @FXML
    private TextField product_price;

    @FXML
    private TextField product_productID;

    @FXML
    private TextField product_productName;

    @FXML
    private ComboBox<?> product_status;

    @FXML
    private TextField product_stock;

    @FXML
    private TableView<productData> product_tableView;

    @FXML
    private ComboBox<?> product_type;

    @FXML
    private Button product_updateBtn;

    @FXML
    private Hyperlink purchase;

    @FXML
    private Hyperlink report;

    @FXML
    private ComboBox<String> searchCombo;

    @FXML
    private TextField confirmedby;

    @FXML
    private TextField custAddress;

    @FXML
    private TextField custEmail;

    @FXML
    private TextField custName;

    @FXML
    private TextField custNo;

    @FXML
    private ComboBox<?> paytype;

    @FXML
    private TextField staffName;

    @FXML
    private ComboBox<?> staffUnit;

    @FXML
    private TextField totalpay;

    @FXML
    private TextField salesComment;

    @FXML
    private TextField searchProduct;

    @FXML
    private ListView<String> productlistview;

    @FXML
    private AnchorPane product1;

    @FXML
    private Label order_name;

    @FXML
    private Label order_status;

    @FXML
    private TextField order_price;

    private Alert alert;
    private Image image;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void orderAddBtn() {
        if (custName.getText().isEmpty()
                || custNo.getText().isEmpty()
                || custEmail.getText().isEmpty()
                || custAddress.getText().isEmpty()
                || paytype.getSelectionModel().getSelectedItem() == null
                || confirmedby.getText().isEmpty()
                || totalpay.getText().isEmpty()
                || staffName.getText().isEmpty()
                || staffUnit.getSelectionModel().getSelectedItem() == null
                || salesComment.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String placeOrder = "INSERT INTO sales_order "
                    + "(cust_name, cust_no, cust_email, address, payment_type, confirmby,"
                    + " total_payment, staff_name, staff_unit, sales_comment, date) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

            try {

                connect = database.connectDB();
                prepare = connect.prepareStatement(placeOrder);

                prepare = connect.prepareStatement(placeOrder);
                prepare.setString(1, custName.getText());
                prepare.setString(2, custNo.getText());
                prepare.setString(3, custEmail.getText());
                prepare.setString(4, custAddress.getText());
                prepare.setString(5, (String) paytype.getSelectionModel().getSelectedItem());
                prepare.setString(6, confirmedby.getText());
                prepare.setString(7, totalpay.getText());
                prepare.setString(8, staffName.getText());
                prepare.setString(9, (String) staffUnit.getSelectionModel().getSelectedItem());
                prepare.setString(10, salesComment.getText());

                //TO GET CURRENT DATE
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(11, String.valueOf(sqlDate));

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added");
                alert.showAndWait();

                orderPayTypeList();
                staffUnitList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void productAddBtn() {

        if (product_productID.getText().isEmpty()
                || product_productName.getText().isEmpty()
                || product_type.getSelectionModel().getSelectedItem() == null
                || product_stock.getText().isEmpty()
                || product_price.getText().isEmpty()
                || product_status.getSelectionModel().getSelectedItem() == null
                || data.path == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            //CHECK PRODUCT ID
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
                    + product_productID.getText() + "'";

            connect = database.connectDB();

            try {

                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(product_productID.getText() + "is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO product "
                            + "(prod_id, prod_name, type, stock, price, status, image, date) "
                            + "VALUES(?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, product_productID.getText());
                    prepare.setString(2, product_productName.getText());
                    prepare.setString(3, (String) product_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, product_stock.getText());
                    prepare.setString(5, product_price.getText());
                    prepare.setString(6, (String) product_status.getSelectionModel().getSelectedItem());

                    String path = data.path;
                    path = path.replace("\\", "\\\\");

                    prepare.setString(7, path);

                    //TO GET CURRENT DATE
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added");
                    alert.showAndWait();

                    productShowData();
                    productClearBtn();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void productUpdateBtn() {

        if (product_productID.getText().isEmpty()
                || product_productName.getText().isEmpty()
                || product_type.getSelectionModel().getSelectedItem() == null
                || product_stock.getText().isEmpty()
                || product_price.getText().isEmpty()
                || product_status.getSelectionModel().getSelectedItem() == null
                || data.path == null
                || data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            String path = data.path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE product SET "
                    + "prod_id = '" + product_productID.getText() + "', prod_name = '"
                    + product_productName.getText() + "', type = '"
                    + product_type.getSelectionModel().getSelectedItem() + "', stock = '"
                    + product_stock.getText() + "', price = '"
                    + product_price.getText() + "', status = '"
                    + product_status.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + data.date + "' WHERE id = " + data.id;

            connect = database.connectDB();

            try {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID: " + product_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succeefully Updated!");
                    alert.showAndWait();

                    //TO UPDATE YOUR TABLE VIEW
                    productShowData();
                    //TO CLEAR YOUR FIELDS
                    productClearBtn();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void productDeleteBtn() {

        if (data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product ID: " + product_productID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM product WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    //TO UPDATE YOUR TABLE VIEW
                    productShowData();
                    //TO CLEAR YOUR FIELDS
                    productClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled.");
                alert.showAndWait();
            }

        }

    }

    public void productClearBtn() {

        product_productID.setText("");
        product_productName.setText("");
        product_type.getSelectionModel().clearSelection();
        product_stock.setText("");
        product_price.setText("");
        product_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        product_imageView.setImage(null);

    }

    //LET'S MAKE A BEHAVIOR FOR IMPORT BTN FIRST
    public void productImportBtn() {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 150, 155, false, true);

            product_imageView.setImage(image);
        }

    }

    public void productSelectData() {

        productData prodData = product_tableView.getSelectionModel().getSelectedItem();
        int num = product_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        product_productID.setText(prodData.getProductId());
        product_productName.setText(prodData.getProductName());
        product_stock.setText(String.valueOf(prodData.getStock()));
        product_price.setText(String.valueOf(prodData.getPrice()));

        data.path = prodData.getImage();

        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();

        image = new Image(path, 150, 155, false, true);

        product_imageView.setImage(image);
    }

    //MERGE ALL DATAS
    public ObservableList<productData> productDataList() {

        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product";

        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prodData;

            while (result.next()) {
                prodData = new productData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prodData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    //TO SHOW DATA ON OUR TABLE
    private ObservableList<productData> productListData;

    public void productShowData() {
        productListData = productDataList();

        product_col_ProductID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        product_col_ProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        product_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        product_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        product_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        product_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        product_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        product_tableView.setItems(productListData);
    }

    private String[] typeList = {"Batteries", "Inverter", "Panels", "Controller", "Kits", "Surge", "Cable", "Electrolytes"};

    public void productTypeList() {

        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        product_type.setItems(listData);
    }

    private String[] statusList = {"In Stock", "Out of Stock"};

    public void productStatusList() {

        List<String> statusL = new ArrayList<>();

        for (String data : statusList) {
            statusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(statusL);
        product_status.setItems(listData);
    }

    private String[] paytypeList = {"Transfer", "Point of Sales(POS)", "Cash", "GiftCard"};

    public void orderPayTypeList() {

        List<String> paytypeL = new ArrayList<>();

        for (String data : paytypeList) {
            paytypeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(paytypeL);
        paytype.setItems(listData);
    }

    private String[] staffunitList = {"Technical", "Sales", "Attendant", "Customer Services"};

    public void staffUnitList() {

        List<String> staffunitL = new ArrayList<>();

        for (String data : staffunitList) {
            staffunitL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(staffunitL);
        staffUnit.setItems(listData);
    }

    public void logout() {

        try {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                //TO HIDE MAIN FORM
                logout.getScene().getWindow().hide();

                //LINK YOUR LOGIN FORM AND DISPLAY IT
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Sneaker Inventory Management System");

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == product) {
            productForm.setVisible(true);
            orderForm.setVisible(false);

            productTypeList();
            productStatusList();

        } else if (event.getSource() == order) {
            productForm.setVisible(false);
            orderForm.setVisible(true);

        }
    }

    @FXML
    public void handleListViewMouseClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            selectProduct();
        }
    }

    @FXML
    public void handleListViewKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            selectProduct();
        } else if (event.getCode() == KeyCode.DOWN && event.isControlDown()) {
            // Jump to the next item (scrolling down)
            productlistview.getSelectionModel().selectNext();
        } else if (event.getCode() == KeyCode.UP && event.isControlDown()) {
            // Jump to the previous item (scrolling up)
            productlistview.getSelectionModel().selectPrevious();
        }
    }

    public void selectProduct() {
        String selectedProduct = productlistview.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            searchProduct.setText(selectedProduct);
            productlistview.setVisible(false);

            // Call fetchproductsForOrder method with the selected product's name
            productData orderProd = fetchproductsForOrder(selectedProduct);

        }
    }

    public productData fetchproductsForOrder(String productName) {
        String sql = "SELECT p.prod_name, p.status, p.price "
                + "FROM product p "
                + "WHERE p.prod_name = ?";
        productData orderProd = new productData("", "", 0.0); // Default values

        try (Connection connect = database.connectDB(); PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, productName);

            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    String order_name = result.getString("prod_name");
                    String order_status = result.getString("status");
                    double order_price = result.getDouble("price");
                    orderProd = new productData(order_name, order_status, order_price);
                } else {
                    System.out.println("No records found for Selected product ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderProd; // Return the fetched product data
    }

//------------------------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productTypeList();
        productStatusList();
        productShowData();

        orderPayTypeList();
        staffUnitList();

// Hide ListView initially
        productlistview.setVisible(false);

// Assuming productListView is your ListView<String> and searchProduct is your TextField
        searchProduct.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the text field is not empty
            if (!newValue.isEmpty()) {

                try {
                    // Establish database connection
                    connect = database.connectDB();

                    // Prepare the SQL query to retrieve product names
                    String sql = "SELECT prod_name FROM product WHERE prod_name LIKE ?";

                    // Create a prepared statement
                    prepare = connect.prepareStatement(sql);

                    // Set the parameter in the SQL query to search for product names containing the newValue
                    prepare.setString(1, "%" + newValue + "%");

                    // Execute the query
                    result = prepare.executeQuery();
                    // Clear the existing items in the ListView
                    productlistview.getItems().clear();

                    // Iterate through the result set and add product names to the ListView
                    while (result.next()) {
                        String productName = result.getString("prod_name");
                        productlistview.getItems().add(productName);
                    }

                    // Close resources
                    result.close();
                    prepare.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle database errors
                } finally {
                    // Close the connection
                    if (connect != null) {
                        try {
                            connect.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // Show the ListView when there is text in the TextField
                productlistview.setVisible(true);
            } else {
                // Clear the ListView if the text field is empty
                productlistview.getItems().clear();
                // Hide the ListView when the text field is empty
                productlistview.setVisible(false);
            }
        });

    }

}
