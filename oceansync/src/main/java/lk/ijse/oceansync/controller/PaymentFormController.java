package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.oceansync.bo.*;
import lk.ijse.oceansync.bo.custom.*;
import lk.ijse.oceansync.db.DbConnection;
import lk.ijse.oceansync.model.*;
import lk.ijse.oceansync.view.tdm.PaymentTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PaymentFormController {

    @FXML
    private JFXComboBox<?> cmbBougthtStockDiscount;

    @FXML
    private JFXComboBox<String> cmbPaymentType;

    @FXML
    private JFXComboBox<String> cmbSelectedActivity;

    @FXML
    private JFXComboBox<?> cmbSelectedActivityDiscount;

    @FXML
    private JFXComboBox<String> cmbSelectedCource;

    @FXML
    private JFXComboBox<?> cmbSelectedCourceDiscount;

    @FXML
    private JFXComboBox<String> cmbStock;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblActivityId;

    @FXML
    private Label lblCourceId;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblSelectedActivityAmount;

    @FXML
    private Label lblSelectedCourceCost;

    @FXML
    private Label lblStockAmount;

    @FXML
    private Label lblStockId;

    @FXML
    private Label lblTel;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearchId;

    private ObservableList<PaymentTm> paymentList = FXCollections.observableArrayList();

    String activityId;
    String courceId;
    String paymentId;
    double totalAmount;
    double balance;
    String stockId;
    int stockQty ;


    ActivityBO activityBO = (ActivityBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ACTIVITY);
    CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COURCE);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);
    StockBO stockBO = (StockBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STOCK);

    public void initialize(){
        setCellValueFactory();
        loadNextPaymentId();
        loadAllCourses();
        loadAllActivities();
        loadAllStocks();
//        loadAllDiscounts();
        loadDateAndTime();
        loadPaymentType();
    }

    private void loadPaymentType() {
        for (Type type : Type.values()) {
            cmbPaymentType.getItems().add(type.name());
        }    }

    private void loadDateAndTime() {
        LocalDate now = LocalDate.now();
        LocalTime localTime = LocalTime.now().withNano(0);

        lblDate.setText(now.toString());
        lblTime.setText(localTime.toString());
    }

    private void loadAllStocks() {
        ObservableList<String> stocks = FXCollections.observableArrayList();
        try {
            List<StockDTO> stockDTOS = stockBO.getAllStock();
            for (StockDTO dto : stockDTOS){
                stocks.add(dto.getName());
            }
            cmbStock.setItems(stocks);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllActivities() {
        ObservableList<String> activities = FXCollections.observableArrayList();
        try {
            List<ActivityDTO> activityDTOS = activityBO.getAllActivity();

            for (ActivityDTO activityDTO : activityDTOS ){
                activities.add(activityDTO.getName());
            }
            cmbSelectedActivity.setItems(activities);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllCourses() {
        ObservableList<String> cources = FXCollections.observableArrayList();
        try {
            List<CourseDTO > courseDTOS = courseBO.getAllCourses();
            for (CourseDTO courseDTO : courseDTOS){
                cources.add(courseDTO.getName());
            }
            cmbSelectedCource.setItems(cources);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadNextPaymentId() {
        try {
            lblPaymentId.setText(paymentBO.generatePaymentNewID());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));

    }

    private void calculateNetTotal() {

        double netTotal = 0;
        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            PaymentTm tm = tblPayment.getItems().get(i);
            netTotal += tm.getTotal();
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnAddToCartActivityOnAction(ActionEvent event) {
        String paymentId = lblPaymentId.getText();
        String courseId = lblCourceId.getText();
        String customerName = lblCustomerName.getText();
        activityId = lblActivityId.getText();
        String description = cmbSelectedActivity.getValue();
        double unitPrice = Double.parseDouble(lblSelectedActivityAmount.getText());
        int qty = 1;
        /*if (txtQty.getText().equals("")) {
            qty = 1;
        } else {
            qty = Integer.parseInt(txtQty.getText());
        }*/
//        int discount = Integer.parseInt(cmbDiscount.getValue());
        double discount;
        if (cmbSelectedActivityDiscount.getValue() == null) {
            discount = 0;
        } else {
            discount = Double.parseDouble(String.valueOf(cmbSelectedActivityDiscount.getValue()));
        }

        double total = qty * unitPrice;
        total -= total * (discount/100);
        if (customerName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer").show();
            return;
        }
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                paymentList.remove(selectedIndex);

                tblPayment.refresh();
                calculateNetTotal();
            }
        });
        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            if (description.equals(colDescription.getCellData(i))) {
                qty += paymentList.get(i).getQty();
                total = unitPrice * qty;

                paymentList.get(i).setQty(qty);
                paymentList.get(i).setTotal(total);

                tblPayment.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }
        PaymentTm tm = new PaymentTm(activityId, customerName, description, unitPrice, qty, discount, total, btnRemove);
        paymentList.add(tm);
        tblPayment.setItems(paymentList);
        txtQty.setText("");
        calculateNetTotal();
    }

    @FXML
    void btnAddToCartCourceOnAction(ActionEvent event) {
        // String paymentId = lblPaymentId.getText();
        String customerName = lblCustomerName.getText();
        courceId = lblCourceId.getText();
        String description = cmbSelectedCource.getValue();
        double unitPrice = Double.parseDouble(lblSelectedCourceCost.getText());
        int qty = 1 ;
        /*if (txtQty.getText().equals("")) {
            qty = 1;
        } else {
            qty = Integer.parseInt(txtQty.getText());
        }*/
        double discount;
        if (cmbSelectedCourceDiscount.getValue() == null) {
            discount = 0;
        } else {
            discount = Double.parseDouble(String.valueOf(cmbSelectedCourceDiscount.getValue()));
        }
        double total = qty * unitPrice;
        total -= total * (discount/100);
        if (customerName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer").show();
            return;
        }
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                paymentList.remove(selectedIndex);

                tblPayment.refresh();
                calculateNetTotal();
            }
        });
        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            if (description.equals(colDescription.getCellData(i))) {
                qty += paymentList.get(i).getQty();
                total = unitPrice * qty;

                paymentList.get(i).setQty(qty);
                paymentList.get(i).setTotal(total);

                tblPayment.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }
        PaymentTm tm = new PaymentTm(courceId, customerName, description, unitPrice, qty, discount, total, btnRemove);
        paymentList.add(tm);
        tblPayment.setItems(paymentList);
        txtQty.setText("");
        calculateNetTotal();


    }

    @FXML
    void btnAddToCartStockOnAction(ActionEvent event) {
       // String paymentId = lblPaymentId.getText();
         stockId = lblStockId.getText();
        String customerName = lblCustomerName.getText();
        String description = cmbStock.getValue();
        double unitPrice = Double.parseDouble(lblStockAmount.getText());

        if (txtQty.getText().isEmpty()  || txtQty.getText() == ""){
            new Alert(Alert.AlertType.ERROR,"please Enter QTY ").show();
        }
        try {
            stockQty = Integer.parseInt(txtQty.getText());

            StockDTO stockDTO = stockBO.getAvailableStock(stockId);

            if (stockQty > stockDTO.getQty()) {
                new Alert(Alert.AlertType.ERROR, "Insufficient Stock Quantity ").show();
                return;
            }else if (stockQty < 0) {
                new Alert(Alert.AlertType.ERROR, "Invalid Quantity").show();
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        double discount;
        if (cmbBougthtStockDiscount.getValue() == null) {
            discount = 0;
        } else {
            discount = Double.parseDouble(String.valueOf(cmbBougthtStockDiscount.getValue()));
        }
        double total = stockQty * unitPrice;
        total -= total * (discount/100);
        if (customerName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select a customer").show();
            return;
        }
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblPayment.getSelectionModel().getSelectedIndex();
                paymentList.remove(selectedIndex);

                tblPayment.refresh();
                calculateNetTotal();
            }
        });
        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            if (description.equals(colDescription.getCellData(i))) {
                stockQty += paymentList.get(i).getQty();
                total = unitPrice * stockQty;

                paymentList.get(i).setQty(stockQty);
                paymentList.get(i).setTotal(total);

                tblPayment.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }
        PaymentTm tm = new PaymentTm(stockId, customerName, description, unitPrice, stockQty, discount, total, btnRemove);
        paymentList.add(tm);
        tblPayment.setItems(paymentList);
        txtQty.setText("");
        calculateNetTotal();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtQty.setText("");
        lblCustomerId.setText("");
        lblCustomerName.setText("");
        cmbSelectedCource.getSelectionModel().clearSelection();
        cmbSelectedActivity.getSelectionModel().clearSelection();
        cmbStock.getSelectionModel().clearSelection();
        cmbPaymentType.getSelectionModel().clearSelection();
        cmbBougthtStockDiscount.getSelectionModel().clearSelection();
        cmbSelectedCourceDiscount.getSelectionModel().clearSelection();
        cmbSelectedActivityDiscount.getSelectionModel().clearSelection();
        lblCourceId.setText("");
        lblSelectedCourceCost.setText("");
        lblSelectedActivityAmount.setText("");
        lblStockId.setText("");
        lblStockAmount.setText("");
        lblSelectedCourceCost.setText("");
        lblSelectedActivityAmount.setText("");
    }

    @FXML
    void btnPlacePaymentOnAction(ActionEvent event) {
        paymentId = lblPaymentId.getText();
        String type = (String) cmbPaymentType.getValue();
        double total = Double.parseDouble(lblNetTotal.getText());
        Date date = Date.valueOf(lblDate.getText());
        String customerId = lblCustomerId.getText();
        String stockId = lblStockId.getText();

        if (txtAmount.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please add amount").show();
        }
        totalAmount = Double.parseDouble(txtAmount.getText());

        if (txtAmount.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please enter amount ").show();
        }
        balance = totalAmount- total;

        List<PaymentDetailDTO> paymentDetails = new ArrayList<>();
        List<SelectedActivityDTO> selectedActivities = new ArrayList<>();
        List<SelectedCourceDTO> selectedCources = new ArrayList<>();
        List<SelectedStockDTO> selectedStocks =new ArrayList<>();

        for (int i = 0; i < tblPayment.getItems().size(); i++) {
            PaymentTm tm = tblPayment.getItems().get(i);

            PaymentDetailDTO paymentDetail = new PaymentDetailDTO(
                    lblPaymentId.getText(),
                    lblCustomerId.getText(),
                    lblCustomerName.getText(),
                    tm.getId(),
                    tm.getDescription(),
                    tm.getUnitPrice(),
                    tm.getQty(),
                    tm.getDiscount(),
                    tm.getTotal()
            );
            paymentDetails.add(paymentDetail);

            char[] charArray = tm.getId().toCharArray();
            if (charArray[0] == 'A') {
                //System.out.println("A");
                SelectedActivityDTO sAct = new SelectedActivityDTO(
                        tm.getId(),
                        customerId,
                        Date.valueOf(lblDate.getText()));
                selectedActivities.add(sAct);
                //System.out.println(sAct.toString());
            } else if (charArray[0] == 'C') {
                SelectedCourceDTO sCource = new SelectedCourceDTO(
                        customerId,
                        tm.getId(),
                        Date.valueOf(lblDate.getText())
                );
                selectedCources.add(sCource);
                //  System.out.println("C");
            } else if (charArray[0] == 'S') {
                SelectedStockDTO sStock = new SelectedStockDTO(
                        stockId,
                        tm.getQty(),
                        customerId,
                        paymentId,
                        Date.valueOf(lblDate.getText())
                );
                selectedStocks.add(sStock);

                System.out.println(sStock);
                // System.out.println("S");
            }
        }
        try {
            StockDTO stockDTO = stockBO.searchStockByID(stockId);

            StockDTO dto = new StockDTO(stockDTO.getItemId(),stockDTO.getName(),stockDTO.getPrice(),stockQty,stockDTO.getUserId());

            boolean isSaved = false;
            isSaved = paymentBO.placePayment(new PaymentDTO(paymentId,type,total,date,customerId,paymentDetails,dto,selectedActivities,selectedCources,selectedStocks));
            System.out.println("Is saved placed payment");
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Payment Saved").showAndWait();
                clearFields();
                loadNextPaymentId();

            }else {
                new Alert(Alert.AlertType.ERROR, "Payment Not Saved").showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {

        JasperDesign report = null;
        try {
            report = JRXmlLoader.load("src/main/java/lk/ijse/oceansync/reports/payment-report.jrxml");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        JasperReport jasperReport = null;
        try {
            jasperReport = JasperCompileManager.compileReport(report);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        Map<String , Object> data = new HashMap<>();
        // System.out.println(data);
        data.put("paymentId", paymentId);
        //  System.out.println(totalAmount);

        data.put("totalAmount", totalAmount);
        data.put("balance", balance);

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JasperViewer.viewReport(jasperPrint, false);
    }

    @FXML
    void btncreateNewActivityOnAction(ActionEvent event) {

    }

    @FXML
    void btncreateNewCourceOnAction(ActionEvent event) {

    }

    @FXML
    void btncreateNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void cmbBougthtStockDiscountOnAction(ActionEvent event) {

    }

    @FXML
    void cmbPaymentTypeOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSelectedActivityDiscountOnAction(ActionEvent event) {


    }

    @FXML
    void cmbSelectedActivityOnAction(ActionEvent event) {
    String activity = (String) cmbSelectedActivity.getValue();
        try {
            ActivityDTO dto =  activityBO.selectActivityByName(activity);
            lblSelectedActivityAmount.setText(String.valueOf(dto.getCost()));
            lblActivityId.setText(dto.getActivityId());
            lblLocation.setText(dto.getLocation());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbSelectedCourceDiscountOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSelectedCourceOnAction(ActionEvent event) {
        String cource = (String) cmbSelectedCource.getValue();
        try {
            CourseDTO courseDTO = courseBO.getCourceByCourceName(cource);
            lblSelectedCourceCost.setText(String.valueOf(courseDTO.getCost()));
            lblCourceId.setText(courseDTO.getCourceId());
            lblDuration.setText(String.valueOf(courseDTO.getDuration()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbStockOnAction(ActionEvent event) {
        try {
            String stockName = (String) cmbStock.getValue();
           StockDTO stockDTO =  stockBO.searchStockByName(stockName);
            lblStockAmount.setText(String.valueOf(stockDTO.getPrice()));
            lblStockId.setText(stockDTO.getItemId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtAmountOnAction(ActionEvent event) {

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchIdOnAction(ActionEvent event) {
        try {
           CustomerDTO customerDTO = customerBO.searchCustomerByNumber(txtSearchId.getText());
            if (customerDTO == null) {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
                return;
            }else {
                lblCustomerId.setText(customerDTO.getCustomerId());
                lblCustomerName.setText(customerDTO.getName());
                lblTel.setText(customerDTO.getTel());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
enum Type{
    CASH, CARD
}

