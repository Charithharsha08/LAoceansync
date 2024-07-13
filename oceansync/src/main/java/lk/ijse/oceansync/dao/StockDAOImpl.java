package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.dao.custom.impl.StockDAO;
import lk.ijse.oceansync.entity.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockDAOImpl implements StockDAO {
    @Override
    public ArrayList<Stock> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Stock> allStocks =new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM stock");
        while (rst.next()){
            allStocks.add(new Stock(rst.getString("itemId"),rst.getString("name"),rst.getDouble("type"),rst.getInt("qty"),rst.getString("userId")));
        } return allStocks;
    }

    @Override
    public boolean add(Stock entity) throws SQLException, ClassNotFoundException {
      return SQLUtil.execute("INSERT INTO stock (itemId,name,type,qty,userId) VALUES(?, ?, ?, ?,?)",entity.getItemId(),entity.getName(),entity.getPrice(),entity.getQty(),entity.getUserId());
    }

    @Override
    public boolean update(Stock entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE stock SET name=?, type=?, qty=?, userId=? WHERE itemId=?",entity.getName(),entity.getPrice(),entity.getQty(),entity.getUserId(),entity.getItemId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT itemId FROM stock ORDER BY itemId DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("itemId");
            int newStockId = Integer.parseInt(id.replace("S00-", "")) + 1;
            return String.format("S00-%03d", newStockId);
        } else {
            return "S00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM stock WHERE itemId=?",id);
    }

    @Override
    public Stock search(String id) throws SQLException, ClassNotFoundException {
    ResultSet resultSet = SQLUtil.execute("SELECT * FROM stock WHERE itemId =?",id);
    resultSet.next();
    return new Stock(
            id + "",
            resultSet.getString("name"),
            resultSet.getDouble("type"),
            resultSet.getInt("Qty"),
            resultSet.getString("userId")
    );
    }

    @Override
    public Stock getAvailableStock(String stockId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM stock WHERE itemId=?",stockId);
        if (resultSet.next()){
            return new Stock(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4),resultSet.getString(5));
        } return null;
    }

    @Override
    public Stock searchByName(String stockName) throws SQLException {
       ResultSet resultSet = SQLUtil.execute("SELECT * FROM stock WHERE name=?",stockName);
       resultSet.next();
        return new Stock(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4),resultSet.getString(5));

    }
}
