package lk.ijse.oceansync.bo.custom.impl;

import lk.ijse.oceansync.bo.custom.CustomerBO;
import lk.ijse.oceansync.dao.custom.impl.CustomerDAO;
import lk.ijse.oceansync.dao.DAOFactory;
import lk.ijse.oceansync.entity.Customer;
import lk.ijse.oceansync.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> dtos = new ArrayList<>();
        for (Customer customer : customers){
            dtos.add(new CustomerDTO(customer.getCustomerId(),customer.getName(),customer.getAddress(),customer.getTel()));
        }return dtos;
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
       return customerDAO.add(new Customer(dto.getCustomerId(),dto.getName(),dto.getAddress(),dto.getTel()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return  customerDAO.update(new Customer(dto.getCustomerId(),dto.getName(),dto.getAddress(),dto.getTel()));
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchCustomerByNumber(String number) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(number);
        return new CustomerDTO(customer.getCustomerId(),customer.getName(),customer.getAddress(),customer.getTel());
    }
}
