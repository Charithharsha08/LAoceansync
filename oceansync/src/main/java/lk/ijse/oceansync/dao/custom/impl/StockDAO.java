package lk.ijse.oceansync.dao.custom.impl;

import lk.ijse.oceansync.dao.CrudDAO;
import lk.ijse.oceansync.entity.Stock;

import java.sql.SQLException;

public interface StockDAO extends CrudDAO<Stock> {
    public Stock getAvailableStock(String stockId) throws SQLException;

    Stock searchByName(String stockName) throws SQLException;
}
