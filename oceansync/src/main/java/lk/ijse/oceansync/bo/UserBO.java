package lk.ijse.oceansync.bo;

import lk.ijse.oceansync.model.UserDTO;

import java.sql.SQLException;

public interface UserBO extends SuperBO{
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException ;

    public UserDTO checkUser(String id) throws SQLException, ClassNotFoundException;
}
