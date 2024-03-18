/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package novelms;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private TableColumn<?, ?> product_col_ProductID;

    @FXML
    private TableColumn<?, ?> product_col_ProductName;

    @FXML
    private TableColumn<?, ?> product_col_date;

    @FXML
    private TableColumn<?, ?> product_col_price;

    @FXML
    private TableColumn<?, ?> product_col_status;

    @FXML
    private TableColumn<?, ?> product_col_stock;

    @FXML
    private TableColumn<?, ?> product_col_type;

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
    private TableView<?> product_tableView;

    @FXML
    private ComboBox<?> product_type;

    @FXML
    private Button product_updateBtn;

    @FXML
    private Hyperlink purchase;

    @FXML
    private Hyperlink report;

    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private String[] typeList = {"Batteries", "Inverter", "Panels", "Controller", "Kits", "Surge", "Cable"};

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productTypeList();
        productStatusList();
    }

}
