package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.dao.custom.impl.EmployeeDAO;
import lk.ijse.oceansync.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = new ArrayList<>();
        ResultSet set = SQLUtil.execute("SELECT * FROM employee");
        while (set.next()){
            employees.add(new Employee(set.getString("id"),set.getString("employeeId"),set.getString("name"),set.getString("activity"),set.getString("month"),set.getDouble("salary"),set.getDate("date"),set.getString("userId")));
        }
        return employees;
    }

    @Override
    public boolean add(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employee (id,employeeId,name,activity,month,salary,date,userId) VALUES(?, ?, ?, ?,?,?,?,?)",entity.getId(),entity.getEmployeeId(),entity.getName(),entity.getActivity(),entity.getMonth(),entity.getSalary(),entity.getDate(),entity.getUserId());
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employee SET employeeId=?, name=?, activity=?, month=?, salary=?, date=?, userId=? WHERE id=?",entity.getEmployeeId(),entity.getName(),entity.getActivity(),entity.getMonth(),entity.getSalary(),entity.getDate(),entity.getUserId(),entity.getId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT id FROM employee ORDER BY id desc LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("E00-", "")) + 1;
            return String.format("E00-%03d", newCustomerId);
        } else {
            return "E00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employee WHERE id=?",id);
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
       // System.out.println(id);
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM employee WHERE id = ?",id);
resultSet.next();
        return new Employee(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDouble(6),resultSet.getDate(7),resultSet.getString(8));
    }
}
