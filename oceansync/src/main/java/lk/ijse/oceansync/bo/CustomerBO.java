package lk.ijse.oceansync.bo;

import lk.ijse.oceansync.entity.Customer;
import lk.ijse.oceansync.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO{
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException;
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    public CustomerDTO searchCustomerByNumber(String number) throws SQLException, ClassNotFoundException;
    }
