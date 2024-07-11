package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.entity.Discount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscountDAOImpl implements DiscountDAO{
    @Override
    public ArrayList<Discount> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Discount> discounts = new ArrayList<>();
       ResultSet resultSet = SQLUtil.execute("SELECT * FROM discount");
       while (resultSet.next()){
           discounts.add(new Discount(resultSet.getString(1),resultSet.getString(3),resultSet.getDouble(3)));
       }return discounts;
    }

    @Override
    public boolean add(Discount entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO discount VALUES(?, ?, ?)",entity.getDiscountId(),entity.getType(),entity.getDiscount());
    }

    @Override
    public boolean update(Discount dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT discountId FROM discount ORDER BY discountId DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("discountId");
            int newCustomerId = Integer.parseInt(id.replace("D00-", "")) + 1;
            return String.format("D00-%03d", newCustomerId);
        } else {
            return "D00-001";
        }    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM discount WHERE discountId = ?",id);
    }

    @Override
    public Discount search(String id) throws SQLException, ClassNotFoundException {
       ResultSet resultSet = SQLUtil.execute("SELECT * FROM discount WHERE discountId = ?",id);
       resultSet.next();
        return new Discount(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3));
    }
}
