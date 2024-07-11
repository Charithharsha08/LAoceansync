package lk.ijse.oceansync.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LoginPageController {
    public AnchorPane rootNode;

    public Pane sidePane;

    public void initialize() throws IOException {

        AnchorPane customerPane = null;
        try {
            customerPane = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.sidePane.getChildren().clear();
        this.sidePane.getChildren().add(customerPane);
    }
}
