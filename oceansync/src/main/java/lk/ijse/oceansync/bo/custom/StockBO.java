package lk.ijse.oceansync.bo.custom;

import lk.ijse.oceansync.bo.SuperBO;
import lk.ijse.oceansync.model.StockDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StockBO extends SuperBO {
    public boolean deleteStock(String id) throws SQLException, ClassNotFoundException;
        public StockDTO searchStockByID(String id) throws SQLException, ClassNotFoundException;
    public boolean addStock(StockDTO dto) throws SQLException, ClassNotFoundException;
    public String generateNewStockID() throws SQLException, ClassNotFoundException;
    public boolean updateStock(StockDTO dto) throws SQLException, ClassNotFoundException;
    public ArrayList<StockDTO> getAllStock() throws SQLException, ClassNotFoundException;
    public StockDTO getAvailableStock(String stockId) throws SQLException;
    StockDTO searchStockByName(String stockName) throws SQLException;
}
