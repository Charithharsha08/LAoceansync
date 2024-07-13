package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.dao.custom.impl.PaymentDAO;
import lk.ijse.oceansync.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(Payment entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO payment (paymentId,type,amount,date,customerId)VALUES (?,?,?,?,?)",entity.getPaymentId(),entity.getType(),entity.getTotal(),entity.getDate(),entity.getCustomerId());
    }

    @Override
    public boolean update(Payment dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT paymentId FROM payment ORDER BY paymentId DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("paymentId");
            int newCustomerId = Integer.parseInt(id.replace("P00-", "")) + 1;
            return String.format("P00-%03d", newCustomerId);
        } else {
            return "P00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Payment search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
