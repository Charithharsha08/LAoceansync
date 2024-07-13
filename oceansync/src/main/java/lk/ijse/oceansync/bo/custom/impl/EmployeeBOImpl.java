package lk.ijse.oceansync.bo.custom.impl;

import lk.ijse.oceansync.bo.custom.EmployeeBO;
import lk.ijse.oceansync.dao.DAOFactory;
import lk.ijse.oceansync.dao.custom.impl.EmployeeDAO;
import lk.ijse.oceansync.entity.Employee;
import lk.ijse.oceansync.model.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> dtos =new ArrayList<>();
        ArrayList<Employee> employees = employeeDAO.getAll();
        for (Employee employee : employees){
            dtos.add(new EmployeeDTO(employee.getId(),employee.getEmployeeId(),employee.getName(),employee.getActivity(),employee.getMonth(),employee.getSalary(),employee.getDate(),employee.getUserId()));
        }return dtos;
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(dto.getId(),dto.getEmployeeId(),dto.getName(),dto.getActivity(),dto.getMonth(),dto.getSalary(),dto.getDate(),dto.getUserId()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getId(),dto.getEmployeeId(),dto.getName(),dto.getActivity(),dto.getMonth(),dto.getSalary(),dto.getDate(),dto.getUserId()));
    }

    @Override
    public String generateNewEmployeeID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(id);
        return new EmployeeDTO(employee.getId(),employee.getEmployeeId(),employee.getName(),employee.getActivity(),employee.getMonth(),employee.getSalary(),employee.getDate(),employee.getUserId());
    }
}
