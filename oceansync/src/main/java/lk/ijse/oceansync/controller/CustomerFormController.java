package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.oceansync.bo.BOFactory;
import lk.ijse.oceansync.bo.CustomerBO;
import lk.ijse.oceansync.model.CustomerDTO;
import lk.ijse.oceansync.view.tdm.CustomerTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private Label lblCustomerId;

    @FXML
    private AnchorPane sidepane;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtSearchId;

    @FXML
    private JFXTextField txtTel;

    @FXML
    private JFXTextField txtEmail;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {
        txtEmail.setVisible(false);
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("tel"));
        loadCustomerTable();
        loadNextCustomerId();
    }

    private void loadNextCustomerId() {
        try {
            String loadNextCustomerId = customerBO.generateNewCustomerID();
           lblCustomerId.setText(loadNextCustomerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadCustomerTable() {
        tblCustomer.getItems().clear();
        try {
            ArrayList<CustomerDTO> dtos = customerBO.getAllCustomers();
            for (CustomerDTO dto : dtos){
                //System.out.println(dto.getCustomerId()+dto.getName()+dto.getAddress()+dto.getTel());
                tblCustomer.getItems().add(new CustomerTm(dto.getCustomerId(),dto.getName(),dto.getAddress(),dto.getTel()));
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
        txtCustomerName.clear();
        txtAddress.clear();
        txtTel.clear();
        txtEmail.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblCustomerId.getText();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to save");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK){
                try {
                    boolean isDeleted = customerBO.deleteCustomer(id);
                    if (isDeleted){
                        new Alert(Alert.AlertType.INFORMATION,"Customer delete successfully ");
                        clearFields();
                        loadCustomerTable();
                        loadNextCustomerId();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Something went wrong please try again ! ");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }else {
                clearFields();
            }
        });

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String customerId = lblCustomerId.getText();
        String customerName = txtCustomerName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();


        if (customerId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Customer Id").show();
            return;
        }
        if (customerName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Customer Name").show();
            return;
        }
        if (address.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Address").show();

            return;
        }
        if (tel.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Telephone Number").show();
            return;
        }

        try {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to save");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    boolean isSaved = false;
                    try {
                        isSaved = customerBO.addCustomer(new CustomerDTO(customerId, customerName, address, tel));
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer save successfully ");
                        clearFields();
                        loadNextCustomerId();
                        loadCustomerTable();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong please try again ! ");
                    }
                }
            });


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
    void btnUpdateOnAction(ActionEvent event) {
            String customerId = lblCustomerId.getText();
            String customerName = txtCustomerName.getText();
            String address = txtAddress.getText();
            String tel = txtTel.getText();


            if (customerId.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please Enter Customer Id").show();
                return;
            }
            if (customerName.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please Enter Customer Name").show();
                return;
            }
            if (address.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please Enter Address").show();

                return;
            }
            if (tel.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please Enter Telephone Number").show();
                return;
            }

            try {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure want to Update");
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        boolean isSaved = false;
                        try {
                            isSaved = customerBO.updateCustomer(new CustomerDTO(customerId, customerName, address, tel));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        if (isSaved) {
                            new Alert(Alert.AlertType.INFORMATION, "Customer update successfully ");
                            clearFields();
                            loadNextCustomerId();
                            loadCustomerTable();
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Something went wrong please try again ! ");
                        }
                    }
                });


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


    @FXML
    void txtAddressOnAction(ActionEvent event) {

    }

    @FXML
    void txtCustomerNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
       String num = txtSearchId.getText();
        try {
            CustomerDTO customer = customerBO.searchCustomerByNumber(num);
            lblCustomerId.setText(customer.getCustomerId());
            txtCustomerName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtTel.setText(customer.getTel());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtTelOnAction(ActionEvent event) {

    }

    @FXML
    void txtTelephoneNumberReleaseOnAction(KeyEvent event) {

    }

}
