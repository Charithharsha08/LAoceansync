package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.oceansync.bo.BOFactory;
import lk.ijse.oceansync.bo.EmployeeBO;
import lk.ijse.oceansync.model.EmployeeDTO;
import lk.ijse.oceansync.view.tdm.EmployeeTm;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import static lk.ijse.oceansync.controller.LoginFormController.credential;

public class EmployeeFormController {

    public JFXTextField txtSearchId;
    @FXML
    private TableColumn<?, ?> colActivity;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Label lblId;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private JFXTextField salary;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private JFXTextField txtActivity;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtMonth;

    @FXML
    private JFXTextField txtName;
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize() {
        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("activity"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("salary"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("userId"));
        loadEmployeeTable();
        loadNextId();
        lblUserId.setText(credential[0]);
        lblUserName.setText(credential[1]);
    }

    private void loadEmployeeTable() {
        tblEmployee.getItems().clear();
        try {
            ArrayList<EmployeeDTO> dtos =  employeeBO.getAllEmployee();
            for (EmployeeDTO employeeDTO : dtos){
                tblEmployee.getItems().add(new EmployeeTm(employeeDTO.getId(),employeeDTO.getEmployeeId(),employeeDTO.getName(),employeeDTO.getActivity(),employeeDTO.getSalary(),employeeDTO.getDate(),employeeDTO.getUserId()));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void brnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String idText = txtSearchId.getText();
        try {
           boolean isDeleted = employeeBO.deleteEmployee(idText);
           if (isDeleted){
               new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
               clearFields();
               loadEmployeeTable();
           }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblId.getText();
        String userId = lblUserId.getText();
        // String userName = lblUserName.getText();
        String employeeId = txtEmployeeId.getText();
        String name = txtName.getText();
        String activity = txtActivity.getText();
        double salary1 = Double.parseDouble(salary.getText());
        String month = txtMonth.getText();
        Date date = Date.valueOf(dpDate.getValue().toString());

        if (employeeId.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Employee Id").show();
        }if (name.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Name").show();
        }if (activity.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Activity").show();
        }if (salary1 == 0){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Salary").show();
        }if (month.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Month").show();
        }if (date.toString().isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Date").show();
        }
        try {
           boolean isSaved = employeeBO.addEmployee(new EmployeeDTO(id,employeeId,name,activity,month,salary1,date,userId));
           if (isSaved){
               new Alert(Alert.AlertType.INFORMATION, "Employee Saved").show();
               clearFields();
               loadNextId();
               loadEmployeeTable();
           }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadNextId() {
        try {
           lblId.setText(employeeBO.generateNewEmployeeID());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtName.clear();
        txtActivity.clear();
        txtActivity.clear();
        salary.clear();
        txtEmployeeId.clear();
        txtMonth.clear();
        dpDate.setValue(null);
        txtSearchId.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblId.getText();
        String userId = lblUserId.getText();
        // String userName = lblUserName.getText();
        String employeeId = txtEmployeeId.getText();
        String name = txtName.getText();
        String activity = txtActivity.getText();
        double salary1 = Double.parseDouble(salary.getText());
        String month = txtMonth.getText();
        Date date = Date.valueOf(dpDate.getValue().toString());

        if (employeeId.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Employee Id").show();
        }
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Name").show();
        }
        if (activity.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Activity").show();
        }
        if (salary1 == 0) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Salary").show();
        }
        if (month.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Month").show();
        }
        if (date.toString().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Date").show();
        }
        try {
            boolean isSaved = employeeBO.updateEmployee(new EmployeeDTO(id, employeeId, name, activity, month, salary1, date, userId));
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Saved").show();
                clearFields();
                loadNextId();
                loadEmployeeTable();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void dpDateOnAction(ActionEvent event) {

    }

    @FXML
    void txtActivityOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmployeeIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtMonthOnAction(ActionEvent event) {

    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtSalaryOnAction(ActionEvent event) {

    }

    public void txtSearchIdOnAction(ActionEvent actionEvent) {
        try {
            String id = txtSearchId.getText();
           EmployeeDTO dto =  employeeBO.searchEmployee(id);
           lblId.setText(dto.getId());
           txtEmployeeId.setText(dto.getEmployeeId());
           lblUserId.setText(dto.getUserId());
           txtActivity.setText(dto.getActivity());
           txtName.setText(dto.getName());
           txtMonth.setText(dto.getMonth());
           salary.setText(String.valueOf(dto.getSalary()));
           dpDate.setValue(dto.getDate().toLocalDate());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
