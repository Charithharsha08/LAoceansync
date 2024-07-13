package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.oceansync.bo.custom.ActivityBO;
import lk.ijse.oceansync.bo.BOFactory;
import lk.ijse.oceansync.model.ActivityDTO;
import lk.ijse.oceansync.view.tdm.ActivityTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class ActivityFormController {

    @FXML
    private TableColumn<?, ?> colActivityId;

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Label lblActivityId;

    @FXML
    private TableView<ActivityTm> tblActivity;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtLocation;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private JFXTextField txtType;

    ActivityBO activityBO = (ActivityBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ACTIVITY);

    public void initialize(){
        tblActivity.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("activityId"));
        tblActivity.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblActivity.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblActivity.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("location"));
        tblActivity.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("cost"));
        loadNextActivityId();
        loadActivityTable();

    }

    private void loadActivityTable() {
        tblActivity.getItems().clear();
        try {
            ArrayList< ActivityDTO > activityDTOS = activityBO.getAllActivity();
            for (ActivityDTO activityDTO : activityDTOS){
                tblActivity.getItems().add(new ActivityTm(activityDTO.getActivityId(),activityDTO.getName(),activityDTO.getType(),activityDTO.getLocation(),activityDTO.getCost()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadNextActivityId() {
        try {
           lblActivityId.setText(activityBO.generateNewActivityID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtName.clear();
        txtType.clear();
        txtCost.clear();
        txtLocation.clear();
        txtSearchId.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to delete ? ");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    String id = txtSearchId.getText();
                    try {
                        boolean isDeleted = activityBO.deleteActivity(id);
                        if (isDeleted){
                            new Alert(Alert.AlertType.INFORMATION,"activity deleted !").show();
                            clearFields();
                            loadNextActivityId();
                            loadActivityTable();

                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String activityId = lblActivityId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        String location = txtLocation.getText();
        double cost = Double.parseDouble(txtCost.getText());

        if (name.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter name").show();
            return;
        }if (type.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter type").show();
            return;
        }if (location.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter location").show();
            return;
        }if (txtCost.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter cost").show();
            return;
        }
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to Save ? ");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    try {
                        boolean isSaved = activityBO.addActivity(new ActivityDTO(activityId,name,type,location,cost));
                        if (isSaved) {
                            new Alert(Alert.AlertType.INFORMATION, "Customer save successfully ");
                            clearFields();
                            loadNextActivityId();
                            loadActivityTable();
                            clearFields();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Something went wrong please try again ! ");
                            clearFields();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String activityId = lblActivityId.getText();
        String name = txtName.getText();
        String type = txtType.getText();
        String location = txtLocation.getText();
        double cost = Double.parseDouble(txtCost.getText());

        if (name.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter name").show();
            return;
        }if (type.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter type").show();
            return;
        }if (location.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter location").show();
            return;
        }if (txtCost.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please enter cost").show();
            return;
        }
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to Update ? ");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    try {
                        boolean isUpdated = activityBO.updateActivity(new ActivityDTO(activityId,name,type,location,cost));
                        if (isUpdated) {
                            new Alert(Alert.AlertType.INFORMATION, "Customer update successfully ");
                            clearFields();
                            loadNextActivityId();
                            loadActivityTable();
                            clearFields();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Something went wrong please try again ! ");
                            clearFields();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtCostOnAction(ActionEvent event) {

    }

    @FXML
    void txtLocationOnAction(ActionEvent event) {

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String id = txtSearchId.getText();
        try {
            ActivityDTO activityDTO = activityBO.searchActivity(id);
            lblActivityId.setText(activityDTO.getActivityId());
            txtName.setText(activityDTO.getName());
            txtType.setText(activityDTO.getType());
            txtLocation.setText(activityDTO.getLocation());
            txtCost.setText(String.valueOf(activityDTO.getCost()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtTypeOnAction(ActionEvent event) {

    }

}
