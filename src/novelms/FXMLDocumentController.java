/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package novelms;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane login_form;

    @FXML
    private AnchorPane register_form;

    @FXML
    private AnchorPane welcome;
    
    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == createBtn) {
            slider.setNode(welcome);
            slider.setToX(350);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                alreadyHave.setVisible(true);
                createBtn.setVisible(false);
                
                register_form.setVisible(true);
                login_form.setVisible(false);
                
                welcome.setStyle("-fx-background-color: #30343E; -fx-background-radius: 0 10px 10px 0;");

//                fp_questionForm.setVisible(false);
//                si_loginForm.setVisible(true);
//                np_newPassForm.setVisible(false);

//                regLquestionList();
            });
            slider.play();
        } else if (event.getSource() == alreadyHave) {
            slider.setNode(welcome);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                alreadyHave.setVisible(false);
                createBtn.setVisible(true);
                
                register_form.setVisible(false);
                login_form.setVisible(true);
                
                welcome.setStyle("-fx-background-color: #30343E; -fx-background-radius: 10px 0 0 10px;");

//                fp_questionForm.setVisible(false);
//                si_loginForm.setVisible(true);
//                np_newPassForm.setVisible(false);
            });
            slider.play();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
