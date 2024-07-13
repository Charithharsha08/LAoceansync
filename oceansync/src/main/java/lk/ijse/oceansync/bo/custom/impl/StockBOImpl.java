package lk.ijse.oceansync.bo.custom.impl;

import lk.ijse.oceansync.bo.custom.StockBO;
import lk.ijse.oceansync.dao.DAOFactory;
import lk.ijse.oceansync.dao.custom.impl.StockDAO;
import lk.ijse.oceansync.entity.Stock;
import lk.ijse.oceansync.model.StockDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockBOImpl implements StockBO {
    StockDAO stockDAO = (StockDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.STOCK);

    @Override
    public boolean deleteStock(String id) throws SQLException, ClassNotFoundException {
        return stockDAO.delete(id);
    }

    @Override
    public StockDTO searchStockByID(String id) throws SQLException, ClassNotFoundException {
       Stock dto = stockDAO.search(id);
       return new StockDTO(dto.getItemId(),dto.getName(),dto.getPrice(),dto.getQty(),dto.getUserId());
    }

    @Override
    public boolean addStock(StockDTO dto) throws SQLException, ClassNotFoundException {
        System.out.println(dto);
    return stockDAO.add(new Stock(dto.getItemId(),dto.getName(),dto.getPrice(),dto.getQty(),dto.getUserId()));
    }

    @Override
    public String generateNewStockID() throws SQLException, ClassNotFoundException {
        return stockDAO.generateNewID();
    }

    @Override
    public boolean updateStock(StockDTO dto) throws SQLException, ClassNotFoundException {
        return stockDAO.update(new Stock(dto.getItemId(),dto.getName(),dto.getPrice(),dto.getQty(),dto.getUserId()));
    }

    @Override
    public ArrayList<StockDTO> getAllStock() throws SQLException, ClassNotFoundException {
        ArrayList<Stock> dtos = stockDAO.getAll();
        ArrayList<StockDTO> stockDTOS =new ArrayList<>();
        for (Stock stock : dtos){
            stockDTOS.add(new StockDTO(stock.getItemId(),stock.getName(),stock.getPrice(),stock.getQty(),stock.getUserId()));
        }
        return stockDTOS;
    }

    @Override
    public StockDTO getAvailableStock(String stockId) throws SQLException {
       Stock availableStock = stockDAO.getAvailableStock(stockId);
       return new StockDTO(availableStock.getItemId(),availableStock.getName(),availableStock.getPrice(),availableStock.getQty(),availableStock.getUserId());
    }

    @Override
    public StockDTO searchStockByName(String stockName) throws SQLException {
        Stock stock = stockDAO.searchByName(stockName);
        return new StockDTO(stock.getItemId(),stock.getName(),stock.getPrice(),stock.getQty(),stock.getUserId());
    }
}
