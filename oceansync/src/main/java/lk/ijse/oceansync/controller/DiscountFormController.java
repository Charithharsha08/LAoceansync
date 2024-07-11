package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.oceansync.bo.BOFactory;
import lk.ijse.oceansync.bo.DiscountBO;
import lk.ijse.oceansync.model.DiscountDTO;
import lk.ijse.oceansync.view.tdm.DiscountTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountFormController {

    @FXML
    private JFXComboBox<DiscountType> cmbDiscountType;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colDiscountId;

    @FXML
    private TableColumn<?, ?> colDiscountType;

    @FXML
    private Label lblDiscountId;

    @FXML
    private TableView<DiscountTm> tblDiscount;

    @FXML
    private JFXTextField txtDiscount;

    @FXML
    private JFXTextField txtSearchId;

    DiscountBO discountBO = (DiscountBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DISCOUNT);

    public void initialize(){
        colDiscountId.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        colDiscountType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        loadDiscountType();
        loadNextDiscountId();
        getDiscountTypes();
        loadDiscountTable();
    }

    private void loadDiscountTable() {
        tblDiscount.getItems().clear();
        try {
            ArrayList<DiscountDTO> discountDTOS = discountBO.getAllDiscount();
            for (DiscountDTO discountDTO : discountDTOS){
                tblDiscount.getItems().add(new DiscountTm(discountDTO.getDiscountId(),discountDTO.getType(),discountDTO.getDiscount()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getDiscountTypes() {
        cmbDiscountType.getItems().clear();
        for (DiscountType type : DiscountType.values()) {
            cmbDiscountType.getItems().add(type);
        }
    }

    private void loadNextDiscountId() {
        try {
           lblDiscountId.setText(discountBO.generateNewDiscountID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDiscountType() {
        cmbDiscountType.getItems().clear();
        for (DiscountType type : DiscountType.values()) {
            cmbDiscountType.getItems().add(type);
        }
    }

    @FXML
    void brnClearOnAction(ActionEvent event) {
    clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String discountId = txtSearchId.getText();

        boolean isDeleted = false;
        try {
            isDeleted = discountBO.deleteDiscount(discountId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (isDeleted) {
            new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
            clearFields();
            loadDiscountTable();
        } else {
            new Alert(Alert.AlertType.WARNING, "Not Deleted").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String discountId  = lblDiscountId.getText();
        String type = cmbDiscountType.getValue().toString();
        double discount = Double.parseDouble(txtDiscount.getText());

        try {
            boolean isdiscountSaved = discountBO.addDiscount(new DiscountDTO(discountId,type,discount));
            if (isdiscountSaved){
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                clearFields();
                loadNextDiscountId();
                loadDiscountTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        lblDiscountId.setText("");
        txtDiscount.clear();
        cmbDiscountType.getSelectionModel().clearSelection();
    }

    @FXML
    void cmbDiscountTypeOnAction(ActionEvent event) {

    }

    @FXML
    void txtDiscountOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        String discountId = txtSearchId.getText();

        DiscountDTO discountDTO = null;
        try {
            discountDTO = discountBO.searchDiscount(discountId);

            if (discountDTO != null) {
               // cmbDiscountType.(discountDTO.getType());
                txtDiscount.setText(String.valueOf(discountDTO.getDiscount()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    enum DiscountType {
        LOCAL, FOREIGN
    }
}
