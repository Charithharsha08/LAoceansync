package lk.ijse.oceansync.bo;

import lk.ijse.oceansync.model.DiscountDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DiscountBO extends SuperBO{
    public ArrayList<DiscountDTO> getAllDiscount() throws SQLException, ClassNotFoundException;
    public boolean addDiscount(DiscountDTO entity) throws SQLException, ClassNotFoundException;
    public String generateNewDiscountID() throws SQLException, ClassNotFoundException;
    public boolean deleteDiscount(String id) throws SQLException, ClassNotFoundException;
    public DiscountDTO searchDiscount(String id) throws SQLException, ClassNotFoundException;
    }
