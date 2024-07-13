package lk.ijse.oceansync.bo.custom;

import lk.ijse.oceansync.bo.SuperBO;
import lk.ijse.oceansync.model.PaymentDTO;

import java.sql.SQLException;

public interface PaymentBO extends SuperBO {
    public String generatePaymentNewID() throws SQLException, ClassNotFoundException;
    public boolean placePayment(PaymentDTO dto) throws SQLException,ClassNotFoundException;
    }
