package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardFormController {

    @FXML
    private JFXButton btnActivity;

    @FXML
    private JFXButton btnCource;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnDiscount;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnStock;

    @FXML
    private AnchorPane centerNode;
    
    @FXML
    private Pane innerPane;

    @FXML
    private Label lblActivity;

    @FXML
    private Label lblCourceCount;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private Label lblDayStatus;

    @FXML
    private AnchorPane sideNode;

    @FXML
    private AnchorPane sidePane;

    private JFXButton selectedButton;

    public void initialize(){
        loadPane("/view/home_form.fxml",btnHome);
    }


    @FXML
    void btnActivityOnAction(ActionEvent event) {
       loadPane("/view/activity_form.fxml",btnActivity);
    }

    @FXML
    void btnCourceOnAction(ActionEvent event) {
      loadPane("/view/cource_form.fxml",btnCource);
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        loadPane("/view/customer_form.fxml",btnCustomer);
    }

    @FXML
    void btnDiscountOnAction(ActionEvent event) {
        loadPane("/view/discount_form.fxml",btnDiscount);
    }

    @FXML
    void btnEmoloyeeOnAction(ActionEvent event) {
        loadPane("/view/employee_form.fxml",btnEmployee);
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) {
        loadPane("/view/home_form.fxml",btnHome);

    }

    @FXML
    void btnMenuBarOnAction(ActionEvent event) {
        AnchorPane profileForm = null;
        try {
            profileForm = FXMLLoader.load(this.getClass().getResource("/view/profile_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // Set the initial position of the profile form to be outside the sideNode
        profileForm.setTranslateX(-sideNode.getWidth());
        sideNode.getChildren().add(profileForm);

        // Create a TranslateTransition to animate the profile form
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), profileForm);
        slideIn.setFromX(-sideNode.getWidth());
        slideIn.setToX(0);

        // Play the animation
        slideIn.play();
        this.sideNode.getChildren().clear();
        this.sideNode.getChildren().add(profileForm);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        loadPane("/view/Payment_form.fxml",btnPayment);
    }

    @FXML
    void btnStockOnAction(ActionEvent event) {
        loadPane("/view/stock_form.fxml",btnStock);


    }

    private void handleSelection(JFXButton button) {
        if(selectedButton != null){
            selectedButton.setStyle(""); // Deselect the previously selected button
        }
        selectedButton = button; // Set the new selected button
        selectedButton.setStyle("-fx-background-radius: 20px 0px 0px 20px;\n" +
                "    -fx-background-color: #97aeed;\n" +
                "    -fx-text-fill: #ffffff;"); // Apply the selected style
    }
    private void animatePane(Pane pane) {
        pane.setTranslateX(-pane.getWidth()); // Start the pane outside the view
        pane.setOpacity(0); // Start with opacity 0 for a fade-in effect

        // Create TranslateTransition to move the pane into view
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), pane);
        translateTransition.setToX(0);

        // Create FadeTransition to fade the pane in
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), pane);
        fadeTransition.setToValue(1);

        // Play both animations together
        translateTransition.play();
        fadeTransition.play();
    }

    public void loadPane(String name ,JFXButton selectedButton){
        handleSelection(selectedButton);
        AnchorPane customerPane = null;
        try {
            customerPane = FXMLLoader.load(this.getClass().getResource(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.centerNode.getChildren().clear();
        this.centerNode.getChildren().add(customerPane);
        animatePane(customerPane); // Call the animation method
    }

}

