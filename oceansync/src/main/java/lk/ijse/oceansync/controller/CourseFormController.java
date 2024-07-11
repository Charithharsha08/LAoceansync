package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.oceansync.bo.BOFactory;
import lk.ijse.oceansync.bo.CourseBO;
import lk.ijse.oceansync.model.CourseDTO;
import lk.ijse.oceansync.view.tdm.CourceTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class CourseFormController {

    @FXML
    private TableColumn<?, ?> colCost;

    @FXML
    private TableColumn<?, ?> colCourceId;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Label lblCourceId;

    @FXML
    private TableView<CourceTm> tblCource;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextField txtDuration;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSearchId;

    CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COURCE);

    public void initialize() {
        tblCource.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("courceId"));
        tblCource.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCource.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("duration"));
        tblCource.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("cost"));
        loadCourseTable();
        loadNextCourseId();
    }

    private void loadNextCourseId() {
        try {
           lblCourceId.setText(courseBO.generateNewCourseID());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCourseTable() {
        tblCource.getItems().clear();
        try {
            ArrayList< CourseDTO > courseDTOS = courseBO.getAllCourses();
            for (CourseDTO courseDTO : courseDTOS){
                tblCource.getItems().add(new CourceTm(courseDTO.getCourceId(),courseDTO.getName(),courseDTO.getDuration(),courseDTO.getCost()));
            }
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
        txtSearchId.clear();
        txtName.clear();
        txtCost.clear();
        txtDuration.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblCourceId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to delete ? ");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                try {
                   boolean isDelete = courseBO.deleteCourse(id);
                   if (isDelete){
                       new Alert(Alert.AlertType.INFORMATION,"Course deleted !").show();
                       clearFields();
                       loadCourseTable();
                       loadNextCourseId();
                   }else {
                       new Alert(Alert.AlertType.ERROR,"Something went wrong please try again later ! ").show();
                   }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String courceId = lblCourceId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        String costText = txtCost.getText();

        if (courceId.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please Enter Course ID").show();
            return;
        }

        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Course Name").show();
            return;
        }

        if (duration.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Course Duration").show();
            return;
        }

        if (costText.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Course Cost").show();
            return;
        }

        double cost;
        try {
            cost = Double.parseDouble(costText);
            if (cost < 0) {
                new Alert(Alert.AlertType.ERROR, "Course Cost cannot be negative").show();
                return;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please Enter a valid Course Cost").show();
            return;
        }

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to Save?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        boolean isSaved = courseBO.addCourse(new CourseDTO(courceId, name, duration, cost));
                        if (isSaved) {
                            new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                            clearFields();
                            loadCourseTable();
                            loadNextCourseId();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                    }
                }
            });
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
        }
    }


        @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String courceId = lblCourceId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        double cost = Double.parseDouble(txtCost.getText());

        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Course Name").show();
        }if (duration.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Course Duration").show();
        }if (txtCost.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Course Cost").show();
        }if(cost<0 ) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Course Cost").show();
        }        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to update ? ");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                try {
                    boolean isUpdated = courseBO.updateCourse(new CourseDTO(courceId,name,duration,cost));
                    if (isUpdated){
                        new Alert(Alert.AlertType.INFORMATION, "Course updated done ").show();
                        clearFields();
                        loadCourseTable();
                        loadNextCourseId();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Something went wrong please try again");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }});
    }

    @FXML
    void txtCostOnAction(ActionEvent event) {

    }

    @FXML
    void txtDurationAction(ActionEvent event) {

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String idText = txtSearchId.getText();
        try {
           CourseDTO courseDTO = courseBO.searchCourse(idText);
           lblCourceId.setText(courseDTO.getCourceId());
           txtName.setText(courseDTO.getName());
           txtDuration.setText(courseDTO.getDuration());
           txtCost.setText(String.valueOf(courseDTO.getCost()));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtnameOnAction(ActionEvent event) {

    }

}
