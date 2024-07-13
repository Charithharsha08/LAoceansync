package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.dao.custom.impl.CustomerDAO;
import lk.ijse.oceansync.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer");
        while (rst.next()){
            customers.add(new Customer(rst.getString("customerId"),rst.getString("name"),rst.getString("address"),rst.getString("tel")));
        }
        return customers;
    }

    @Override
    public boolean add(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO customer (customerId,name,address,tel)VALUES(?, ?, ?, ?)",entity.getCustomerId(),entity.getName(),entity.getAddress(),entity.getTel());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE customer SET name=?, address=?, tel=? WHERE customerId=?",entity.getName(),entity.getAddress(),entity.getTel(),entity.getCustomerId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("customerId");
            int newCustomerId = Integer.parseInt(id.replace("U00-", "")) + 1;
            return String.format("U00-%03d", newCustomerId);
        } else {
            return "U00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("DELETE FROM customer WHERE customerId=?",id);
    }

    @Override
    public Customer search(String number) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE tel = ?",number);
        rst.next();
        return new Customer(rst.getString("customerId"),rst.getString("name"),rst.getString("address"),number +"");
    }
}
