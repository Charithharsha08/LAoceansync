package lk.ijse.oceansync.dao;

import lk.ijse.oceansync.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User>{
    public User searchUser(String id) throws SQLException;
}
