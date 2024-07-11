package lk.ijse.oceansync.bo;

import lk.ijse.oceansync.entity.Payment;
import lk.ijse.oceansync.model.PaymentDTO;

import java.sql.SQLException;

public interface PaymentBO extends SuperBO{
    public String generatePaymentNewID() throws SQLException, ClassNotFoundException;
    public boolean placePayment(PaymentDTO dto) throws SQLException,ClassNotFoundException;
    }
