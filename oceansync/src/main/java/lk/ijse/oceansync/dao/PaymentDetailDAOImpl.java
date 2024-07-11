package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.entity.PaymentDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDetailDAOImpl implements PaymentDetailDAO{
    @Override
    public ArrayList<PaymentDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(PaymentDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO paymentDetail (paymentId,customerId,name,id,description,unitPrice,qty,discount,total) VALUES(?,?,?,?,?,?,?,?,?)",entity.getPaymentId(),entity.getCustomerId(),entity.getName(),entity.getId(),entity.getDescription(),entity.getUnitPrice(),entity.getQty(),entity.getDiscount(),entity.getTotal());
    }

    @Override
    public boolean update(PaymentDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public PaymentDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
