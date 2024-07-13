package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.dao.custom.impl.UserDAO;
import lk.ijse.oceansync.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean add(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO user (userId,userName,password) VALUES (?,?,?)",entity.getUserId(),entity.getUserName(),entity.getPassword());
    }

    @Override
    public boolean update(User dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
    return null;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public User searchUser(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE userId = ?",id);
        resultSet.next();
        return new User(id + "",resultSet.getNString("userName"),resultSet.getNString("password"));
            }
}
