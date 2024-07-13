package lk.ijse.oceansync.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.oceansync.bo.BOFactory;
import lk.ijse.oceansync.bo.custom.StockBO;
import lk.ijse.oceansync.model.StockDTO;
import lk.ijse.oceansync.view.tdm.StockTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockFormController {

    public JFXTextField txtSearchId;
    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStockId;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private Label lblStockId;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<StockTm> lblViewAllStock;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtname;

    StockBO stockBO = (StockBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STOCK);
    public void initialize() {
        lblUserId.setText(LoginFormController.credential[0]);
        lblUserName.setText(LoginFormController.credential[1]);
        lblViewAllStock.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("stockId"));
        lblViewAllStock.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        lblViewAllStock.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("type"));
        lblViewAllStock.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        lblViewAllStock.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("userId"));
        loadNextStockId();
        loadAllStock();
    }

    private void loadAllStock() {
        lblViewAllStock.getItems().clear();
        try {
            ArrayList<StockDTO> allStockDTOS = stockBO.getAllStock();
            for (StockDTO dto : allStockDTOS){
                lblViewAllStock.getItems().add(new StockTm(dto.getItemId(),dto.getName(),dto.getPrice(),dto.getQty(),dto.getUserId()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadNextStockId() {
        try {
            String nextId = stockBO.generateNewStockID();
            lblStockId.setText(nextId);
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

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String stockId = txtSearchId.getText();

        try {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure want to delete ? ");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    boolean isDeleted = false;
                    try {
                        isDeleted = stockBO.deleteStock(stockId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Stock delete successfully !").show();
                        clearFields();
                        loadAllStock();

                    }else {
                        clearFields();
                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String stockId = lblStockId.getText();
        String name = txtname.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        String userId = lblUserId.getText();



        if (stockId.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Enter Stock ID ! ");
        }
        if (name.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION, "Please enter item name ! ");
        }
        if (txtPrice.getText().isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Please enter corrct price ! ");
        }else if (price == 0 ){
            new Alert(Alert.AlertType.INFORMATION, "Price cannot be zero!");
        }
        if (txtQty.getText().isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Please enter QTY !");
        }else if (qty==0){
            new Alert(Alert.AlertType.INFORMATION,"QTY cannot be zero!");
        }
        if (userId.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Please enter UserId");
        }

        try {
            boolean stockSaved = stockBO.addStock(new StockDTO(stockId,name,price,qty,userId));
            if (stockSaved){
                new Alert(Alert.AlertType.INFORMATION, "Stock Save").show();
                clearFields();
                loadNextStockId();
                loadAllStock();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtname.clear();
        txtPrice.clear();
        txtQty.clear();
        txtSearchId.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String stockId = lblStockId.getText();
        String name = txtname.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        String userId = lblUserId.getText();

        if (txtname.getText().isEmpty()) {
           Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter Stock Name");
           alert.show();
        }
        if (txtPrice.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Stock Price").show();
        }
        if (txtQty.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Stock Quantity").show();
        }
        try {
           boolean isUpdated = stockBO.updateStock(new StockDTO(stockId,name,price,qty,userId));
           if (isUpdated){
               new Alert(Alert.AlertType.INFORMATION,"Stock update done ! ").show();
               loadAllStock();
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtPriceOnAction(ActionEvent event) {

    }

    @FXML
    void txtPriceReleaseOnAction(KeyEvent event) {

    }

    @FXML
    void txtQtyReleaseOnAction(KeyEvent event) {

    }

    @FXML
    void txtQtyonAction(ActionEvent event) {

    }

    public void txtSearchIdOnAction(ActionEvent actionEvent) {
        String s = txtSearchId.getText();
        try {
            StockDTO stockDTO =stockBO.searchStockByID(s);
            lblStockId.setText(stockDTO.getItemId());
            txtname.setText(stockDTO.getName());
            txtPrice.setText(String.valueOf(stockDTO.getPrice()));
            txtQty.setText(String.valueOf(stockDTO.getQty()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
