/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package novelms;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author USER
 */
public class addProductController implements Initializable {

    @FXML
    private Button cancelProduct;

    @FXML
    private TextField order_amount;

    @FXML
    private Label order_name;

    @FXML
    private TextField order_price;

    @FXML
    private Spinner<?> order_spinner;

    @FXML
    private Label order_status;

    @FXML
    private AnchorPane products;
    
    private productData prodData;
    
    public void setData(productData prodData){
        this.prodData = prodData;
        
        order_name.setText(prodData.getProductName());
        order_price.setText(String.valueOf(prodData.getPrice()));
        order_status.setText(prodData.getStatus());
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
