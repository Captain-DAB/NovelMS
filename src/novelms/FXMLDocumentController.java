/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package novelms;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.sql.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author USER
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button alreadyHave;

    @FXML
    private Button createBtn;

    @FXML
    private Hyperlink forgotPassword;

    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private FontAwesomeIcon fp_backToQuestionForm;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private TextField fp_username;

    @FXML
    private Label loginAccount;

    @FXML
    private Label loginAccount1;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePasswordBtn;

    @FXML
    private AnchorPane np_changePasswordForm;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Label registerAccount;

    @FXML
    private Button registerBtn;

    @FXML
    private AnchorPane register_form;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private TextField su_username;

    @FXML
    private AnchorPane welcome;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Alert alert;

    private String[] questionList = {"What is your name?", "What is your favourite Color?", "What is your favourite food?",};

    public void regLquestionList() {

        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }

    public void loginBtn() {
        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();
        } else {
            String selctData = "SELECT username, password FROM admin WHERE username = ? and password = ?";

            connect = database.connectDB();

            try {
                prepare = connect.prepareStatement(selctData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();

                //IF SUCCESSFULLY LOGIN, THEN PROCEED TO ANOTHER FORM WHICH IS OUR MAIN FORM
                if (result.next()) {
                    //TO GET THE USERNAME THAT USER USED
                    data.username = si_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully login!");
                    alert.showAndWait();

                    //Link your MainForm
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.setTitle("Novel Management System Dashboard");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    
                    stage.setScene(scene);
                    stage.show();
                    
                    loginBtn.getScene().getWindow().hide();
//                    
                } else {//IF NOT, THEN THE ERROR MESSAGE WILL APPEAR
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void regBtn() {

        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO admin (username, password, question, answer, date) " + "Values(?,?,?,?,?)";
            connect = database.connectDB();

            try {
                //CHECK IF THE USERNAME IS ALREADY RECORDED AND PASSWORD LESS THAN 8
                String checkUsername = "SELECT username FROM admin WHERE username = '"
                        + su_username.getText() + "'";

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " is already taken");
                    alert.showAndWait();
                } else if (su_password.getText().length() < 8) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password, at least 8 characters are needed");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());

                    java.util.Date date = new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully registered Account!");
                    alert.showAndWait();

                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(welcome);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));

                    welcome.setStyle("-fx-background-color: #30343E; -fx-background-radius: 10px 0 0 10px;");
                    slider.play();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void proceedBtn() {
        if (fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String selectData = "SELECT username, question, answer FROM admin WHERE username = ? AND question = ? AND answer = ?";
            connect = database.connectDB();

            try {

                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String) fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                result = prepare.executeQuery();

                if (result.next()) {
                    np_changePasswordForm.setVisible(true);
                    fp_questionForm.setVisible(false);

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void forgotPassQuestionList() {

        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
    }

    public void changePassBtn() {

        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {

            if (np_newPassword.getText().equals(np_confirmPassword.getText())) {
                String getDate = "SELECT date FROM admin WHERE username = '"
                        + fp_username.getText() + "'";

                connect = database.connectDB();

                try {
                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();

                    String date = "";
                    if (result.next()) {
                        date = result.getString("date");
                    }

                    String updatePass = "UPDATE admin SET password = '"
                            + np_newPassword.getText() + "', question = '"
                            + fp_question.getSelectionModel().getSelectedItem() + "', answer = '"
                            + fp_answer.getText() + "', date = '"
                            + date + "' WHERE username = '"
                            + fp_username.getText() + "'";

                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully changed Password!");
                    alert.showAndWait();

                    login_form.setVisible(true);
                    np_changePasswordForm.setVisible(false);

                    //TO CLEAR FIELDS
                    np_newPassword.setText("");
                    np_confirmPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.setText("");
                    fp_username.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password Mismatch");
                alert.showAndWait();
            }

        }

    }

    public void backToLoginForm() {
        login_form.setVisible(true);
        fp_questionForm.setVisible(false);
    }

    public void backToQuestionForm() {
        fp_questionForm.setVisible(true);
        np_changePasswordForm.setVisible(false);
    }

    public void switchForgotPass() {
        fp_questionForm.setVisible(true);
        login_form.setVisible(false);

        forgotPassQuestionList();
    }

    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == createBtn) {
            slider.setNode(welcome);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                alreadyHave.setVisible(true);
                createBtn.setVisible(false);

                fp_questionForm.setVisible(false);
                login_form.setVisible(true);
                np_changePasswordForm.setVisible(false);

                regLquestionList();
                welcome.setStyle("-fx-background-color: #30343E; -fx-background-radius: 0 10px 10px 0;");
            });
            slider.play();
        } else if (event.getSource() == alreadyHave) {
            slider.setNode(welcome);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                alreadyHave.setVisible(false);
                createBtn.setVisible(true);

                fp_questionForm.setVisible(false);
                login_form.setVisible(true);
                np_changePasswordForm.setVisible(false);

                welcome.setStyle("-fx-background-color: #30343E; -fx-background-radius: 10px 0 0 10px;");
            });
            slider.play();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
