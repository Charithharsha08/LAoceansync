package lk.ijse.oceansync.controller;

//iimport javafx.fxml.FXML;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static lk.ijse.oceansync.controller.LoginFormController.credential;

public class HomeFormController {

    public Label lblDayStatus;
    @FXML
    private Label lblActivity;

    @FXML
    private Label lblCourceCount;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private AnchorPane sidePane;

    public void initialize() {

        setDayStatus();
        loadCoustomerCount();
        loadCourceCount();
        loadActivityCount();
        applyStyles();

    }

    private void applyStyles() {
        lblActivity.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            }
        });


    }

    private void loadActivityCount() {
        /*int activityCount;
        try {
            activityCount = ActivityRepo.getActivityCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblActivity.setText(String.valueOf(activityCount));*/
    }

    private void loadCourceCount() {
       /* int courceCount;
        try {
            courceCount = CourceRepo.getCourceCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblCourceCount.setText(String.valueOf(courceCount));*/
    }

    private void loadCoustomerCount() {
       /* int customerCount;
        try {
            customerCount = CustomerRepo.getCustomerCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblCustomerCount.setText(String.valueOf(customerCount));*/
    }

    private void setDayStatus() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        String status;

        if (hour >= 5 && hour < 12) {
            status = "Morning ";
        } else if (hour >= 12 && hour < 18) {
            status = "Afternoon ";
        } else {
            status = "Evening ";
        }

        lblDayStatus.setText(status + (credential[1]));
        System.out.println(credential[0]);
    }
}


