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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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

    private Alert alert;
    private Image image;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

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

                    populateComboBox();
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

                    populateComboBox();
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

                    populateComboBox();

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
//------------------------------------------------------------------------------------------------------------

    private void populateComboBox() {
        connect = database.connectDB();
        ObservableList<String> productList = FXCollections.observableArrayList();

        try {
            String query = "SELECT prod_name FROM product";
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            while (result.next()) {
                String productName = result.getString("prod_name");
                productList.add(productName);
            }

            searchCombo.setItems(productList);
        } catch (Exception e) {
            e.printStackTrace();

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Database Error, An error occurred while retrieving product data from the database.");
            alert.showAndWait();

        }
    }

    public void handleSearch(ActionEvent event) {
        String searchText = searchCombo.getEditor().getText().trim();
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        for (String productName : searchCombo.getItems()) {
            if (productName.toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(productName);
            }
        }

        searchCombo.setItems(filteredList);
        searchCombo.show();
    }

    private void handleAutoCompletion(String searchText) {
    if (searchText.isEmpty() && searchCombo.isShowing()) {
        // If the search text is empty and the ComboBox dropdown is showing,
        // repopulate the ComboBox with all available options
        populateComboBox();
    } else if (!searchCombo.isShowing()) {
        // If the ComboBox dropdown is not showing, update auto-completion
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        for (String productName : searchCombo.getItems()) {
            if (productName.toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(productName);
            }
        }

        searchCombo.setItems(filteredList);
        searchCombo.show();
    }
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productTypeList();
        productStatusList();
        productShowData();

        populateComboBox();

        
        //
        searchCombo.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            handleAutoCompletion(newValue);
        });
    }

}
