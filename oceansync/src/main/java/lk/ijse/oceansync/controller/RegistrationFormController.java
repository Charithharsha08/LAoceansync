package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.oceansync.bo.BOFactory;
import lk.ijse.oceansync.bo.UserBO;
import lk.ijse.oceansync.model.UserDTO;


import java.io.IOException;
import java.sql.SQLException;

public class RegistrationFormController {

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPw;

    @FXML
    private JFXTextField txtUserId;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @FXML
    void btnClearOnAction(ActionEvent event) {
        Window window = txtUserId.getScene().getWindow();
        Stage stage = (Stage) window;
        stage.close();
        loadLoginPage();
    }

    private void clearFields() {
        loadLoginPage();
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String name = txtName.getText();
        String pw = txtPw.getText();

        /* if(isValid()){
           try {

                if (userId.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please Enter User Id").show();
                } else if (name.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please Enter Name").show();
                }else if (pw.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Please Enter Password").show();
                }
               boolean isSaved  = UserRepo.saveUser(userId, name, pw);


                if (isSaved) {
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "User Registration Successful");
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            Window window = txtUserId.getScene().getWindow();
                            Stage stage = (Stage) window;
                            stage.close();
                            loadLoginPage();
                        } else {
                            clearFields();
                        }
                    });
                } else {
                    new Alert(Alert.AlertType.WARNING, "User Registration Failed").show();
                }
            } catch(SQLException e){
                new Alert(Alert.AlertType.ERROR, "User Id Already Exists").show();
                throw new RuntimeException(e);
            }
        }*/
        try {
        if (userId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Enter User Id").show();
        } else if (name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Enter Name").show();
        }else if (pw.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Enter Password").show();
        }
           boolean isSaved = userBO.addUser(new UserDTO(userId,name,pw));
            if (isSaved) {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "User Registration Successful");
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        Window window = txtUserId.getScene().getWindow();
                        Stage stage = (Stage) window;
                        stage.close();
                        loadLoginPage();
                    } else {
                        clearFields();
                    }
                });
            } else {
                new Alert(Alert.AlertType.WARNING, "User Registration Failed").show();
            }
        } catch(SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, "User Id Already Exists").show();
            throw new RuntimeException(e);
        }
    }



    private void loadLoginPage() {
        try {
            AnchorPane node = FXMLLoader.load(getClass().getResource("/view/login_page.fxml"));
            Scene scene = new Scene(node);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnRegisterOnAction(event);
    }

    @FXML
    void txtUserIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPw.requestFocus();
    }

    public void txtUserIdOnReleasedAction(KeyEvent keyEvent) {
       // Regex.setTextColor(TextField.USERID, txtUserId);

    }
    public void txtUserNameOnReleasedAction(KeyEvent keyEvent) {
       // Regex.setTextColor(TextField.USERNAME, txtName);
    }

    public void txtPasswordOnReleasedAction(KeyEvent keyEvent) {
      //  Regex.setTextColor(TextField.PASSWORD, txtPw);

    }
    public boolean isValid(){
      /*  if (!Regex.setTextColor(TextField.USERID, txtUserId)) return false;
        if (!Regex.setTextColor(TextField.USERNAME, txtName)) return false;
        if (!Regex.setTextColor(TextField.PASSWORD, txtPw)) return false;*/
        return true;
    }
}


