package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.oceansync.bo.BOFactory;
import lk.ijse.oceansync.bo.UserBO;
import lk.ijse.oceansync.bo.UserBOImpl;
import lk.ijse.oceansync.model.UserDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.ErrorManager;

public class LoginFormController {

    public Pane sidePane;
    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtuserid;

    public static String[] credential = new String[3];
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event)  {
        String userId = txtuserid.getText();
        String password = txtPassword.getText();

        try {
            UserDTO userDTO = userBO.checkUser(userId);
            if (userDTO.getUserId().equals(userId) && userDTO.getPassword().equals(password)){
                navigateToTheDashboard();
                credential[0] = userId;
                credential[1] = userDTO.getUserName();
                credential[2] = password;

            }if (!userDTO.getUserId().equals(userId)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("User Id is Incorrect");
                alert.showAndWait();

            }else if (!userDTO.getPassword().equals(password)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Password is Incorrect");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void navigateToTheDashboard() {

        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
        }

        Scene scene = new Scene(pane);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    @FXML
    void linkRegistrationOnAction(ActionEvent event) throws IOException {
        AnchorPane registationForm = null;
        try {
            registationForm = FXMLLoader.load(this.getClass().getResource("/view/registration_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sidePane.getChildren().clear();
        this.sidePane.getChildren().add(registationForm);
    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnLoginOnAction(event);
    }


    public void txtUserIdOnReleasedAction(KeyEvent keyEvent) {
        //    if (!Regex.setTextColor(T))
    }

    public void txtPasswordOnReleasedAction(KeyEvent keyEvent) {
        //
    }

}
